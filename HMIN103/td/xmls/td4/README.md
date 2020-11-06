# Exo 1

1. 
```
for $train in /gare/train 
    where $train//bar 
        return $train/@numero/string()
```

2. 
```
let $id_reservations := //resa/@id 
for $usager in /gare/usager 
    where $usager/@id = $id_reservations 
        return $usager/@nom/string()
```

3. 
```
let $x := for $resa in //resa order by $resa/@numero 
    return $resa return $x[last()]
```

4. 
```
for $x in //train 
    where $x[count(.//resa) > 1] 
        return $x/@numero/string()
```
5. 
```
for $x in //usager 
    where count(//resa[@id = $x/@id]) = 2 
        return $x/@nom/string()
```
6. 
```
for $x in //usager 
    where count(//resa[@id = $x/@id]) = 0 
        return $x/@nom/string()
```

# Exo 2

1. All the items
```
//item
```

2. The keywords in annotations of closed auctions
```
for $x in //closed_auctions
    return $x//keyword
```

3. All the keywords
```
//keywords
```

4. The keywords in a paragraph item
```
for $x in //item 
    for $y in $x//keyword 
        return $y
```

5. The (either North or South.) American items
```
let $x := //namerica/item 
let $y := //samerica/item 
return ($x,$y)
```

6. The paragraph items containing a keyword
```
for $x in //item 
    for $y in $x//text 
        where exists($y//keyword)
            return $y
```

7. The mails containing a keyword
```
for $x in //mail 
    where $x//keyword 
        return $x
```

8. The open auctions in which a certain person issued a bid before another person
```
for $x in //open_auction 
    return $x[count(.//bidder) > 1]
```

9. The past bidders of a given open auction
```
# pas correcte mais un début
let $sdate := //open_auction[@id='open_auction0']/bidder[last()]/date/text() return $sdate

```

10. The items that follow, in document order, a given item
```
for $x in //regions//item[@id='item10'] $x/following::item
ou
for $x in //regions//item[@id='item10'] $x/following-sibling::item)
```

11. The text nodes that are contained in the keywords of the description element of a given item
```
for $x in //item[@id='item20']/description//keyword
    return $x
```

12. People having an address and either a phone or a homepage
```
for $x in //person 
    where $x//address 
        where $x//homepage or $x//phone 
            return $x
```

13. People having no homepage
```
for $x in //person
	where not ($x//homepage)
		return $x
```

14. The initial and last bidder of all open auctions
```
let $x := //bidder[last()]
let $y := //bidder[position() = 1]
return ($x,$y)
```

15. The open auctions having more than 5 bidders
```
for $x in //open_auction 
    where $x[count($x//bidder) > 5] 
        return $x
```

16. Mails sent in September
```
for $x in //mail
    where matches($x//date, '09/.*/.*') 
        return $x
```

17. The items whose description contains the word ‘gold’
```
let $x := //description
return $x[contains(text,'gold')]
```
