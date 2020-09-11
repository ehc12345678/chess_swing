package org.erik.chess;

import javax.swing.*;
import java.awt.*;

public class Chess extends JFrame {
    public Chess() {
        setSize(450, 450);

        JPanel panel = new JPanel(new BorderLayout());
        Board board = new Board();
        panel.add(board, BorderLayout.CENTER);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.show();
    }
}
