****# XQuery
# Element
```dtd
<!ELEMENT [NAME] [REG_EXPR] [OPTION]>

<!-- [OPTION] -->
(#ANY)
(#EMPTY)
(#PCDATA) (=text)
```

# Attribute
```dtd
<!ATTLIST [EL_REFERENCE] [NAME] [TYPE] [OPTION]>

<!-- [TYPE] -->
CDATA

<!-- [OPTION] -->
#REQUIRED (for ID & IDREF)
#IMPLIED
#FIXED
```

# Let
```xquery
let $x := P return Q
```

## For
```xquery
for $x in P return Q
```

# Where
```xquery
for $x in P
    where Q
        return R
```

# Order by
```xquery
for $x in P
    order by Q desc
        return R
```

# If
```xquery
for $x in P
    if Q 
        then return R
        else ()
```

# Element Creation
```xquery
<el> {
    ... XQuery content ...
}
</el>
```

# Attribute Creation
```xquery
<el attribute = "{ ... Xquery content ...  "> {
    ... XQuery content ...
}
</el>
```

# Syntaxe générique
```xquery
element
    {el}
    {
        ... XQuery content ...
    }

    attribute
        {attr}
        {
            ... XQuery content ...
        }  
```

# Functions
```xquery
declare function myfunct(...param...) {
    ... XQuery content ...
}
```

