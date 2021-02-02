

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;


public class KnowledgeBase {

	private FactBase bf; // base de faits initiale
	private RuleBase br; // base de règles
	private FactBase bfSat; // base de faits saturée - vide initialement

	public KnowledgeBase() {
		bf = new FactBase();
		br = new RuleBase();
		bfSat = new FactBase();

	}

	public KnowledgeBase(String fic) {
		this(); // initialisation des bases à vide
		BufferedReader lectureFichier;
		try {
			lectureFichier = new BufferedReader(new FileReader(fic));
		} catch (FileNotFoundException e) {
			System.err.println("Fichier base de connaissances absent: " + fic);
			e.printStackTrace();
			return;
		}
		try {
			String s = lectureFichier.readLine();
			if (s != null) { // si non vide
				bf = new FactBase(s); // 1ere ligne : factbase
				s = lectureFichier.readLine();
				while (s != null && s.length() != 0) { // arret si fin de fichier ou ligne vide
					br.addRule(new Rule(s));
					s = lectureFichier.readLine();
				}
			}
			lectureFichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FactBase getBf() {
		return bf;
	}

	public FactBase getBfSat() {
		return bfSat;
	}

	public RuleBase getBr() {
		return br;
	}

	@Override
	public String toString() {
		return "**********\nBase de connaissances: \n" + bf + br + "\n**********";
	}


	public void forwardChainingBasic() {
		// algo basique de forward chaining

		bfSat = new FactBase(); // ré-initialisation de bfSat
		bfSat.addAtoms(bf.getAtoms()); // avec les atomes de bf
		boolean fin = false;
		boolean[] Appliquee = new boolean[br.size()];
		for (int i = 0; i < br.size(); i++) {
			Appliquee[i] = false;
		}
		while (!fin) {
			FactBase newFacts = new FactBase();
			for (int i = 0; i < br.size(); i++) {
				if (!Appliquee[i]) {
					Rule r = br.getRule(i);
					// test d'applicabilite de la regle i
					boolean applicable = true;
					List<Atom> hp = r.getHypothesis();
					for (int j = 0; applicable && j < hp.size(); j++)
						if (!bfSat.contains(r.getAtomHyp(j)))
							applicable = false;
					if (applicable) {
						Appliquee[i] = true;
						Atom c = r.getConclusion();
						if (!bfSat.contains(c) && !newFacts.contains(c))
							newFacts.addAtomWithoutCheck(c);
					}
				}
			}
			if (newFacts.isEmpty())
				fin = true;
			else
				bfSat.addAtoms(newFacts.getAtoms());
		}
	}

	public void forwardChainingOpt(){

		bfSat = new FactBase(); // ré-initialisation de bfSat
		bfSat.addAtoms(bf.getAtoms()); // avec les atomes de bf

		if (!this.getBr().semiPos()){System.err.println("non semi positive");System.exit(1);}

		HashMap<Atom,List<Rule>> atome2Regles = new HashMap<Atom,List<Rule>>();
		HashMap<Rule,Integer> cptr = new HashMap<Rule,Integer>();
		LinkedList<Atom> aTraiter = new LinkedList<Atom>();
		HashSet<Atom> hashBf = new HashSet<Atom>();

		HashMap<Atom,List<Rule>> atomNeg2Rules = new HashMap<Atom,List<Rule>>();
		HashSet<Rule> reglesBloquees = new HashSet<Rule>();

		for(int i=0 ; i<this.getBr().size() ; i++){
			cptr.put(this.getBr().getRule(i),this.getBr().getRule(i).getHypothesis().size());
			if (this.getBr().getRule(i).getHypothesis().size()!=0){
				for(Atom a : this.getBr().getRule(i).getHypothesis()){
					List<Rule> listeRegles = atome2Regles.get(a);
					if(listeRegles == null){
						listeRegles = new ArrayList<Rule>();
						atome2Regles.put(a,listeRegles);
					}
					listeRegles.add(this.getBr().getRule(i));	
				}
			}
			if (this.getBr().getRule(i).getNegativeHypothesis().size()!=0){
				for(Atom a : this.getBr().getRule(i).getNegativeHypothesis()){
					List<Rule> listeRegles = atomNeg2Rules.get(a);
					if(listeRegles == null){
						listeRegles = new ArrayList<Rule>();
						atomNeg2Rules.put(a,listeRegles);
					}
					listeRegles.add(this.getBr().getRule(i));	
				}
			}
		}

		for(int i = 0 ; i<this.getBf().size(); i++){
			aTraiter.addLast(this.getBf().getAtoms().get(i));
			hashBf.add(this.getBf().getAtoms().get(i));
			if (atomNeg2Rules.get(this.getBf().getAtoms().get(i))!=null){
				for (Rule r : atomNeg2Rules.get(this.getBf().getAtoms().get(i))){
					if (r.getNegativeHypothesis().contains(this.getBf().getAtoms().get(i))){reglesBloquees.add(r);}
				}	
			}
		}
		for (int i =0 ; i<this.getBr().size();i++){
			if (this.getBr().getRule(i).getHypothesis().size()==0 && !reglesBloquees.contains(this.getBr().getRule(i))){
				Atom concl = this.getBr().getRule(i).getConclusion();
				if (!hashBf.contains(concl)){
					aTraiter.addLast(concl);
					hashBf.add(concl);
					bfSat.addAtomWithoutCheck(concl);
				}
			}
		}


		while(!aTraiter.isEmpty()){
			Atom f = aTraiter.removeFirst();
			List<Rule> LRf = atome2Regles.get(f);
			if(LRf!=null){
				//if (atomNeg2Rules.get(f)!=null){LRf.addAll(atomNeg2Rules.get(f));}
				for (Rule r : LRf){
					int val = cptr.get(r) - 1;
					cptr.put(r,val);
					if(val<=0 && !reglesBloquees.contains(r)){
						Atom c = r.getConclusion();
						if(!hashBf.contains(c)){
							aTraiter.addLast(c);
							hashBf.add(c);
							bfSat.addAtomWithoutCheck(c);
						}
					}					
				}
			}
		}

	}

	public boolean BC3(Atom a,HashSet<Atom> Lb, int depth){
		String tires = "---";
		System.out.println(tires.repeat(depth)+" "+a.toString());
		//System.out.println(tires);
		if (this.getBf().contains(a)){System.out.println(tires.repeat(depth)+" BF");return(true);}
		for(int i=0 ; i< this.getBr().size() ; i++){			
			Rule temp = this.getBr().getRule(i);
			if(temp.getConclusion().equals(a)){		
				System.out.println(tires+tires.repeat(depth)+" R"+(i+1));					// pour toute regle H -> a
				int j=0;
				boolean LbContainsHi = Lb.contains(temp.getAtomHyp(j));
				while(j<temp.getHypothesis().size() && !LbContainsHi){
					Atom Hi = temp.getAtomHyp(j);
					LbContainsHi = LbContainsHi || Lb.contains(Hi);
					j++;
				}
				//System.out.println(tires+tires.repeat(depth)+Hi);
				if(!LbContainsHi){
					int k = 0;
					Lb.add(temp.getConclusion());
					while(k<temp.getHypothesis().size() && this.BC3(temp.getAtomHyp(k), Lb, depth+1)){
						k++;
					}
					Lb.remove(temp.getConclusion());
					if (k>=temp.getHypothesis().size()){return true;}
					System.out.println(tires+tires.repeat(depth)+" Echec");
				}
			}
		}
		return false;
	}

	public boolean BC3opti(Atom a, HashSet<Atom> Lb, HashSet<Atom> prouves, HashSet<Atom> refutes, int depth){
		String tires = "---";
		System.out.println(tires.repeat(depth)+" "+a.toString());
		//System.out.println(tires);
		if (this.getBf().contains(a)){System.out.println(tires.repeat(depth)+" BF");return(true);}
		if (prouves.contains(a)){System.out.println(tires.repeat(depth)+" deja prouve");return(true);}
		if (refutes.contains(a)){System.out.println(tires.repeat(depth)+" deja refute");return false;}
		for(int i=0 ; i< this.getBr().size() ; i++){			
			Rule temp = this.getBr().getRule(i);
			if(temp.getConclusion().equals(a)){		
				System.out.println(tires+tires.repeat(depth)+" R"+(i+1));					// pour toute regle H -> a
				int j=0;
				boolean LbContainsHi = Lb.contains(temp.getAtomHyp(j));
				while(j<temp.getHypothesis().size() && !LbContainsHi){
					Atom Hi = temp.getAtomHyp(j);
					LbContainsHi = LbContainsHi || Lb.contains(Hi);
					j++;
				}
				//System.out.println(tires+tires.repeat(depth)+Hi);
				if(!LbContainsHi){
					int k = 0;
					Lb.add(temp.getConclusion());
					while(k<temp.getHypothesis().size() && this.BC3opti(temp.getAtomHyp(k),Lb,prouves,refutes, depth+1)){
						k++;
					}
					Lb.remove(temp.getConclusion());
					if (k>=temp.getHypothesis().size()){prouves.add(a);return true;}
					System.out.println(tires+tires.repeat(depth)+" Echec");
					refutes.add(a);
				}
			}
		}
		return false;
	}

}
