package TDTP1_2;

public class SortedDico extends AbstractDico{
	
	public SortedDico(int length) {
		super(length);
	}

	public boolean mustGrown() {
		return false;
	}

	public void grow() {
	}

	@Override
	public int indexOf(Object key) {
		return 0;
	}

	@Override
	public int newIndexOf(Object key) {
		return 0;
	}


}
