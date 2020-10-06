grammar Language;

listExpr returns [ ArrayList<Expr> l_expr ] @init { $l_expr = new ArrayList<Expr>(); } :
    ( e=expression { $l_expr.add($e.e);})+;

listParam returns [ ArrayList<Param> l_params ] @init { $l_params = new ArrayList<Param>(); } :
    ( a=param { $l_params.add($a.p);})+;

listVar returns [ ArrayList<Variable> l_vars ] @init { $l_vars = new ArrayList<Variable>(); } :
    ( v=variable { $l_vars.add($v.var);})+;

listProcs returns [ArrayList<Proc> l_procs] @init { $l_procs = new ArrayList<Proc>(); } :
    (p=proc {$l_procs.add($p.prc);})+;

program returns [Program pr] :
    lvar=variable ldef=listProcs i=instruction {$pr = new Program($lvar.var, $ldef.l_procs, $i.i);}
    | linst=listProcs i=instruction {$pr = new Program($linst.l_procs, $i.i);}
    | ldef=listProcs i=instruction {$pr = new Program($ldef.l_procs, $i.i);}
    | i=instruction {$pr = new Program($i.i);};

proc returns [Proc prc] :
    n=Name ' (' lp=listParam ') :' t=type lv=listVar i=instruction
        {$prc = new Proc($n.text, $lp.l_params, $t.t, $lv.l_vars, $i.i);} //all
    | n=Name ' (' ') :' t=type lv=listVar i=instruction
        {$prc = new Proc($n.text, $t.t, $lv.l_vars, $i.i);} // no params
    | n=Name ' (' lp=listParam ') :' lv=listVar i=instruction
              {$prc = new Proc($n.text, $lp.l_params, $lv.l_vars, $i.i);} // no type
    | n=Name ' (' ') :' lv=listVar i=instruction
        {$prc = new Proc($n.text, $lv.l_vars, $i.i);} // no type and no param
    | n=Name ' (' lp=listParam ') :' t=type i=instruction
        {$prc = new Proc($n.text, $lp.l_params, $t.t, $i.i);} // no var
    | n=Name ' (' ') :' t=type i=instruction
        {$prc = new Proc($n.text, $t.t, $i.i);} // no param and no var
    | n=Name ' (' lp=listParam ') :' i=instruction
        {$prc = new Proc($n.text, $i.i, $lp.l_params);} // no var and no type
    | n=Name ' (' ') :' i=instruction
            {$prc = new Proc($n.text, $i.i);}; // no param, no var, no type

instruction returns [Inst i]:
    n=Name ':=' e1=expression {$i = new AffectVar($n.text, $e1.e);}
    | itern=ternaryCond {$i = $itern.t;}
    | ifonc=fonctionIns {$i = $ifonc.fi;}
    | e1=expression '[' e2=expression '] := ' e3=expression {$i = new AffectArray($e1.e, $e2.e, $e3.e);}
    | 'while' e=expression 'do' i2=instruction {$i = new While($e.e, $i2.i);}
    | 'skip' {$i = new Skip();}
    | i1=instruction';'i2=instruction {$i = new SequenceInstruc($i1.i, $i2.i);};

expression returns [Expr e]:
    c=constante {$e = $c.e;}
    | n=Name {$e = new Name($n.text);}
    // unaryOp
    | '-' e1=expression {$e = new UMinus($e1.e);}
    | 'not' e1=expression {$e = new UNeg($e1.e);}
    // binaryOp
    | e1=expression '+' e2=expression {$e = new BAdd($e1.e, $e2.e);}
    | e1=expression '-' e2=expression {$e = new BSub($e1.e, $e2.e);}
    | e1=expression '*' e2=expression {$e = new BMul($e1.e, $e2.e);}
    | e1=expression '/' e2=expression {$e = new BDiv($e1.e, $e2.e);}
    | e1=expression 'and' e2=expression {$e = new BAnd($e1.e, $e2.e);}
    | e1=expression '+' e2=expression {$e = new BOr($e1.e, $e2.e);}
    | e1=expression '<' e2=expression {$e = new BInf($e1.e, $e2.e);}
    | e1=expression '<=' e2=expression {$e = new BInfEgal($e1.e, $e2.e);}
    | e1=expression '=' e2=expression {$e = new BEgal($e1.e, $e2.e);}
    | e1=expression '!=' e2=expression {$e = new BDiff($e1.e, $e2.e);}
    | e1=expression '>=' e2=expression {$e = new BSupEgal($e1.e, $e2.e);}
    | e1=expression '>' e2=expression {$e = new BSup($e1.e, $e2.e);}
    // fonction
    | efonc=fonctionExpr {$e = $efonc.fe;}
    // array access
    | e1=expression '['e2=expression']' {$e = new ArrayGet($e1.e, $e2.e);}
    // newArray
    | 'new array of' t=type '['e1=expression']' {$e = new NewArray($t.t, $e1.e);};

ternaryCond returns [TernaryCond t]:
    'if' e=expression 'then' i1=instruction 'else' i2=instruction {$t = new TernaryCond($e.e, $i1.i, $i2.i);};

// CIBLE D'APPEL
appel returns [Appel a]:
    'read' {$a = new Read();}
    | 'write' {$a = new Write();}
    | name=Name {$a = new Function($name.text);};

// APPEL AU FONCTION
fonctionExpr returns [AppelFonctionExpr fe]:
    a=appel '(' le=listExpr ')' {$fe = new AppelFonctionExpr($a.a, $le.l_expr);}
    | a=appel '()' {$fe = new AppelFonctionExpr($a.a);};

fonctionIns returns [AppelFonctionInst fi]:
    a=appel '(' le=listExpr ')' {$fi = new AppelFonctionInst($a.a, $le.l_expr);}
    | a=appel '()' {$fi = new AppelFonctionInst($a.a);};

// PARAM
param returns [Param p]:
    '(' n=Name ':' t=type ')' {$p = new Param($n.text, $t.t);}
    | n=Name ':' t=type {$p = new Param($n.text, $t.t);};

// Variable
variable returns [Variable var]:
    'var' lv=listParam {$var = new Variable($lv.l_params);};

constante returns [Expr e]:
    n=Number {$e = new CstInt($n.text);}
    | 'true' {$e = new CstTrue();}
    | 'false' {$e = new CstFalse();};

type returns [Type t]:
    'integer' {$t = new TypeInt();}
    | 'boolean' {$t = new TypeBool();}
    | 'array of ' t2=type {$t = new TypeArray($t2.t);};

Number:
    ('0'..'9')+;

Name:
    ([a-z]+ | [A-Z]+);

WS: [ \t\r\n]+ -> skip;