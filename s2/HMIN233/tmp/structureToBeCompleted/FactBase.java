

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author graphikTeam
 *
 */
public class FactBase {
	private List<Atom> atoms; // les atomes

	/**
	 * Constructeur : cree une base de faits vide
	 */
	public FactBase() {
		atoms = new ArrayList<Atom>();
	}

	/**
	 * Constructeur : initialise avec une chaine de caracteres correspondant a des
	 * atomes
	 * 
	 * @param factList : les faits, passes sous la forme "atom1;...;atomk"
	 */
	public FactBase(String factList) {
		this(); // appel au constructeur sans paramètres
		createFactBase(factList);
	}

	/**
	 * Remplit la base de faits avec la chaine de caracteres passee en parametres
	 * 
	 * @param factList: les faits, passes sous la forme "atom1;...;atomk"
	 */
	private void createFactBase(String factList)
	// Prerequis : le format de la base de faits doit etre correct
	{
		StringTokenizer st = new StringTokenizer(factList, ";");
		while (st.hasMoreTokens()) {
			String s = st.nextToken(); // s represente un atome
			Atom a = new Atom(s);
			atoms.add(a);// on ajoute un atome a la liste des atomes
		}
	}

	/**
	 * Ajoute des atomes a la base de faits s'ils n'apparaissent pas deja
	 * 
	 * @param atomList la liste d'atomes a ajouter (seuls ceux n'apparaissant pas
	 *                 dans la base seront ajoutes)
	 */
	public void addAtoms(List<Atom> atomList) {
		for (int i = 0; i < atomList.size(); i++) {
			Atom a = atomList.get(i);
			if (!atoms.contains(a))
				addAtomWithoutCheck(a);
		}
	}

	/**
	 * Verifie si un atom est dans la base de faits
	 * 
	 * @param a l'atome
	 */
	public boolean contains(Atom a) {
		return atoms.contains(a);
	}

	/**
	 * Ajoute un atome a la base de faits (meme si il y est deja)
	 * 
	 * @param a l'atome a ajouter
	 */
	public void addAtomWithoutCheck(Atom a) {
		atoms.add(a);
	}

	/**
	 * retourne la liste des atomes de la base de faits
	 * 
	 * @return la liste des atomes de la base de faits
	 */
	public List<Atom> getAtoms() {
		return atoms;
	}

	/**
	 * teste si la base est vide
	 * 
	 * @return vrai si la base est vide
	 */
	public boolean isEmpty() {
		return atoms.isEmpty();
	}

	/**
	 * retourne le nombre de faits dans la base
	 * 
	 * @return le nombre de faits dans la base
	 */
	public int size() {
		return atoms.size();
	}

	/**
	 * Retourne une description de la base de faits
	 * 
	 * @return description de la base de faits
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "Base de Faits" + " (" + atoms.size() + ")" + ":\n";
		for (int i = 0; i < atoms.size() - 1; i++)
			s += atoms.get(i) + " ; ";
		s += atoms.get(atoms.size() - 1) + "\n";
		return s;
	}

	public static void main(String args[]) {
		FactBase bf = new FactBase();
		Atom a = new Atom("A");
		Atom b = new Atom("B");
		Atom a2 = new Atom("A");
		bf.addAtomWithoutCheck(a); // A
		List<Atom> T = new ArrayList<>();
		T.add(b);
		T.add(a2); // B A
		bf.addAtoms(T); // A B
		System.out.println(bf);

	}
}
