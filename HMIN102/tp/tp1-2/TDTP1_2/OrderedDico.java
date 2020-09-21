package TDTP1_2;

public class OrderedDico extends AbstractDico{

	public OrderedDico(int length) {
		super(length);
	}
	
	@Override
	public int indexOf(Object key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null) {
				i++;
			}
		}
		return -1;
	}
	
	
	public int newIndexOf(Object key) {
		int size = this.size();
		if (size == keys.length) {
			Object[] newKeys = new Object[keys.length + 1];
			Object[] newValues = new Object[keys.length + 1];
			
			for(int i = 0; i < keys.length; i++) {
				newKeys[i] = keys[i];
				newValues[i] = values[i];
			}
			
			keys = newKeys;
			values = newValues;
			
			return keys.length - 1;
		} else {
			return size;
		}
	}
	
}
