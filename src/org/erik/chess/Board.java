package org.erik.chess;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: Feb 21, 2005
 * Time: 12:03:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board extends JPanel {
    private static final int BOARD_SIZE = 8;
    public static final int FIRST_ROW = 1;
    public static final int LAST_ROW = FIRST_ROW + BOARD_SIZE - 1;
    public static final char FIRST_RANK = 'a';
    public static final int LAST_RANK = FIRST_RANK + BOARD_SIZE - 1;

    private Square[][] m_squares;
    private List deadPieces = new ArrayList();
    private HashMap imageMap = new HashMap();
    private Square selectedSquare = null;

    public Board() {
        m_squares = new Square[BOARD_SIZE][BOARD_SIZE];
        reset();
        this.addMouseListener(new MouseListener());
    }

    public void reset() {
        for (int row = FIRST_ROW; row <= LAST_ROW; ++row) {
            for (char rank = FIRST_RANK; rank <= LAST_RANK; ++rank) {
                int r = row - FIRST_ROW;
                int c = rank - FIRST_RANK;

                String color;
                if ((r % 2) == (c % 2)) {
                    color = Square.BLACK;
                } else {
                    color = Square.WHITE;
                }
                m_squares[r][c] = new Square(color, row, rank);
            }
        }

        // pawns
        for (char rank = FIRST_RANK; rank <= LAST_RANK; ++rank) {
            placePiece(2, rank, new Piece(Piece.PAWN, Piece.WHITE));
            placePiece(7, rank, new Piece(Piece.PAWN, Piece.BLACK));
        }

        // rooks
        // TODO: add rooks

        // knights
        // TODO: add knights

        // bishops
        // TODO: add bishops

        // queens + kings
        // TODO: add kings + queens

        setSize(450, 450);
    }

    public void placePiece(int row, char rank, Piece piece) {
        Square square = getSquare(row, rank);
        if (square.getPiece() != null) {
            capturePiece(square);
        }

        // queen me!
        // TODO: check for promotions
        square.setPiece(piece);
    }

    private void onMovePiece(Square fromSquare, Square moveSquare) {
        // TODO: check if legal move

        placePiece(moveSquare.getRow(), moveSquare.getRank(), fromSquare.getPiece());
        fromSquare.setPiece(null);
        selectedSquare = null;
        update(getGraphics());
    }

    private void capturePiece(Square onSquare) {
        Piece piece = onSquare.getPiece();
        if (piece != null) {
            piece.captured();
            onSquare.setPiece(null);
        }
    }

    public Square getSquare(int row, char rank) {
        return m_squares[row - FIRST_ROW][rank - FIRST_RANK];
    }

    public Square getSquare(String s) {
        if (s.length() < 2) {
            System.out.println("Woops: " + s);
        }
        char rank = s.charAt(0);
        int row = s.charAt(1) - '0';
        return getSquare(row, rank);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = getSize();
        int squareSize = size.width / (BOARD_SIZE + 2);
        int midSquare = squareSize / 2;

        int yPos = size.height - squareSize;
        for (int row = FIRST_ROW; row <= LAST_ROW; ++row) {
            int xPos = 0;

            yPos -= squareSize;
            g.drawChars(Integer.toString(row).toCharArray(), 0, 1, xPos + midSquare, yPos + midSquare);

            for (char rank = FIRST_RANK; rank <= LAST_RANK; ++rank) {
                xPos += squareSize;

                if (row == FIRST_ROW) {
                    g.drawChars(new char[]{rank}, 0, 1, xPos + midSquare, yPos + squareSize + 10);
                }

                Square square = getSquare(row, rank);
                Piece piece = square.getPiece();
                if (piece != null) {
                    String imageName = "resource/" + piece + "-" + square + ".gif";
                    g.drawImage(getImage(imageName), xPos, yPos, null);
                } else {
                    String imageName = "resource/" + square + ".gif";
                    g.drawImage(getImage(imageName), xPos, yPos, null);
                }
            }
        }
    }

    private Image getImage(String imageName) {
        ImageIcon icon = (ImageIcon) imageMap.get(imageName);
        if (icon == null) {
            icon = new ImageIcon(getClass().getResource(imageName));
            imageMap.put(imageName, icon);
        }
        return icon.getImage();
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            Dimension size = getSize();
            int squareSize = size.width / (BOARD_SIZE + 2);

            int rowOffset = (e.getY() - (squareSize / 2)) / squareSize;
            int colOffset =  (e.getX() / squareSize) - 1;
            if (rowOffset >= 0 && rowOffset < BOARD_SIZE && colOffset >= 0 && colOffset < BOARD_SIZE) {
                int row = BOARD_SIZE - rowOffset;
                char rank = (char) ('a' + colOffset);
                System.out.println("Clicking " + rowOffset + " " + colOffset + " " + row + rank);
                if (selectedSquare == null) {
                    Square square = getSquare(row, rank);
                    if (square.getPiece() != null) {
                        System.out.println("Selecting square with piece on it " + square.verboseStr());
                        selectedSquare = square;
                    }
                } else {
                    Square moveSquare = getSquare(row, rank);
                    if (moveSquare == selectedSquare) {
                        System.out.println("Clicked same square, cancel move");
                        selectedSquare = null; // cancel
                    } else {
                        System.out.println(
                            "Move piece " + selectedSquare.getPiece().verboseStr() + " to " + moveSquare.verboseStr());
                        onMovePiece(selectedSquare, moveSquare);
                    }
                }
            } else {
                System.out.println("Clicked outside board, cancel move");
                selectedSquare = null; // cancel the selected square
            }
        }
    }

}
