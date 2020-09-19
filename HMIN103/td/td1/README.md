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

    <!-- tweet_data -->
    <!ELEMENT tweet_data (tweet)*>

    <!-- tweet -->
    <!ELEMENT tweet (date, location, language, system, corps*) >
    <!ATTLIST tweet id ID #REQUIRED>
    <!ATTLIST nb_retweet CDATA>
    <!ATTLIST system CDATA>
    <!ATTLIST language CDATA>

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

    <!-- corps -->
    <!ELEMENT corps (text | hashtag | ref_user)+>

    <!-- text -->
    <!ELEMENT text (#PCDATA)>
    <!ATTLIST text type CDATA "serif">
    <!ATTLIST text size CDATA "12">
    <!ATTLIST text color CDATA "black">

    <!-- hashtag -->
    <!ELEMENT hashtag (#PCDATA)>
    <!ATTLIST hashtag type CDATA "serif">
    <!ATTLIST hashtag size CDATA "12">
    <!ATTLIST hashtag color CDATA "blue">

    <!-- ref_user -->    
    <!ELEMENT ref_user (#PCDATA)>
    <!ATTLIST ref_user type CDATA "serif">
    <!ATTLIST ref_user size CDATA "12">
    <!ATTLIST ref_user color CDATA "blue">

    <!-- retweet -->
    <!ELEMENT retweet (tweet)>
    <!ATTLIST retweet id ID #REQUIRED> <!-- pas convaincu sur ça -->
    <!ATTLIST retweet nom CDATA #REQUIRED>
    <!ATTLIST retweet pp_url CDATA #REQUIRED>

    <!-- profile -->
    <!ELEMENT profile (description, pp, followers, follow)>
    <!ATTLIST profile idUser ID #REQUIRED>
    <!ELEMENT description (#PCDATA) #REQUIRED>
    <!ELEMENT pp (#PCDATA) #REQUIRED>
    <!ELEMENT followers (#PCDATA)>
    <!ELEMENT follow (#PCDATA)>
]>
```