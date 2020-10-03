# Exo 1

1. 
```
for $train in /gare/train where $train//bar return $train/@numero/string()
```

2. 
```
let $id_reservations := //resa/@id for $usager in /gare/usager where $usager/@id = $id_reservations return $usager/@nom/string()
```

3. 
```
let $x := for $resa in //resa order by $resa/@numero return $resa return $x[last()]
```

4. 
```
for $x in //train where $x[count(.//resa) > 1] return $x/@numero/string()
```
5. 
```
for $x in //usager where count(//resa[@id = $x/@id]) = 2 return $x/@nom/string()
```
6. 
```
for $x in //usager where count(//resa[@id = $x/@id]) = 0 return $x/@nom/string()
```

# Exo 2

1. All the items
```
//item
```

2. The keywords in annotations of closed auctions
```
for $x in //closed_auctions return $x//keyword
```

3. All the keywords

4. The keywords in a paragraph item

5. The (either North or South.) American items

6. The paragraph items containing a keyword

7. The mails containing a keyword

8. The open auctions in which a certain person issued a bid before another
person

9. The past bidders of a given open auction

10. The items that follow, in document order, a given item

11. The text nodes that are contained in the keywords of the description element
of a given item

12. People having an address and either a phone or a homepage

13. People having no homepage

14. The initial and last bidder of all open auctions

15. The open auctions having more than 5 bidders

16. Mails sent in September

17. The items whose description contains the word ‘gold’