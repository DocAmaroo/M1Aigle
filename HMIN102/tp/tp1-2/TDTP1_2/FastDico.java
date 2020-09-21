package TDTP1_2;

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
		return this.size() >= 3/4*(keys.length-1);
	}

	public void grow() {
		while (this.mustGrown()) {
			Object tmpKeys[] = new Object[this.size()+1];
			Object tmpValues[] = new Object[this.size()+1];
			
			int index = 0;
			while( index < this.size() ) {
				tmpKeys[index] = keys[index];
				tmpValues[index] = values[index];
				
				keys = tmpKeys;
				values = tmpValues;
			}
		}
	}
	
	@Override
	public int indexOf(Object key) {
		return key.hashCode()%keys.length;
	}
	
	public IDico put(Object key, Object value) {
		this.grow();
		int size = keys.length;
		int index = this.indexOf(key);
		int i=0;
		while(i < size) {
			int tmp = index + i;
			if (tmp >= size) {
				tmp = tmp - size;
			}
			if (keys[tmp] == null) {
				i = size;
				keys[tmp] = key;
				values[tmp] = value;
			} else {
				i++;
			}
		}
		
		return this;
		
	}
	
	public int newIndexOf(Object key) {
		//appelle grow
		return 0;
	}
}
