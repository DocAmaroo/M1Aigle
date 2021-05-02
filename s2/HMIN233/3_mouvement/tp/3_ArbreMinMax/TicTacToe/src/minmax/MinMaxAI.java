package src.minmax;

import src.arbre.TreeNode;

import java.util.ArrayList;

public class MinMaxAI {

    private TreeNode<TTTState> root;

    public MinMaxAI() {
    }

    public void informAIMove(int x, int y) {
        // informer l'IA sur le coup du joueur humain
    }

    public boolean playAITurn(int coord[]) {
        // donner le coup de l'IA
        // coord[0] = x
        // coord[1] = y
        // retourner vrai si la partie est termié , faux sinon

        coord[0] = 0;
        coord[1] = 0;
        return false;
    }

    public TreeNode<TTTState> getRoot() {
        return root;
    }

    public void makeTree() {
        TTTState state = new TTTState();
        root = new TreeNode<TTTState>(null, state);
        rectTree(root);
    }

    private void rectTree(TreeNode<TTTState> tree) {
        for (TTTState state : tree.getData().nextStates()) {
            TreeNode<TTTState> node = new TreeNode<>(tree, state);
            tree.addChild(node);

            // --- Si c'est match nul
            if (state.winner() == -1)
                rectTree(node);
        }

    }
}
