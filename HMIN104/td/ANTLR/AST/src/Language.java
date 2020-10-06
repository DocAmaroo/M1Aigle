import java.util.ArrayList;

// TYPES
abstract class Type {
    public abstract void print();
}
class TypeInt extends Type {
    public void print() {
        System.out.print("integer ");
    }
}
class TypeBool extends Type {
    public void print() {
        System.out.print("boolean ");
    }
}
class TypeArray extends Type {
    protected Type t;

    public TypeArray(Type t) {
        this.t = t;
    }

    public void print() {
        System.out.print("array of ");
        t.print();
    }
}

// EXPRESSIONS
abstract class Expr {
    public abstract void print();
}

// CONSTANTES
abstract class Cst extends Expr {}
class CstInt extends Cst {
    protected String n;
    public CstInt(String n) {
        this.n = n;
    }
    public void print() {
        System.out.print(this.n + " ");
    }
}
class CstTrue extends Cst {
    public void print() {
        System.out.print("true ");
    }
}
class CstFalse extends Cst {
    public void print() {
        System.out.print("false ");
    }
}

// UNARY OPERATION
abstract class UnaryOp extends Expr{
    protected Expr e;
}
class UNeg extends UnaryOp {
    public UNeg(Expr e) {
        this.e = e;
    }
    public void print(){
        System.out.print(" not ");
        e.print();
    }
}
class UMinus extends UnaryOp {
    public UMinus(Expr e){
        this.e = e;
    }
    public void print() {
        System.out.print(" -");
        e.print();
    }
}

// BINARY OPERATION
abstract class BinaryOp extends Expr {
    protected Expr e1;
    protected Expr e2;
}
class BAdd extends BinaryOp {
    public BAdd(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" + ");
        e2.print();
    }
}
class BSub extends BinaryOp {
    public BSub(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" - ");
        e2.print();
    }
}
class BMul extends BinaryOp {
    public BMul(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" * ");
        e2.print();
    }
}
class BDiv extends BinaryOp {
    public BDiv(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" / ");
        e2.print();
    }
}
class BAnd extends BinaryOp {
    public BAnd(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" AND ");
        e2.print();
    }
}
class BOr extends BinaryOp {
    public BOr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" OR ");
        e2.print();
    }
}
class BInf extends BinaryOp {
    public BInf(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" < ");
        e2.print();
    }
}
class BInfEgal extends BinaryOp {
    public BInfEgal(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" <= ");
        e2.print();
    }
}
class BEgal extends BinaryOp {
    public BEgal(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" = ");
        e2.print();
    }
}
class BDiff extends BinaryOp {
    public BDiff(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" = ");
        e2.print();
    }
}
class BSupEgal extends BinaryOp {
    public BSupEgal(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" >= ");
        e2.print();
    }
}
class BSup extends BinaryOp {
    public BSup(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public void print() {
        e1.print();
        System.out.print(" > ");
        e2.print();
    }
}
class ArrayGet extends Expr {
    protected Expr array;
    protected Expr e;
    public ArrayGet(Expr array, Expr e) {
        this.array = array;
        this.e = e;
    }
    public void print() {
        array.print();
        System.out.print("[");
        e.print();
        System.out.print("]");
    }
}
class NewArray extends Expr {
    protected Type t;
    protected Expr e;
    public NewArray(Type t, Expr e){
        this.t = t;
        this.e = e;
    }
    public void print() {
        System.out.print("new array of");
        t.print();
        System.out.print("[");
        e.print();
        System.out.print("]");
    }
}
class AppelFonction extends Expr {
    protected Appel a;
    protected ArrayList<Expr> args;
    public AppelFonction(Appel a) {
        this.a = a;
        this.args = null;
    }
    public AppelFonction(Appel a, ArrayList<Expr> args) {
        this.a = a;
        this.args = args;
    }
    public void print(){
        this.a.print();
        System.out.print("(");
        for (Expr e : args){
            e.print();
        }
        System.out.print(")");
    }
}

// INSTRUCTIONS
abstract class Inst {
    public abstract void print();
}
class Param extends Inst {
    protected String name;
    protected Type t;

    public Param(String name, Type t) {
        this.name = name;
        this.t = t;
    }

