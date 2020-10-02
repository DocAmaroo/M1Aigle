EXERCICE 1 :
1. /FILMS/FILM/TITRE/text()
2. /FILMS/FILM[ANNEE = '1990']/TITRE/text()
3. /FILMS/FILM[TITRE = 'Alien']/RESUME
4. /FILMS/FILM[last()]
5. /FILMS/FILM[ANNEE = '1990'][last()]
6. /FILMS/FILM[RESUME]
7. /FILMS/FILM[not(RESUME)]
8. /FILMS/FILM[TITRE = "Vertigo"]/ROLES/ROLE/text()
9. /FILMS/ARTISTE[@id=/FILMS/FILM[TITRE="Vertigo"]/MES/@idref]
10. /FILMS/FILM[MES/@idref=/FILMS/FILM[TITRE='Vertigo']/MES/@idref]
11. /FILMS/FILM[contains(TITRE, 'V')]
12. /FILMS/FILM[ROLES/ROLE[@idref=/FILMS/ARTISTE[ACTPNOM='Bruce' and ACTNOM='Willis']/@id]]/TITRE
13. /FILMS/FILM[TITRE='Reservoir dogs']/ROLES/ROLE[@idref = /FILMS/ARTISTE[ACTNOM='Keitel' and ACTPNOM='Harvey']/@id]
14. /FILMS/ARTISTE[not(ACTNOM='Keitel') and not(ACTPNOM='Harvey')][@id = /FILMS/FILM[TITRE = 'Reservoir dogs']/ROLES/ROLE/@idref]
15. //*[count(*) = 3]
16. /FILMS/node()[name()=contains(node(),"TI")]
17. /FILMS/FILM[preceding::TITRE = "Shining"]/TITRE/text()
/FILMS/FILM[preceding-sibling::FILM/TITRE = "Shining"]/TITRE/text()

EXERCICE 2 :
