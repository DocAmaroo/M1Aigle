// Generated from /home/thomas/IdeaProjects/AST/src/Language.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LanguageParser#listExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(LanguageParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#listParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListParam(LanguageParser.ListParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#listVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListVar(LanguageParser.ListVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#listProcs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListProcs(LanguageParser.ListProcsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#proc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProc(LanguageParser.ProcContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(LanguageParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(LanguageParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#ternaryCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryCond(LanguageParser.TernaryCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#appel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAppel(LanguageParser.AppelContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#fonctionExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFonctionExpr(LanguageParser.FonctionExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#fonctionIns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFonctionIns(LanguageParser.FonctionInsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(LanguageParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(LanguageParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#constante}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstante(LanguageParser.ConstanteContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(LanguageParser.TypeContext ctx);
}