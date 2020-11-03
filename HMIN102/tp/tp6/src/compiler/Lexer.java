package compiler;

class LexerJava extends ALexer{

	@Override
	public void print() {
		System.out.println("I am scanning a Java program text ");
		
	}

}


class LexerCPP extends ALexer{

	@Override
	public void print() {
		System.out.println("I am scanning a CPP program text ");
		
	}
	
	
}