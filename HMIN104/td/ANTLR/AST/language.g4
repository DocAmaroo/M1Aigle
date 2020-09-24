grammar language;

program:
    variable*
    definition*;

definition:
    'f (' Name ':' type ') :' type?
    variable*
    instruction;

instruction:
    Name ':=' expression;

expression:
    constante
    | variable
    | unaryOp expression
    | expression binaryOp expression
    | appel '(' expression* ')'
    | expression '[' expression ']'
    | 'new array of' type '[' expression ']';

appel:
    'read'
    | 'write'
    | Name;

ternaryCond:
    'if' expression 'then' expression 'else' expression;

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

variable:
    'var' Name ':' type;

constante:
    Number
    | 'true'
    | 'false';

type:
    'integer'
    | 'boolean'
    | 'array of' type;

Number:
    ('0'..'9')+;

Name:
    [a-zA-Z0-9]+;

WS: [ \t\r\n]+ -> skip;