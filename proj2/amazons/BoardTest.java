package amazons;

import org.junit.Test;
import static amazons.Piece.*;
import static amazons.Piece.WHITE;
import static org.junit.Assert.*;
import ucb.junit.textui;
import java.util.Iterator;
import java.util.ArrayList;

public class BoardTest {
    static final String INIT_BOARD_STATE =
            "   - - - B - - B - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   B - - - - - - - - B\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   W - - - - - - - - W\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - W - - W - - -\n";

    static final String SMILE =
            "   - - - - - - - - - -\n" +
                    "   - S S S - - S S S -\n" +
                    "   - S - S - - S - S -\n" +
                    "   - S S S - - S S S -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - W - - - - W - -\n" +
                    "   - - - W W W W - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n";
    private void makeSmile(Board b) {
        b.put(EMPTY, Square.sq(0, 3));
        b.put(EMPTY, Square.sq(0, 6));
        b.put(EMPTY, Square.sq(9, 3));
        b.put(EMPTY, Square.sq(9, 6));
        b.put(EMPTY, Square.sq(3, 0));
        b.put(EMPTY, Square.sq(3, 9));
        b.put(EMPTY, Square.sq(6, 0));
        b.put(EMPTY, Square.sq(6, 9));
        for (int col = 1; col < 4; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(2, 7));
        for (int col = 6; col < 9; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(7, 7));
        for (int lip = 3; lip < 7; lip += 1) {
            b.put(WHITE, Square.sq(lip, 2));
        }
        b.put(WHITE, Square.sq(2, 3));
        b.put(WHITE, Square.sq(7, 3));
    }

    @Test
    public void emptyToStringTest() {
        Board newBoard = new Board();
        assertEquals(INIT_BOARD_STATE, newBoard.toString());
    }

    @Test
    public void testToStringSmile() {
        Board newSmile = new Board();
        makeSmile(newSmile);
        assertEquals(SMILE, newSmile.toString());
    }

    @Test
    public void isUnblockedMoveTest() {
        Board newBoard = new Board();
        Square beginning = Square.sq(0, 3);
        Square end = Square.sq(5, 3);
        Square newWhite = Square.sq(5, 0);
        assertTrue(newBoard.isUnblockedMove(beginning, end, null));
        assertEquals(WHITE, newBoard.get(3, 0));
        assertFalse(newBoard.isUnblockedMove(beginning, newWhite, null));
    }

    @Test
    public void isUnblockedMoveTestSecond() {
        Board newBoard = new Board();
        Square beginning = Square.sq(0, 5);
        Square end = Square.sq(5, 5);
        Square newWhite = Square.sq(5, 0);
        assertTrue(newBoard.isUnblockedMove(beginning, end, null));
        assertEquals(EMPTY, newBoard.get(5, 0));
        assertTrue(newBoard.isUnblockedMove(beginning, newWhite, null));
    }

    @Test
    public void reachableFromMoveTest() {
        Board newBoard = new Board();
        for (int i = 1; i < 9; i++) {
            newBoard.put(SPEAR, 3, i);
        }
        Iterator<Square> newIterator = newBoard.reachableFrom(Square.sq(0, 3), null);
        ArrayList<Square> newSquare = new ArrayList<>();
        while(newIterator.hasNext()) {
            Square newest = newIterator.next();
            if (newest != null) {
                System.out.println(newest);
            }
        }
    }

}


