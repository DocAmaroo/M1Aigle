# HMIN205 - TD1

## Exercice 1 - Les différents parties d'une application Android

### Question 1

Ouai ouai j'ai observé

### Question 2

```java
EditText et = new EditText(this);
LinearLayout ll = new LinearLayout(this);
ll.setOrientation(LinearLayout.VERTICAL);
ll.addView(tv);
ll.addView(et);
setContentView(ll);
```

### Question 3

Le premier code est le Manifest, le fichier de configuration de notre application Android.

Le second code est la vue contenant le code de l'activitée principale de notre application.

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

![voir ex3.jpg](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN205/td_tp/td_tp1/ex3.jpg)

## Exercice 4 - Evènements utilisateurs

### Question 1

Lors du click sur le bouton celui-ci affichera un message (toast) pendant une longue durée.

### Question 2

Dans main.xml
```xml
  <!-- On ajoute notre second bouton à la suite du premier -->
  <Button
    android:id="@+id/my_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/secondbutton" />
</LinearLayout>
```

Dans MainActivity.java
```java
// ... idem
public class MainActivity extends Activity {
  
  private Button button;
  private Button myButton;

  // ...idem
    myButton = (Button) findViewById(R.id.myButtonId);
    myButton.setOnClickListener(new OnClickListener(){ 
      @Override
      public void onClick(View arg0) {
        Toast.makeText(getApplicationContext(), "Message My Button", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
```

### Question 3

Dans MainActivity.java
```java
// ... idem
public class MainActivity extends Activity {
  
  private Button button;
  private Button myButton;
  private TextView text;

  // ...idem
    myButton = (Button) findViewById(R.id.myButtonId);
    myButton.setOnClickListener(new OnClickListener(){ 
      @Override
      public void onClick(View v) {
        text.setText("Exercice 4");

        if(!visible){
          text.setVisibility(View.VISIBLE);
          visible=true;
        }else{
          text.setVisibility(View.INVISIBLE);
          visible=false;
        }
      }
    });
  }
}
```

### Question 4

Avec différent fichier values (values-en, values-fr, ...) contenant les différentes strings universelles de notre application, on peut utiliser un Toast de cette manière:
```java
Toast.makeText(getApplicationContext(), getResources().getString(R.string.hello), Toast.LENGTH_LONG).show();
```


### Question 5

```java
myButton.setOnLongClickListener(new OnLongClickListener(){ 
  @Override
  public void onLongClick(View v) {
    Toast toast = Toast.makeText(getApplicationContext(), "Message My Button", Toast.LENGTH_SHORT).show();
  }
});
```

## Exercice 5

Affichage :
  - Trois checkbox
  - Un bouton

Quand la checkbox d'id *windows_option* est coché, le message toast suivant : "Bro, try Linux :)" s'affiche.

Quand on click sur le bouton, un message toast montrant le nom suivis de l'état d'une option (coché ou non) s'affiche.

## Exercice 6

![voir ex6.jpg](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN205/td_tp/td_tp1/ex6.jpg)