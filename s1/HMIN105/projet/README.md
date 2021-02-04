# Information

:mortar_board: Etudiants sur le projet

Canta Thomas

Fontaine Quentin

# Fonctionnalitée

:white_check_mark: Implémenter
- Zone de mémoire partagée
- Tableau de sémaphores
- Commande/Libération de données
- Possibilités de faire plusieurs demande en une seule commandes
- Réception de toutes les données lors d'une nouvelle mise à jour

:construction: Non implémenter
- Possibilitée, après une commande, de refaire une nouvelle demande
- TCP

:warning: Avertissement
- !! Ne surtout pas donner de valeur < 1 pour les cpu ou les go !!
- Pendant la phase de "connexion" vous ne verrais pas les mises à jour s'effectuer.
- Si pendant votre saisie, un affichage survient en plein milieu, faite comme si de rien n'était.


# Compilation et éxécution

#Lancement

```bash
#création des dossiers bin et obj
#A n'exécuter qu'une seule fois! 
make init

#Si les dossiers ne se sont pas crées
sudo mkdir bin obj

#Compilation
make

#Lancement du serveur
./bin/server datacenter

#Lancement d'un/des clients
./bin/client
```
