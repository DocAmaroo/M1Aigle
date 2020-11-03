package compiler;

class GeneratorJava extends AGenerator{

	@Override
	public void print() {
		System.out.println("I am generating a JMV program text from AST");		
	}

}

class GeneratorCPP extends AGenerator{

	@Override
	public void print() {
		System.out.println("I am generating an Assembler program text from a AST ");		
	}
	
	
}
