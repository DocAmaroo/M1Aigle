package compiler;

public class JavaFactory extends AFactory{

	public JavaFactory() {
		this.supportedLanguage = "java";
		 
	}

	@Override
	public ALexer createLexer() {
		return new LexerJava();
		
	}

	@Override
	public AParser createParser() {
		return new ParserJava();
		
	}

	@Override
	public AGenerator createGenerator() {
		return new GeneratorJava();
		
	}
	
}
