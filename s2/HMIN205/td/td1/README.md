# HMIN205 - TD1

## Exercice 1 - Les différents parties d'une application Android

### Question 1

/

### Question 2

Comprends pas ce qu'il veut 

### Question 3

Le premier code est le Manifest, le fichier de configuration de notre application Android.
Le second code est la vue contenant le code de notre activitée principales de notre application

### Question 4

```xml
<EditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:inputType="text"/>
```

## Exercice 2 - Les ressources

### Question 1

Dans ce code on utilise les chaîne de caractère global définit dans le fichier string.xml.

### Question 2
Fichier : *res/strings/string.xml*
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
  ... idem ...
  <!-- On peut ajouter notre phrase ici -->
  <string name="home_msg">Ma première application Android</string>
</ressources>
```

Fichier : *res/layout/layout_file_name.xml*
```xml
<?xml version="1.0" encoding="utf-8"?>
... (idem) ...
  <!-- On ajoute un élément TextView -->
  <TextView
    android:id="@+id/home_msg"
    android:layout_width="wrap-content"
    android:layout_height="wrap-content"
    android:text="@string/home_msg" />

</LinearLayout>
```

## Exercice 3 - Les layouts (Gabarits)

![voir ex3.png](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN205/td/td1/ex3.jpg)
