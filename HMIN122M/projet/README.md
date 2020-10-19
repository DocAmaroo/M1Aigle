1. (~1 page)
objectifs :
- Amasser plus d'utilisateurs
- Organiser plus d'évènements
- Sponsorisée des évènements
- Offrir aux utilisateurs l'opportunité de jouer au poker en ligne sur une plateforme sécurisée.
- Financement à l'aide de pub

qui va vouloir nos services ? 
- 18/60 ans majoritairement
- Majoritairement des personnes moyennes+/aisée
- Des personnes fougueuses qui aime jouer avec leur argent

Nos services permettent aux clients de
- Gagner de l'argent
- Passer du temps
- L'esprit de compétition
- Ressentis d'adrénaline

Concurrents :
- Pokerstars
- PMU

nos points forts :
- On allie poker et paris sportif sur une même platforme
- On as une bonne audiance
- Sponsorisé

nos points faible :
- le surplus de service peu faire peur pour de nouveau joueur

revenus :
/

infos utiles :
/

2. Indiquez les actions / opérations (e.g., ventes, livraisons) à tracer pour récupérer ces informations. 
- transactions
- pertes / gains


3. Pour chaque action / opération, proposez au moins trois traitements possibles (i.e., requêtes analytiques) permettant d’aider à la prise de décision sur le sujet.

4. Ordonnez les actions par ordre d’importance / rentabilité potentielle (e.g., augmentation des ventes vs. utilisation optimale de l’espace de stockage dans le magasin).

La conception de l’entrepôt dépendra exclusivement des traitements que vous avez indiqués.

# La conception

5. Identifiez les deux actions / opérations les plus importantes à analyser.
- gestion des transactions
- 

6. Pour chaque actions / opérations, concevez un data-mart indépendant (c’est à dire, un modèle en étoile)

   a. Pour l’action / opération la plus importante, prévoyez une modélisation détaillée dans la table des faits.

   b. Pour la deuxième action, prévoyez une modélisation moins détaillée (snapshot, transactions, record-update)

   c. Pour chaque action / opération, donnez la liste des faits (ou mesures) à enregistrer dans l’entrepôt.

   d. Dites s’il s’agit d’une mesure additive, semi-additive, ou non-additive.

7. Définissez les dimensions nécessaires aux modèles. Pour chaque action, indiquez au moins 5 dimensions.

   a. Pour chaque dimension, prévoir une dizaine d’attributs.

8. Est-il possible de répondre aux traitements que vous avez indiqué avec le modèle que vous avez mis en place ? Expliquez pourquoi et comment.

9. Pour tester la pertinence de votre modèle, donner un exemple d’instance de l’entrepôt de données (2 ou 3 lignes par table suffisent).

10. Estimez la taille des tables de l’entrepôt (en terme du nombre de lignes) sur 12 mois. Est ce que cette taille est raisonnable ? Justifiez votre réponse.

Attention : On ne vous demande pas de faire le modèle conceptuel des données (on se focalise directement sur le modèle logique de l’entrepôt).


# L’implémentation et le requêtage 

11. Implantez en ORACLE les tables de faits et des dimensions prévues par votre modèle. (En cas de problème avec ORACLE utiliser SQL Live ou installer POSTGRES : www.postgresql.org/download )

   a. Utilisez des vues virtuelles pour les dimensions partagées.

12. Proposez 10 requêtes analytiques correspondant aux traitements que vous avez indiqués.

13. Donnez l’ensemble des vues matérialisées permettant de répondre à l’ensemble de vos requêtes. Utiliser la technique basée sur le treillis d'agrégation présentée en cours.