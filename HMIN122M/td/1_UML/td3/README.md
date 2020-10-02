:triangular_flag_on_post: **Attente**: expliquer diff entre les requêtes et pourquoi les stats sont meilleurs

# 1. Plan d'exécution ORACLE

## 1.1 Sélection

Question 1 :
...

Question 2 :
Attendre le message de Kevinou...


Question 3 :

Question 5 :
SELECT departement.nom FROM ville, departement WHERE dep=id AND insee = '34172';

Question 6 : 
SELECT departement.nom FROM ville, departement WHERE dep=id GROUP BY departement.nom;

Question 7 : 
SELECT ville.nom, departement.nom FROM ville, departement WHERE dep=id AND id='91';

Question 8 :
SELECT /*+use_nl (departement ville)*/ departement.nom FROM ville, departement WHERE dep=id AND insee = '34172';
SELECT /*+use_nl (departement ville)*/ departement.nom FROM ville, departement WHERE dep=id GROUP BY departement.nom;
SELECT /*+use_nl (departement ville)*/ ville.nom, departement.nom FROM ville, departement WHERE dep=id AND id='91';

Question 9 :
le fun !

Question 10 :   
SELECT ville.nom AS VILLE, departement.nom AS DEPT, region.nom AS REGION FROM ville, departement, region WHERE ville.dep = departement.id AND departement.reg = region.id GROUP BY ville.nom, departement.nom, region.nom;

Question 11 :
create index idx_reg_dep on departement (reg);


