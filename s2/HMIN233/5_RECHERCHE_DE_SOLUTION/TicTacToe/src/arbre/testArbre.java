package src.arbre;

import java.util.ArrayList;

public class testArbre {

    public static void main(String[] args) {
        TreeNode<String> racine = new TreeNode<String>("racine");

        // arbre de test
        for (int i = 0; i < 3; i++) {
            TreeNode<String> noeud = new TreeNode<String>(racine, "niveau 1." + i);
            for (int j = 0; j < 2; j++) {
                noeud.addChild(new TreeNode<>(racine, "niveau 2." + j));
            }
            racine.addChild(noeud);
        }

        System.out.println("\n[+] Parcours en largeur");
        TreeNode.breathFirstDisplay(racine);

        System.out.println("\n[+] Parcours en profondeur");
        TreeNode.depthFirstDisplay(racine);
    }



    public static void displayArbre(TreeNode<String> racine) {
		System.out.println(racine.toString());

		ArrayList<TreeNode<String>> children = racine.getChildren();
		if (children != null) {
            for (TreeNode<String> child : children) {
                displayArbre(child);
            }
        }
    }
}
