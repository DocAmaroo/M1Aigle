package compiler;

public abstract class AFactory {
	
		protected String supportedLanguage;
		
		public String getLanguage(){return supportedLanguage;}

		public static AFactory getFactory(String language) throws Exception{
			if (language.equals("Java")){
				return(new JavaFactory());} 
			else if (language.equals("C++")){
				return(new CPPFactory());}
			else throw new Exception("Non supported Language : " + language + ", Extend the framework to support it");
		}

		public abstract ALexer createLexer();
		public abstract AParser createParser();
		public abstract AGenerator createGenerator();
	}
