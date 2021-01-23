

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RuleBase {

	private List<Rule> rules; // l'ensemble des regles

	/**
	 * Constructeur : cree une base de regles vide
	 */
	public RuleBase() {
		rules = new ArrayList<Rule>();
	}

	/**
	 * ajoute une regle a la base de regles (sans verifier si elle y est deja)
	 * 
	 * @param r regle a ajouter
	 */
	public void addRule(Rule r) {
		rules.add(r);
	}

	/**
	 * retourne le nombre de regles
	 * 
	 * @return le nombre de regles dans la base
	 */
	public int size() {
		return rules.size();
	}

	public boolean semiPos(){
		HashSet<Atom> concl = new HashSet<Atom>();
		for (int i=0; i< this.rules.size();i++){
			concl.add(rules.get(i).getConclusion());
		}
		for (int i=0;i<this.rules.size();i++){
			for (Atom a : concl){
				if (rules.get(i).getNegativeHypothesis().contains(a)){return false;}
			}
		}
		return true;

	}

	/**
	 * teste si la base est vide
	 * 
	 * @return vrai si la base est vide
	 */
	public boolean isEmpty() {
		return rules.isEmpty();
	}

	/**
	 * retourne la regle de rang i
	 * 
	 * @param i le rang de la regle (debut a 0)
	 * @return la regle de rang i
	 */
	public Rule getRule(int i) {
		return rules.get(i);
	}

	/**
	 * retourne une description de la base de regles
	 * 
	 * @return description de la base de faits
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Base de Regles" + " (" + rules.size() + ")" + ":\n";
		for (int i = 0; i < rules.size(); i++)
			s += "\t Regle " + (i + 1) + " : " + rules.get(i) + "\n";
		return s;
	}

}
