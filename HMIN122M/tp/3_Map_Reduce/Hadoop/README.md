# Bug de Hadoop sous Windows
L'utilisation de Hadoop sous Windows entraîne la levée d'une exception de type IOException (Failed to set permissions of path: 
```diff
-\tmp\hadoop-user\mapred\staging\user722309568\.staging to 0700). 
```
C'est un bug connu de Hadoop (cf. https://issues.apache.org/jira/browse/HADOOP-7682), qu'il est possible de résoudre de la manière suivante :

1. Télécharger le jar suivant https://github.com/congainc/patch-hadoop_7682-1.0.x-win/downloads
2. Déplacer le jar dans TP_HMIN122M-hadoop/lib-dev
3. Ajouter le .jar au build path
4. Dans le package explorer, clic droit sur le projet /Build Path/Configure Build Path/Librairies/Add external jars
5. Ajouter le .jar précédemment téléchargé
6. Changer une valeur de la configuration du job avec la méthode suivante
```java
public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    conf.set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");
    ...
```

# Problème sur les nouveaux postes informatique
L'exception levée sur les nouveaux postes informatiques est dûe au nouveau mécanisme d'authentification de l'UM, où votre nom utilisateur correspond à votre adresse e-mail ; Kerberos, le protocole d'authentification utilisé par Hadoop utilise des règles différentes pour les adresses email.

La solution est de modifier votre nom utilisateur lors de l'exécution du programme.
Pour ce faire il suffit d'ajouter la variable d'environnement HADOOP_USER_NAME avant l'exécution du programme.
Le plus simple est de l'ajouter directement via Eclipse où l'on va modifier la configuration à l'exécution.
1. Faites un click droit sur la racine de votre projet, situé dans l'onglet à droite, suivez "Run as/Run Configurations"
2. Onglet "Environment"
3. New...
4. Name: HADOOP_USER_NAME, Value: user

Exécutez le programme WordCount et vérifiez que tout fonctionne.