// Generated from /home/thomas/IdeaProjects/AST/src/Language.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LanguageParser}.
 */
public interface LanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LanguageParser#listExpr}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(LanguageParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#listExpr}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(LanguageParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#listParam}.
	 * @param ctx the parse tree
	 */
	void enterListParam(LanguageParser.ListParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#listParam}.
	 * @param ctx the parse tree
	 */
	void exitListParam(LanguageParser.ListParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#listVar}.
	 * @param ctx the parse tree
	 */
	void enterListVar(LanguageParser.ListVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#listVar}.
	 * @param ctx the parse tree
	 */
	void exitListVar(LanguageParser.ListVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#listProcs}.
	 * @param ctx the parse tree
	 */
	void enterListProcs(LanguageParser.ListProcsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#listProcs}.
	 * @param ctx the parse tree
	 */
	void exitListProcs(LanguageParser.ListProcsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#proc}.
	 * @param ctx the parse tree
	 */
	void enterProc(LanguageParser.ProcContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#proc}.
	 * @param ctx the parse tree
	 */
	void exitProc(LanguageParser.ProcContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(LanguageParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(LanguageParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(LanguageParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(LanguageParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#ternaryCond}.
	 * @param ctx the parse tree
	 */
	void enterTernaryCond(LanguageParser.TernaryCondContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#ternaryCond}.
	 * @param ctx the parse tree
	 */
	void exitTernaryCond(LanguageParser.TernaryCondContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#appel}.
	 * @param ctx the parse tree
	 */
	void enterAppel(LanguageParser.AppelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#appel}.
	 * @param ctx the parse tree
	 */
	void exitAppel(LanguageParser.AppelContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#fonctionExpr}.
	 * @param ctx the parse tree
	 */
	void enterFonctionExpr(LanguageParser.FonctionExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#fonctionExpr}.
	 * @param ctx the parse tree
	 */
	void exitFonctionExpr(LanguageParser.FonctionExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#fonctionIns}.
	 * @param ctx the parse tree
	 */
	void enterFonctionIns(LanguageParser.FonctionInsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#fonctionIns}.
	 * @param ctx the parse tree
	 */
	void exitFonctionIns(LanguageParser.FonctionInsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(LanguageParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(LanguageParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(LanguageParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(LanguageParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#constante}.
	 * @param ctx the parse tree
	 */
	void enterConstante(LanguageParser.ConstanteContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#constante}.
	 * @param ctx the parse tree
	 */
	void exitConstante(LanguageParser.ConstanteContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(LanguageParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(LanguageParser.TypeContext ctx);
}