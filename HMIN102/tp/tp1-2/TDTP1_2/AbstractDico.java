package TDTP1_2;

public abstract class AbstractDico implements IDico {
	protected Object keys[];
	protected Object values[];
	private int length;
	
	public AbstractDico(int length) {
		this.keys = new Object[length];
		this.values = new Object[length];
		this.length = length;
	}
	
	public int size() {
		return length;
	}
	
	public boolean isEmpty() {
		return length == 0;
	}
	
	public Object get(Object key) {
		int index=0;
		while( !(keys[index].equals(key)) ) {
			index++;
		}
		return values[index];
	}
	
	public IDico put(Object key, Object value) {
		return null;
	}
	
	public boolean containsKey(Object key) {
		for( int i=0; i<this.size(); i++) {
			if ( keys[i].equals(key) ) {
				return true;
			}
		}
		return false;
	}
	
	public abstract int indexOf(Object key);
	public abstract int newIndexOf(Object key);
}
