package compiler;

class ParserJava extends AParser{

	@Override
	public void print() {
		System.out.println("I am parsing a JAVA scanned text and I generate a Java abstractSyntaxTree");		
	}

}

class ParserCPP extends AParser{

	@Override
	public void print() {
		System.out.println("I am parsing a CPP scanned text and I generate a Java abstractSyntaxTree");		
	}
	
	
}
