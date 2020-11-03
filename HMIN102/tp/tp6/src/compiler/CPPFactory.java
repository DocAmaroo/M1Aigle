package compiler;

public class CPPFactory extends AFactory{
	
	
	public CPPFactory() {
		this.supportedLanguage = "C++";
	}

	@Override
	public ALexer createLexer() {
		return new LexerCPP();
	}

	@Override
	public AParser createParser() {
		return new ParserCPP();
	}

	@Override
	public AGenerator createGenerator() {
		return new GeneratorCPP();
	}

}
