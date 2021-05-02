package src.minmax;

import com.sun.source.tree.Tree;
import src.arbre.TreeNode;

public class MinMax {
    public static void main(String[] args) {

        TTTState state = new TTTState();

        MinMaxAI ai = new MinMaxAI();
        ai.makeTree();
        TreeNode.depthFirstDisplay(ai.getRoot());

        TTTGame game = new TTTGame();
        //ajouter l'ia au jeu, sinon on joue tout seul
        //game.setAI(ai);
        // si l'ia joue le premier coup
        //game.playAITurn();
        game.setVisible(true);
    }
}
