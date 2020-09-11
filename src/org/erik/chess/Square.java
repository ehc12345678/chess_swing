package org.erik.chess;

public class Square {
    public static final String WHITE = "ws";
    public static final String BLACK = "bs";
    private String color;
    private int row;
    private char rank;
    private Piece piece;

    public Square(String color, int row, char rank) {
        this.color = color;
        this.row = row;
        this.rank = rank;
    }

    public String getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (piece != null) {
            piece.setRank(getRank());
            piece.setRow(getRow());
        }
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public char getRank() {
        return rank;
    }

    public String toString() {
        return color;
    }

    String verboseStr() {
        return color + "(" + row + rank + ")";
    }
}
