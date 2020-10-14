package tp3;

public abstract class AbstractDico implements IDico {
	protected Object keys[];
	protected Object values[];
	
	public AbstractDico(int length) {
		this.keys = new Object[length];
		this.values = new Object[length];
	}
	
	public int size() {
		return keys.length;
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public Object get(Object key) {
		int index=0;
		while( !(key.equals(keys[index])) ) {
			index++;
		}
		return values[index];
	}
	
	public boolean containsKey(Object key) {
		for( int i=0; i<this.size(); i++) {
			if ( key.equals(keys[i]) ) {
				return true;
			}
		}
		return false;
	}

	public IDico put(Object key, Object value){
		int index = this.indexOf(key);
		if (index == -1) {
			int i = this.newIndexOf(key);
			keys[i] = key;
			values[i] = value;
		} else {
			keys[index] = key;
			values[index] = value;
		}
		return this;
	}

	public abstract int indexOf(Object key);
	public abstract int newIndexOf(Object key);
}
