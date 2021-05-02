package src.minmax;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.ArrayList;

// une petite classe pour faire l'interaction
// vous pouvez l'ameliorer si vous voulez
public class TTTGame extends JFrame {
    MinMaxAI AI = null;
    boolean end = false;
    public final int boardSize = 3;
    public static JPanel mainPanel;
    public String turn = "O";
    ArrayList<ArrayList<MyButton>> board;

    public class MyButton extends JButton {
        public int x, y;
    }

    ;

    public TTTGame() {
        super("TicTacToe");
        board = new ArrayList<>();
        setSize(new Dimension(boardSize * 100, boardSize * 100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(boardSize, boardSize));
        for (int i = 0; i < boardSize; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < boardSize; j++) {
                MyButton square = new MyButton();
                board.get(i).add(square);
                square.x = j;
                square.y = i;
                square.setContentAreaFilled(false);
                square.setFont(new Font("Arial", Font.PLAIN, 80));
                square.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!end && square.getLabel().equals(""))
                            playTurn(square);
                        else
                            restart();
                    }
                });
                mainPanel.add(square);
            }
        }
        this.add(mainPanel);
    }

    public void restart() {
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                board.get(i).get(j).setLabel("");
    }

    private void playTurn(MyButton square) {
        board.get(square.y).get(square.x).setLabel(turn);
        if (AI != null) {
            AI.informAIMove(square.x, square.y);
            switchTurn();
            playAITurn();
        } else
            switchTurn();
    }

    public void setAI(MinMaxAI ai) {
        AI = ai;
    }

    public void playAITurn() {
        int coord[] = new int[2];
        end = AI.playAITurn(coord);
        board.get(coord[1]).get(coord[0]).setLabel(turn);
        switchTurn();
    }

    private void switchTurn() {
        if (turn.equals("O"))
            turn = "X";
        else
            turn = "O";
    }
}
