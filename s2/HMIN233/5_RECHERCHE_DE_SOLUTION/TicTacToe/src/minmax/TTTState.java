package src.minmax;

import java.util.ArrayList;
import java.util.Arrays;

public class TTTState {
    private int boardSize = 3;
    private byte[][] board;
    private final int players = 2;
    private int lastPlayer = 0;

    public TTTState() {
        board = new byte[boardSize][boardSize];
        emptyBoard();
    }

    public TTTState(int _size) {
        boardSize = _size;
        board = new byte[boardSize][boardSize];
        emptyBoard();
    }

    public TTTState(TTTState in) {
        boardSize = in.boardSize;
        lastPlayer = in.lastPlayer;
        board = new byte[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(in.board[i], 0, board[i], 0, boardSize);
        }
    }

    public ArrayList<TTTState> nextStates() {
        ArrayList<TTTState> ret = new ArrayList<>();
        TTTState newState;

        if (!boardFull()) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == -1) {
                        newState = new TTTState(this);
                        newState.lastPlayer = getNextPlayer();
                        newState.board[i][j] = (byte) getNextPlayer();
                        ret.add(newState);
                    }
                }
            }
        }

        return ret;
    }

    private void emptyBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = -1;
            }
        }
    }

    private int getNextPlayer() {
        return (lastPlayer + 1) % players;
    }

    public boolean boardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public int winner() {
        int count;
        int token;

        // --- lignes
        for (int i = 0; i < boardSize; i++) {
            token = board[i][0];
            count = 0;

            for (int j = 0; j < boardSize; j++) {
                if (token == board[i][j] && board[i][j] != -1) count++;
            }

            if (count == boardSize) return token;
        }

        // --- colonnes
        for (int i = 0; i < boardSize; i++) {
            token = board[0][i];
            count = 0;

            for (int j = 0; j < boardSize; j++) {
                if (token == board[j][i] && board[j][i] != -1) count++;
            }

            if (count == boardSize) return token;
        }

        // --- diagonal: \
        token = board[0][0];
        count = 1;
        for (int i = 0; i < boardSize; i++) {
            if (token == board[i][i] && board[i][i] != -1) count++;
        }
        if (count == boardSize) return token;

        // --- diagonal: /
        token = board[0][boardSize - 1];
        count = 1;
        for (int i = 0; i < boardSize; i++) {
            if (token == board[i][boardSize - 1 - i] && board[i][boardSize - 1 - i] != -1) count++;
        }
        if (count == boardSize) return token;

        return -1;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == -1) ret.append("_");
                else if (board[i][j] == 0) ret.append("X");
                else ret.append("O");
            }
            ret.append("\n");
        }
        return ret.toString();
    }
}
