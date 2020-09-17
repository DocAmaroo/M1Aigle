# TD 1 : XML et DTDs

## Exercice 1
*<small> fait sur feuille / </small>*

## Exercice 2 

### XML1

```xml
<!DOCTYPE F [
    <!ELEMENT F (C*|B*)>
    <!ELEMENT C (A|D)>
    <!ELEMENT B (A|E)>
    <!ELEMENT A EMPTY>
    <!ELEMENT D EMPTY>
    <!ELEMENT E EMPTY>
]
```

ou encore

```xml
<!DOCTYPE F [
    <!ELEMENT F (C*, B*)>
    <!ELEMENT C (A, D)>
    <!ELEMENT B (A, E)>
    <!ELEMENT A EMPTY>
    <!ELEMENT D EMPTY>
    <!ELEMENT E EMPTY>
]
```

### XML2

``` xml
<!DOCTYPE D [
    <!ELEMENT D (C*, E*, B*, C*, E)>
    <!ELEMENT C EMPTY>
    <!ELEMENT B EMPTY>
    <!ELEMENT E EMPTY>
]>
```

ou encore

``` xml
<!DOCTYPE D [
    <!ELEMENT D ( (C*, E, B), (C*, E))>
    <!ELEMENT C EMPTY>
    <!ELEMENT B EMPTY>
    <!ELEMENT E EMPTY>
]>
```

### XML3

```xml
<!DOCTYPE A [
    <!ELEMENT A (C*, E*, C)>
    <!ELEMENT C EMPTY>
    <!ELEMENT E EMPTY>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A ( (C*, E*) | C)>
    <!ELEMENT C EMPTY>
    <!ELEMENT E EMPTY>
]>
```

### XML4

```xml
<!DOCTYPE B [
    <!ELEMENT B ((C*, B*), (E*, D*, E))*>
    <!ELEMENT C EMPTY>
    <!ELEMENT E EMPTY>
    <!ELEMENT D EMPTY>
]>
```

ou encore

```xml
<!DOCTYPE B [
    <!ELEMENT B ((C*, B*, E*, D*, E*))*>
    <!ELEMENT C EMPTY>
    <!ELEMENT E EMPTY>
    <!ELEMENT D EMPTY>
]>
```

### XML5

```xml
<!DOCTYPE A [
    <!ELEMENT A (A*) ANY>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A EMPTY>
]>
```

### XML6

```xml
<!DOCTYPE A [
    <!ELEMENT A (C*)>
    <!ELEMENT C (#PCDATA)>
    <!ATTLIST C id CDATA #REQUIRED>
    <!ATTLIST C friend CDATA #IMPLIED>
]>
```

ou encore

```xml
<!DOCTYPE A [
    <!ELEMENT A (C+)>
    <!ELEMENT C (#PCDATA)>
    <!ATTLIST C id CDATA #IMPLIED>
    <!ATTLIST C friend CDATA #IMPLIED>
]>
```

## Exercice 3

```xml
<!DOCTYPE tweet [

    <!-- tweet -->
    <!ELEMENT tweet (date, location, language, system, url*, (text+ |hashtag* | ref_user*)*)>
    <!ATTLIST tweet id ID #REQUIRED>
    <!ATTLIST tweet retweet CDATA>

    <!-- date -->
    <!ELEMENT date (day, month, year, hour, minute, second)>
    <!ELEMENT day (#PCDATA) #REQUIRED>
    <!ELEMENT month (#PCDATA) #REQUIRED>
    <!ELEMENT year (#PCDATA) #REQUIRED>
    <!ELEMENT hour (#PCDATA) #REQUIRED>
    <!ELEMENT minute (#PCDATA) #REQUIRED>
    <!ELEMENT second (#PCDATA) #REQUIRED>

    <!-- location -->
    <!ELEMENT location (longitude, latitude, city, country)>
    <!ELEMENT longitude (#PCDATA)>
    <!ELEMENT latitude (#PCDATA)>
    <!ELEMENT city (#PCDATA)>
    <!ELEMENT country (#PCDATA)>

    <!-- language -->
    <!ELEMENT language (#PCDATA)>

    <!-- system -->
    <!ELEMENT system (#PCDATA)>

    <!-- url -->
    <!ELEMENT a (#PCDATA)>
    <!ATTLIST a href CDATA #REQUIRED>

    <!-- text -->
    <!ELEMENT text (#PCDATA)>
    <!ATTLIST text font_type CDATA #REQUIRED>
    <!ATTLIST text font_size CDATA #REQUIRED>
    <!ATTLIST text font_color CDATA #REQUIRED>

    <!-- hashtag -->
    <!ELEMENT hashtag (#PCDATA)>
    <!ATTLIST hashtag font_type CDATA #REQUIRED>
    <!ATTLIST hashtag font_size CDATA #REQUIRED>
    <!ATTLIST hashtag font_color CDATA #REQUIRED>

    <!-- ref_user -->    
    <!ELEMENT ref_user (#PCDATA)>
    <!ATTLIST ref_user font_type CDATA #REQUIRED>
    <!ATTLIST ref_user font_size CDATA #REQUIRED>
    <!ATTLIST ref_user font_color CDATA #REQUIRED>

    <!-- retweet -->
    <!ELEMENT retweet (tweet)>
    <!ATTLIST retweet id ID #REQUIRED> <!-- pas convaincu sur ça -->
    <!ATTLIST retweet nom CDATA #REQUIRED>
    <!ATTLIST retweet pp_url CDATA #REQUIRED>

    <!-- profile -->
    <!ELEMENT profile (description, pp, followers, follow)>
    <!ELEMENT description (#PCDATA) #REQUIRED>
    <!ELEMENT pp (#PCDATA) #REQUIRED>
    <!ELEMENT followers (#PCDATA)>
    <!ELEMENT follow (#PCDATA)>
]>
```