grammar Language;

program:
    variable*
    definition*
    instruction*;

definition:
    'f (' Name ':' type ') :' type?
    variable?
    instruction*;

instruction:
    Name ':=' expression
    | expression '[' expression ']'
    | ternaryCond
    | 'while' expression 'do' instruction
    | appel'('expression*')'
    | 'skip'
    | instruction';'instruction;

expression:
    constante
    | Name
    | unaryOp expression
    | expression binaryOp expression
    | appel'('expression*')'
    | expression '['expression']'
    | 'new array of' type '['expression']';

appel:
    'read'
    | 'write'
    | Name;

ternaryCond:
    'if' expression 'then' instruction+ 'else' instruction+;

binaryOp:
    '+'
    | '-'
    | '*'
    | '/'
    | 'and'
    | 'or'
    | '<'
    | '<='
    | '='
    | '!='
    | '>='
    | '>';

unaryOp:
    '-'
    | 'not';

variable:
    'var' Name ':' type;

constante returns [Cst const]:
    Number {$const = new CstInt();}
    | 'true' {$const = new CstTrue();}
    | 'false' {$const = new CstFalse();};

type returns [Type t]:
    'integer' {$t = new TypeInt();}
    | 'boolean' {$t = new TypeBool();}
    | 'array of ' t2=type {$t = new TypeArray($t2.t);};

Number:
    ('0'..'9')+;

Name:
    [a-zA-Z0-9]+;

WS: [ \t\r\n]+ -> skip;