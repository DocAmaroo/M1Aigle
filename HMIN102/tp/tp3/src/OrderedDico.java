package tp3;

public class OrderedDico extends AbstractDico{

	public OrderedDico(int length) {
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

	@Override
	public int indexOf(Object key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public int newIndexOf(Object key) {
		if (this.mustGrown()) {
			this.grow();
		};

		return this.size() - 1;
	}

	public boolean mustGrown() {
		return (this.size() == keys.length);
	}

	public void grow() {
		Object[] newKeys = new Object[keys.length + 1];
		Object[] newValues = new Object[keys.length + 1];

		int size = this.size();

		for (int i = 0; i < keys.length; i++) {
			newKeys[i] = keys[i];
			newValues[i] = values[i];
		}

		this.keys = newKeys;
		this.values = newValues;
	}
}
