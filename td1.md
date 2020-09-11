1. sur feuille
2. 

### XML1

```xml
<!DOCTYPE F [
    <!ELEMENT F (C*|B*)>
    <!ELEMENT C (A|D)>
    <!ELEMENT B (A|E)>
    <!ELEMENT A #EMPTY>
    <!ELEMENT D #EMPTY>
    <!ELEMENT E #EMPTY>
]
```

ou encore

```xml
<!DOCTYPE F [
    <!ELEMENT F (C*, B*)>
    <!ELEMENT C (A, D)>
    <!ELEMENT B (A, E)>
    <!ELEMENT A #EMPTY>
    <!ELEMENT D #EMPTY>
    <!ELEMENT E #EMPTY>
]
```

### XML2

``` xml
<!DOCTYPE D [
    <!ELEMENT D (C*, E*, B*, C*, E)>
    <!ELEMENT C #EMPTY>
    <!ELEMENT D #EMPTY>
    <!ELEMENT E #EMPTY>
]>
```

ou encore

``` xml
<!DOCTYPE D [
    <!ELEMENT D ( (C*, E, B), (C*, E))>
    <!ELEMENT C #EMPTY>
    <!ELEMENT B #EMPTY>
    <!ELEMENT E #EMPTY>
]>
```

### XML3

```xml
<!DOCTYPE A [
    <!ELEMENT A (C*, E*, C)>
    <!ELEMENT C #EMPTY>
    <!ELEMENT E #EMPTY>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A ( (C*, E*) | C)>
    <!ELEMENT C #EMPTY>
    <!ELEMENT E #EMPTY>
]>
```

### XML4

```xml
<!DOCTYPE B [
    <!ELEMENT B ((C*, B*), (E*, D*, E))*>
    <!ELEMENT C #EMPTY>
    <!ELEMENT E #EMPTY>
    <!ELEMENT D #EMPTY>
]>
```

ou encore

```xml
<!DOCTYPE B [
    <!ELEMENT B ((C*, B*, E*, D*, E*))*>
    <!ELEMENT C #EMPTY>
    <!ELEMENT E #EMPTY>
    <!ELEMENT D #EMPTY>
]>
```

### XML5

```xml
<!DOCTYPE A [
    <!ELEMENT A (A*) #ANY>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A #EMPTY>
]>
```

### XML6

```xml
<!DOCTYPE A [
    <!ELEMENT A (A*) #ANY>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A #EMPTY>
]>
```