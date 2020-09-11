package org.erik.chess;

/**
 * TODO: split this out into subclasses
 *     Pawn
 *     Rook
 *     Bishop
 *     Knight
 *     Queen
 *     King
 */
public class Piece {
    public static final String WHITE = "w";
    public static final String BLACK = "b";

    public static final String PAWN = "p";
    public static final String ROOK = "r";
    public static final String KNIGHT = "n";
    public static final String BISHOP = "b";
    public static final String QUEEN = "q";
    public static final String KING = "k";

    private String type;
    private String color;
    private int row;
    private char rank;

    public Piece(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String toString() {
        return color + type;
    }

    public int getRow() {
        return row;
    }

    public char getRank() {
        return rank;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public void captured() {
        rank = 0;
        row = 0;
    }

    String verboseStr() {
        return color + type + "(" + row + rank + ")";
    }
}

