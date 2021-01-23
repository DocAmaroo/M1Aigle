

/**
 * @author graphikTeam
 *
 */
/*
 * Un atome est un symbole propositionnel. Remarque : le seul attribut d'un
 * atome est la string correspondant au symbole Dans un but d'optimisation, on
 * pourrait aussi associer à chaque symbole un entier distinct, et utiliser cet
 * entier comme index dans diverses structures
 */

public class Atom {
	private String symbol; // le symbole propositionnel

	/**
	 * @param s l'atome (
	 */
	public Atom(String s) {
		symbol = s;
	}

	@Override
	public boolean equals(Object b) {
		if (!(b instanceof Atom))
			return false;
		return this.symbol.equals(((Atom) b).symbol);
		// deux atomes sont égaux s'ils ont même symbole
		// equals() de String compare le contenu des chaînes de caractère
	}

	/**
	 * @return le symbole propositionnel
	 */
	@Override
	public String toString() {
		return this.symbol;
	}

	/** overrides hashCode from Object **/
	@Override
	public int hashCode()
	// pour utilisation dans les hashMap :
	// le hashcode est utilise pour définir les cles (deux objets ayant même hascode
	// correspondent à la même cle)
	// la redefinition de cette méthode assure que deux atomes sont égaux pour
	// equals() ssi ils ont le même hashCode
	//
	{
		return symbol.hashCode(); // hashcode fondé sur l'attribut symbol (une String)

	}
}
