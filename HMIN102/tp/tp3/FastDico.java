package tp3;

public class FastDico extends AbstractDico{

	public FastDico(int length) {
		super(length);
	}

	@Override
	public int size() {
		int result = 0;
		for (int i=0; i < keys.length; i++) {
			if (keys[i] != null) {
				result++;
			}
		}
		
		return result;
	}

	public boolean mustGrown() {
		return this.size() >= (int)(3/4*(keys.length));
	}

	public void grow() {
		int length = keys.length;
		Object[] newKeys = new Object[length + (int) Math.ceil(length/4)];
		Object[] newValues = new Object[length + (int) Math.ceil(length/4)];

		int size = this.size();

		for (int i = 0; i < keys.length; i++) {
			this.put(newKeys[i], newValues[i]);
		}

		this.keys = newKeys;
		this.values = newValues;
	}


	@Override
	public int indexOf(Object key) {
		int hashkey = key.hashCode()%keys.length;
		if (hashkey < 0) {
			hashkey = hashkey*-1;
		}

		if( values[hashkey] == null) {
			return hashkey;
		}
        else return -1;
	}

	@Override
	public int newIndexOf(Object key) {
		if (this.mustGrown()) {
			this.grow();
		};

		int length = keys.length;
		int hashkey = key.hashCode()%length;
		int cpt = 0;
		int index = 0;

		while(cpt < length) {
			index = hashkey + cpt;

			if(index >= length) {
				// on retourne au début du tableau
				index = index - length;
			}

			if(values[index] == null) {
				cpt = length;
			} else {
				cpt++;
			}
		}

		return index;
	}
}
