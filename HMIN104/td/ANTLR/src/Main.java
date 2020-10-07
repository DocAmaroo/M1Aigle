import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CharStream stream = CharStreams.fromFileName("code");

        LanguageLexer lexer = new LanguageLexer(stream);

        CommonTokenStream tokens= new CommonTokenStream(lexer);

        LanguageParser parser = new LanguageParser(tokens);

        parser.program().pr.print();
    }
}