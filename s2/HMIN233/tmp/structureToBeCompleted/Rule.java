

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Rule {
	private List<Atom> hypothesis;// l'hypothese : une liste d'atomes (H+)
	private List<Atom> negHypothesis;
	private Atom conclusion;// la conclusion : un atome

	/**
	 * Constructeur
	 * 
	 * @param strRule la regle, sous forme sous forme textuelle ; cette forme est
	 *                "atome1;atome2;...atomek", ou les (k-1) premiers atomes
	 *                forment l'hypothese, et le dernier forme la conclusion
	 */
	public Rule(String strRule) {
		hypothesis = new ArrayList<Atom>();
		negHypothesis = new ArrayList<Atom>();
		StringTokenizer st = new StringTokenizer(strRule, ";");
		while (st.hasMoreTokens()) {
			String s = st.nextToken(); // s represente un atome
			if (s.contains("-")){
				String neg = s.replaceAll("-","");
				Atom a = new Atom(neg);
				negHypothesis.add(a);
			}
			else{
				Atom a = new Atom(s);
				hypothesis.add(a);// ajout de a a la liste des atomes de l'hypothese (pour l'instant, on ajoute
									// aussi celui de la conclusion)
			}

		}
		// on a mis tous les atomes crees en hypothese
		// il reste a tranferer le dernier en conclusion
		conclusion = hypothesis.get(hypothesis.size() - 1);
		hypothesis.remove(hypothesis.size() - 1);
	}

	/**
	 * accesseur a l'hypothese de la regle
	 * 
	 * @return l'hypothese de la regle
	 */
	public List<Atom> getHypothesis() {
		return hypothesis;
	}

	public List<Atom> getNegativeHypothesis() {
		return negHypothesis;
	}
	/**
	 * retourne la ieme atome de l'hypothese
	 * 
	 * @param i le rang de l'atome a retourner (debut a 0)
	 * @return le ieme atome de l'hypothese
	 */
	public Atom getAtomHyp(int i) {
		return hypothesis.get(i);
	}

	public Atom getAtomNegHyp(int i) {
		return negHypothesis.get(i);
	}
	/**
	 * accesseur a la conclusion de la regle
	 * 
	 * @return l'atome conclusion de la regle
	 */
	public Atom getConclusion() {
		return conclusion;
	}

	/**
	 * retourne une description de la regle
	 * 
	 * @return la chaine decrivant la regle (suivant l'ecriture habituelle)
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < hypothesis.size(); i++) {
			s += hypothesis.get(i);
			s += " ; ";
		}
		for (int i = 0; i < negHypothesis.size(); i++) {
			s += "-"+negHypothesis.get(i);
			if (i < negHypothesis.size() - 1)
				s += " ; ";
		}
		s += " --> ";
		s += conclusion;
		return s;
	}

}