    public void print(){
        System.out.print(this.name+ ": ");
        this.t.print();
    }
}
class Variable extends Inst {
    protected ArrayList<Param> af;
    public Variable(ArrayList<Param> af) {
        this.af = af;
    }
    public void print() {}
}
class AffectVar extends Inst {
    protected String var;
    protected Expr e;
    public AffectVar(String var, Expr e) {
        this.var = var;
        this.e = e;
    }
    public void print() {
        System.out.print(var + " := ");
        e.print();
    }
}
class AffectArray extends Inst {
    protected Expr e1;
    protected Expr e2;
    protected Expr e3;
    public AffectArray(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }
    public void print(){
        this.e1.print();
        System.out.print("[");
        this.e2.print();
        System.out.print("] := ");
        this.e3.print();
    }
}
class TernaryCond extends Inst{
    protected Expr e1;
    protected Inst i2;
    protected Inst i3;
    public TernaryCond(Expr e1, Inst i2, Inst i3) {
        this.e1 = e1;
        this.i2 = i2;
        this.i3 = i3;
    }
    public void print(){
        System.out.print("if (");
        this.e1.print();
        System.out.print(") then (");
        this.i2.print();
        System.out.print(") else (");
        this.i3.print();
        System.out.print(")\n");
    }
}
class Skip extends Inst{
    public void print() {
        System.out.println("skip");
    }
}
class While extends Inst{
    protected Expr e;
    protected Inst i;
    public While(Expr e, Inst i){
        this.e = e;
        this.i = i;
    }
    public void print() {
        System.out.print("while (");
        this.e.print();
        System.out.print(") do (");
        this.i.print();
        System.out.print(")");
    }
}
class SequenceInstruc extends Inst{
    protected Inst i1, i2;
    public SequenceInstruc(Inst i1, Inst i2){
        this.i1 = i1;
        this.i2 = i2;
    }
    public void print() {
        this.i1.print();
        System.out.print("; ");
        this.i2.print();
    }
}


// CIBLE D'APPEL
abstract class Appel {
    abstract void print();
}
class Read extends Appel {
    public void print() {
        System.out.print("read");
    }
}
class Write extends Appel {
    public void print() {
        System.out.print("write");
    }
}
class Function extends Appel {
    protected String funcName;
    public Function(String funcName) {
        this.funcName = funcName;
    }
    public void print() {
        System.out.print(funcName);
    }
}

// PROCEDURES
class Proc {
    protected String n;
    protected ArrayList<Param> listParam;
    protected Type t;
    protected ArrayList<Variable> listVar;
    protected Inst i;
    public Proc(String n, ArrayList<Param> listParam, Type t, ArrayList<Variable> listVar, Inst i) {
        this.n = n;
        this.listParam = listParam;
        this.t = t;
        this.listVar = listVar;
        this.i = i;
    }
    // no param
    public Proc(String n, Type t, ArrayList<Variable> listVar, Inst i) {
        this.n = n;
        this.listParam = null;
        this.t = t;
        this.listVar = listVar;
        this.i = i;
    }
    // no type
    public Proc(String n, ArrayList<Param> listParam, ArrayList<Variable> listVar, Inst i) {
        this.n = n;
        this.listParam = listParam;
        this.t = null;
        this.listVar = listVar;
        this.i = i;
    }
    // no type and no param
    public Proc(String n, ArrayList<Variable> listVar, Inst i) {
        this.n = n;
        this.listParam = null;
        this.t = null;
        this.listVar = listVar;
        this.i = i;
    }
    // no var
    public Proc(String n, ArrayList<Param> listParam, Type t, Inst i) {
        this.n = n;
        this.listParam = listParam;
        this.t = t;
        this.listVar = null;
        this.i = i;
    }
    // no param and no var
    public Proc(String n, Type t, Inst i) {
        this.n = n;
        this.listParam = null;
        this.t = t;
        this.listVar = null;
        this.i = i;
    }
    // no vars and no type
    public Proc(String n, Inst i, ArrayList<Param> listParam) {
        this.n = n;
        this.listParam = listParam;
        this.t = null;
        this.listVar = null;
        this.i = i;
    }
    // no param, no var, no type
    public Proc(String n, Inst i) {
        this.n = n;
        this.listParam = null;
        this.t = null;
        this.listVar = null;
        this.i = i;
    }
}

// PROGRAMME
class Program {
    protected Variable listVar;
    protected ArrayList<Proc> listProc;
    protected Inst i;
    public Program(Variable listVar, ArrayList<Proc> listProc, Inst i){
        this.listVar = listVar;
        this.listProc = listProc;
        this.i = i;
    }
    public Program(ArrayList<Proc> listProc, Inst i){
        this.listVar = null;
        this.listProc = listProc;
        this.i = i;
    }
    public Program(Variable listVar, Inst i){
        this.listVar = listVar;
        this.listProc = null;
        this.i = i;
    }
    public Program(Inst i){
        this.listVar = null;
        this.listProc = null;
        this.i = i;
    }
    public void print() {
        this.listVar.print();
        String args = "";
        for (Proc proc : this.listProc) {
            args += proc.toString();
        }
        System.out.print(args);
        this.i.print();
    }
}
