package compiler;

public class Compiler {

		protected AFactory factory;
		protected ALexer lexer;
		protected AParser parser;
		protected AGenerator gen;

		public Compiler (String languageName) throws Exception{
			factory = AFactory.getFactory(languageName);
			lexer = factory.createLexer();
			parser = factory.createParser();
			gen = factory.createGenerator();
			}
		
		public void compile(){
			System.out.println("Compiling a: " + factory.getLanguage() + " program.");
			this.lexer.print();
			this.parser.print();
			this.gen.print();
		}
	
		public static void main(String[] args) {
		try {
			Compiler c1 = new Compiler("Java");
			Compiler c2 = new Compiler("C++");
			
			c1.compile();
			System.out.println(" ---- ");
			c2.compile();
			System.out.println(" ---- ");
			try {
				Compiler c3 = new Compiler("Ada");
				c3.compile();
			}
			catch( Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		}
	
}


