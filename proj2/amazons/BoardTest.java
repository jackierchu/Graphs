package amazons;

import org.junit.Test;

import static amazons.Move.mv;
import static amazons.Piece.*;
import static amazons.Piece.WHITE;
import static amazons.Square.sq;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ucb.junit.textui;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

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


    public void reachableFromMoveTest() {
        Board newBoard = new Board();
        for (int i = 1; i < 9; i++) {
            newBoard.put(SPEAR, 3, i);
        }
        Iterator<Square> newIterator = newBoard.reachableFrom(Square.sq(0, 3), null);
        ArrayList<Square> newSquare = new ArrayList<>();
        while(newIterator.hasNext()) {
            Square newest = newIterator.next();
            System.out.println(newest);
        }
    }

    @Test
    public void legalMoveTest() {
        Board newBoard = new Board();

        for (int i = 0; i < 10; i++) {
            newBoard.put(SPEAR, 2, i);
            newBoard.put(SPEAR, 7, i);
        }

        for (int j = 0; j < 10; j++) {
            newBoard.put(SPEAR, j, 4);
        }

        System.out.println(newBoard.toString());
        Iterator<Move> newMove = newBoard.legalMoves(WHITE);
        while (newMove.hasNext()) {
            Move current = newMove.next();
            System.out.println(current.toString());
        }
    }

    @Test
    public void testUndo() {
        Board b0 = new Board();
        Board b1 = new Board(b0);
        Move firstMove = mv(sq("a4"), sq("b3"), sq("d5"));
        b0.makeMove(firstMove);
        Board b2 = new Board(b0);
        b2.undo();

        assertEquals(b2.toString(), b1.toString());
        assertEquals((long)b1.numMoves(), (long)b2.numMoves());
        b2.makeMove(firstMove);
        assertEquals(b2.toString(), b0.toString());
    }

    /** Tests reachableFromIterator to make sure it returns all reachable
     *  Squares. This method may need to be changed based on
     *   your implementation. */
    @Test
    public void testReachableFrom() {
        Board b = new Board();
        buildBoard(b, reachableFromTestBoard);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 5), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(reachableFromTestSquares.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(reachableFromTestSquares.size(), numSquares);
        assertEquals(reachableFromTestSquares.size(), squares.size());
    }

    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        buildBoard(b, null); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(false); // FIXME
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(0, numMoves); // FIXME
        assertEquals(0, moves.size()); // FIXME
    }


    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = 0; row < Board.SIZE; row++) {
                Piece piece = target[row][col];
                b.put(piece, Square.sq(col, row));
            }
        }
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] reachableFromTestBoard =
            {
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, W, W },
                    { E, E, E, E, E, E, E, S, E, S },
                    { E, E, E, S, S, S, S, E, E, S },
                    { E, E, E, S, E, E, E, E, B, E },
                    { E, E, E, S, E, W, E, E, B, E },
                    { E, E, E, S, S, S, B, W, B, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
            };

    static final Set<Square> reachableFromTestSquares =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 4),
                    Square.sq(4, 4),
                    Square.sq(4, 5),
                    Square.sq(6, 5),
                    Square.sq(7, 5),
                    Square.sq(6, 4),
                    Square.sq(7, 3),
                    Square.sq(8, 2)));




}


