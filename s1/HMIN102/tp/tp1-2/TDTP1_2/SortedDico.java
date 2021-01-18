package TDTP1_2;

public class SortedDico extends AbstractDico{

	public SortedDico(int taille) {
		super(taille);
	}

	public int compareTo(Object o1, Object o2) {
		String k1=String.valueOf(o1);
		String k2=String.valueOf(o2);
		return k1.compareTo(k2);
	}

	public int size() {
		int taille=0;
		int c=0;
		while(c < keys.length) {
			if(keys[c]!=null) taille=taille+1;
			c=c+1;
		}
		return taille;
	}

	public void mustGrow() {
		if(this.size()>=keys.length-1) grow();
	}

	public void grow() {
		Object[] tmpk=keys;
		Object[] tmpv=values;
		int taille=keys.length;
		keys = new Object[taille+1];
		values = new Object[taille+1];
		for(int i=0; i<taille; i++) {
			keys[i]=tmpk[i];
			values[i]=tmpv[i];
		}
	}

	public int indexOf(Object key) {
		if(this.size()>=keys.length-1) {
			return -1;
		}
		else {
			int index=0;
			if (this.size() == 0) {
				return 0;
			}
			while(compareTo(key, keys[index])>0) {
				index++;
			}
			for(int i=this.size()-1; i>=index; i--) {
				keys[i+1]=keys[i];
				keys[i]=null;
				values[i+1]=values[i];
				values[i]=null;
			}
			return index;
		}
	}

	public int newIndexOf(Object key) {
		mustGrow();
		int index=0;
		while(compareTo(key, keys[index])>0) {
			index=index+1;
		}
		for(int i=this.size()-1; i>=index;i--) {
			keys[i+1]=keys[i];
			keys[i]=null;
			values[i+1]=values[i];
			values[i]=null;
		}
		return index;
	}
}