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
        buildBoard(b, reachableFromTestBoard);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(legalMoveFromTestMoves.contains(m.toString()));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(legalMoveFromTestMoves.size(), numMoves);
        assertEquals(legalMoveFromTestMoves.size(), moves.size());
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
    static final Set<String> legalMoveFromTestMoves =
            new HashSet<String>(Arrays.asList(
                    "f6-g6(h6)", "f6-g6(h5)", "f6-g6(i4)", "f6-g6(g5)", "f6-g6(f5)", "f6-g6(f6)",
                    "f6-g6(e6)", "f6-h6(h5)", "f6-h6(h4)", "f6-h6(g5)", "f6-h6(g6)", "f6-h6(f6)",
                    "f6-h6(e6)", "f6-g5(g6)", "f6-g5(h6)", "f6-g5(h5)", "f6-g5(h4)", "f6-g5(i3)",
                    "f6-g5(f5)", "f6-g5(e5)", "f6-g5(f6)", "f6-h4(h5)", "f6-h4(h6)", "f6-h4(i4)",
                    "f6-h4(i3)", "f6-h4(g3)", "f6-h4(f2)", "f6-h4(e1)", "f6-h4(g5)", "f6-h4(f6)",
                    "f6-i3(i4)", "f6-i3(h2)", "f6-i3(g1)", "f6-i3(h4)", "f6-i3(g5)", "f6-i3(f6)",
                    "f6-f5(f6)", "f6-f5(g6)", "f6-f5(g5)", "f6-f5(h5)", "f6-f5(e5)", "f6-f5(e6)",
                    "f6-e5(e6)", "f6-e5(f6)", "f6-e5(f5)", "f6-e5(g5)", "f6-e5(h5)", "f6-e6(f6)",
                    "f6-e6(g6)", "f6-e6(h6)", "f6-e6(f5)", "f6-e6(e5)", "h7-h8(h9)", "h7-h8(h10)",
                    "h7-h8(i9)", "h7-h8(j10)", "h7-h8(i8)", "h7-h8(j8)", "h7-h8(h7)", "h7-h8(h6)",
                    "h7-h8(h5)", "h7-h8(h4)", "h7-h8(g8)", "h7-h8(f8)", "h7-h8(e8)", "h7-h8(d8)",
                    "h7-h8(c8)", "h7-h8(b8)", "h7-h8(a8)", "h7-h8(g9)", "h7-h8(f10)", "h7-h9(h10)",
                    "h7-h9(i10)", "h7-h9(i9)", "h7-h9(j9)", "h7-h9(i8)", "h7-h9(j7)", "h7-h9(h8)",
                    "h7-h9(h7)", "h7-h9(h6)", "h7-h9(h5)", "h7-h9(h4)", "h7-h9(g8)", "h7-h9(g9)",
                    "h7-h9(f9)", "h7-h9(e9)", "h7-h9(d9)", "h7-h9(c9)", "h7-h9(b9)", "h7-h9(a9)",
                    "h7-h9(g10)", "h7-h10(i10)", "h7-h10(j10)", "h7-h10(i9)", "h7-h10(j8)", "h7-h10(h9)",
                    "h7-h10(h8)", "h7-h10(h7)", "h7-h10(h6)", "h7-h10(h5)", "h7-h10(h4)", "h7-h10(g9)",
                    "h7-h10(f8)", "h7-h10(g10)", "h7-h10(f10)", "h7-h10(e10)", "h7-h10(d10)", "h7-h10(c10)",
                    "h7-h10(b10)", "h7-h10(a10)", "h7-i8(i9)", "h7-i8(i10)", "h7-i8(j9)", "h7-i8(j8)",
                    "h7-i8(j7)", "h7-i8(h7)", "h7-i8(g6)", "h7-i8(f5)", "h7-i8(h8)", "h7-i8(g8)",
                    "h7-i8(f8)", "h7-i8(e8)", "h7-i8(d8)", "h7-i8(c8)", "h7-i8(b8)", "h7-i8(a8)",
                    "h7-i8(h9)", "h7-i8(g10)", "h7-j9(j10)", "h7-j9(j8)", "h7-j9(j7)", "h7-j9(j6)",
                    "h7-j9(j5)", "h7-j9(i8)", "h7-j9(h7)", "h7-j9(g6)", "h7-j9(f5)", "h7-j9(i9)",
                    "h7-j9(h9)", "h7-j9(g9)", "h7-j9(f9)", "h7-j9(e9)", "h7-j9(d9)", "h7-j9(c9)",
                    "h7-j9(b9)", "h7-j9(a9)", "h7-j9(i10)", "h7-h6(h7)", "h7-h6(h8)", "h7-h6(h9)",
                    "h7-h6(h10)", "h7-h6(h5)", "h7-h6(h4)", "h7-h6(g5)", "h7-h6(g6)", "h7-h5(h6)",
                    "h7-h5(h7)", "h7-h5(h8)", "h7-h5(h9)", "h7-h5(h10)", "h7-h5(i4)", "h7-h5(h4)",
                    "h7-h5(g5)", "h7-h5(f5)", "h7-h5(e5)", "h7-h5(g6)", "h7-h4(h5)", "h7-h4(h6)",
                    "h7-h4(h7)", "h7-h4(h8)", "h7-h4(h9)", "h7-h4(h10)", "h7-h4(i4)", "h7-h4(i3)",
                    "h7-h4(g3)", "h7-h4(f2)", "h7-h4(e1)", "h7-h4(g5)", "h7-g6(h7)", "h7-g6(i8)",
                    "h7-g6(j9)", "h7-g6(h6)", "h7-g6(h5)", "h7-g6(i4)", "h7-g6(g5)", "h7-g6(f5)",
                    "h7-f5(g6)", "h7-f5(h7)", "h7-f5(i8)", "h7-f5(j9)", "h7-f5(g5)", "h7-f5(h5)",
                    "h7-f5(e5)", "h7-f5(e6)", "h7-g8(g9)", "h7-g8(g10)", "h7-g8(h9)", "h7-g8(i10)",
                    "h7-g8(h8)", "h7-g8(i8)", "h7-g8(j8)", "h7-g8(h7)", "h7-g8(f8)", "h7-g8(e8)",
                    "h7-g8(d8)", "h7-g8(c8)", "h7-g8(b8)", "h7-g8(a8)", "h7-g8(f9)", "h7-g8(e10)",
                    "h7-f9(f10)", "h7-f9(g10)", "h7-f9(g9)", "h7-f9(h9)", "h7-f9(i9)", "h7-f9(j9)",
                    "h7-f9(g8)", "h7-f9(h7)", "h7-f9(f8)", "h7-f9(e8)", "h7-f9(e9)", "h7-f9(d9)",
                    "h7-f9(c9)", "h7-f9(b9)", "h7-f9(a9)", "h7-f9(e10)", "h7-e10(f10)", "h7-e10(g10)",
                    "h7-e10(h10)", "h7-e10(i10)", "h7-e10(j10)", "h7-e10(f9)", "h7-e10(g8)", "h7-e10(h7)",
                    "h7-e10(e9)", "h7-e10(e8)", "h7-e10(d9)", "h7-e10(c8)", "h7-e10(b7)", "h7-e10(a6)",
                    "h7-e10(d10)", "h7-e10(c10)", "h7-e10(b10)", "h7-e10(a10)", "i2-i3(i4)", "i2-i3(i2)",
                    "i2-i3(i1)", "i2-i3(h2)", "i2-i3(g1)", "i2-i3(h4)", "i2-i3(g5)", "i2-i4(j5)",
                    "i2-i4(i3)", "i2-i4(i2)", "i2-i4(i1)", "i2-i4(h4)", "i2-i4(h5)", "i2-i4(g6)",
                    "i2-j1(i1)", "i2-j1(h1)", "i2-j1(g1)", "i2-j1(f1)", "i2-j1(e1)", "i2-j1(d1)",
                    "i2-j1(c1)",
                    "i2-j1(b1)",
                    "i2-j1(a1)",
                    "i2-j1(i2)",
                    "i2-i1(i2)",
                    "i2-i1(i3)",
                    "i2-i1(i4)",
                    "i2-i1(j1)",
                    "i2-i1(h1)",
                    "i2-i1(g1)",
                    "i2-i1(f1)",
                    "i2-i1(e1)",
                    "i2-i1(d1)",
                    "i2-i1(c1)",
                    "i2-i1(b1)",
                    "i2-i1(a1)",
                    "i2-i1(h2)",
                    "i2-i1(g3)",
                    "i2-h1(h2)",
                    "i2-h1(i2)",
                    "i2-h1(i1)",
                    "i2-h1(j1)",
                    "i2-h1(g1)",
                    "i2-h1(f1)",
                    "i2-h1(e1)",
                    "i2-h1(d1)",
                    "i2-h1(c1)",
                    "i2-h1(b1)",
                    "i2-h1(a1)",
                    "i2-h1(g2)",
                    "i2-h1(f3)",
                    "i2-h2(i3)",
                    "i2-h2(i2)",
                    "i2-h2(i1)",
                    "i2-h2(h1)",
                    "i2-h2(g1)",
                    "i2-h2(g2)",
                    "i2-h2(f2)",
                    "i2-h2(e2)",
                    "i2-h2(d2)",
                    "i2-h2(c2)",
                    "i2-h2(b2)",
                    "i2-h2(a2)",
                    "i2-h2(g3)",
                    "i2-g2(g3)",
                    "i2-g2(h2)",
                    "i2-g2(i2)",
                    "i2-g2(h1)",
                    "i2-g2(g1)",
                    "i2-g2(f1)",
                    "i2-g2(f2)",
                    "i2-g2(e2)",
                    "i2-g2(d2)",
                    "i2-g2(c2)",
                    "i2-g2(b2)",
                    "i2-g2(a2)",
                    "i2-g2(f3)",
                    "i2-f2(f3)",
                    "i2-f2(g3)",
                    "i2-f2(h4)",
                    "i2-f2(g2)",
                    "i2-f2(h2)",
                    "i2-f2(i2)",
                    "i2-f2(g1)",
                    "i2-f2(f1)",
                    "i2-f2(e1)",
                    "i2-f2(e2)",
                    "i2-f2(d2)",
                    "i2-f2(c2)",
                    "i2-f2(b2)",
                    "i2-f2(a2)",
                    "i2-f2(e3)",
                    "i2-e2(e3)",
                    "i2-e2(f3)",
                    "i2-e2(f2)",
                    "i2-e2(g2)",
                    "i2-e2(h2)",
                    "i2-e2(i2)",
                    "i2-e2(f1)",
                    "i2-e2(e1)",
                    "i2-e2(d1)",
                    "i2-e2(d2)",
                    "i2-e2(c2)",
                    "i2-e2(b2)",
                    "i2-e2(a2)",
                    "i2-e2(d3)",
                    "i2-e2(c4)",
                    "i2-e2(b5)",
                    "i2-e2(a6)",
                    "i2-d2(d3)",
                    "i2-d2(e3)",
                    "i2-d2(e2)",
                    "i2-d2(f2)",
                    "i2-d2(g2)",
                    "i2-d2(h2)",
                    "i2-d2(i2)",
                    "i2-d2(e1)",
                    "i2-d2(d1)",
                    "i2-d2(c1)",
                    "i2-d2(c2)",
                    "i2-d2(b2)",
                    "i2-d2(a2)",
                    "i2-d2(c3)",
                    "i2-d2(b4)",
                    "i2-d2(a5)",
                    "i2-c2(c3)",
                    "i2-c2(c4)",
                    "i2-c2(c5)",
                    "i2-c2(c6)",
                    "i2-c2(c7)",
                    "i2-c2(c8)",
                    "i2-c2(c9)",
                    "i2-c2(c10)",
                    "i2-c2(d3)",
                    "i2-c2(d2)",
                    "i2-c2(e2)",
                    "i2-c2(f2)",
                    "i2-c2(g2)",
                    "i2-c2(h2)",
                    "i2-c2(i2)",
                    "i2-c2(d1)",
                    "i2-c2(c1)",
                    "i2-c2(b1)",
                    "i2-c2(b2)",
                    "i2-c2(a2)",
                    "i2-c2(b3)",
                    "i2-c2(a4)",
                    "i2-b2(b3)",
                    "i2-b2(b4)",
                    "i2-b2(b5)",
                    "i2-b2(b6)",
                    "i2-b2(b7)",
                    "i2-b2(b8)",
                    "i2-b2(b9)",
                    "i2-b2(b10)",
                    "i2-b2(c3)",
                    "i2-b2(c2)",
                    "i2-b2(d2)",
                    "i2-b2(e2)",
                    "i2-b2(f2)",
                    "i2-b2(g2)",
                    "i2-b2(h2)",
                    "i2-b2(i2)",
                    "i2-b2(c1)",
                    "i2-b2(b1)",
                    "i2-b2(a1)",
                    "i2-b2(a2)",
                    "i2-b2(a3)",
                    "i2-a2(a3)",
                    "i2-a2(a4)",
                    "i2-a2(a5)",
                    "i2-a2(a6)",
                    "i2-a2(a7)",
                    "i2-a2(a8)",
                    "i2-a2(a9)",
                    "i2-a2(a10)",
                    "i2-a2(b3)",
                    "i2-a2(c4)",
                    "i2-a2(b2)",
                    "i2-a2(c2)",
                    "i2-a2(d2)",
                    "i2-a2(e2)",
                    "i2-a2(f2)",
                    "i2-a2(g2)",
                    "i2-a2(h2)",
                    "i2-a2(i2)",
                    "i2-a2(b1)",
                    "i2-a2(a1)",
                    "j2-j1(j2)",
                    "j2-j1(i1)",
                    "j2-j1(h1)",
                    "j2-j1(g1)",
                    "j2-j1(f1)",
                    "j2-j1(e1)",
                    "j2-j1(d1)",
                    "j2-j1(c1)",
                    "j2-j1(b1)",
                    "j2-j1(a1)",
                    "j2-i1(j2)",
                    "j2-i1(j1)",
                    "j2-i1(h1)",
                    "j2-i1(g1)",
                    "j2-i1(f1)",
                    "j2-i1(e1)",
                    "j2-i1(d1)",
                    "j2-i1(c1)",
                    "j2-i1(b1)",
                    "j2-i1(a1)",
                    "j2-i1(h2)",
                    "j2-i1(g3)",
                    "j2-i3(i4)",
                    "j2-i3(j2)",
                    "j2-i3(h2)",
                    "j2-i3(g1)",
                    "j2-i3(h4)",
                    "j2-i3(g5)",
                    "j2-h4(h5)",
                    "j2-h4(h6)",
                    "j2-h4(i4)",
                    "j2-h4(i3)",
                    "j2-h4(j2)",
                    "j2-h4(g3)",
                    "j2-h4(f2)",
                    "j2-h4(e1)",
                    "j2-h4(g5)",
                    "j2-g5(g6)",
                    "j2-g5(h6)",
                    "j2-g5(h5)",
                    "j2-g5(h4)",
                    "j2-g5(i3)",
                    "j2-g5(j2)",
                    "j2-g5(f5)",
                    "j2-g5(e5)"
            ));

    @Test
    public void LegalMoveTest() {
        Board b = new Board();
        Iterator<Move> originalMove = b.legalMoves(WHITE);
        for (int i = 0; i < 10; i ++) {
            b.put(SPEAR, 2, i);
            b.put(SPEAR, 7, i);
        }
        for (int j = 0; j < 10; j ++) {
            b.put(SPEAR, j, 4);
            b.put(SPEAR, j, 1);
        }

        Iterator<Move> thisOne = b.legalMoves(WHITE);

        ArrayList<Move> sizeTest = new ArrayList<>();
        while (thisOne.hasNext()) {
            Move currMove = thisOne.next();
            if (currMove != null) {
                sizeTest.add(currMove);
            }
        }
        assertEquals((3 * 3) * 2 + 4 * 2, sizeTest.size());
        sizeTest.clear();

        for(int i = 0; i < 10; i ++) {
            b.put(SPEAR, i, 8);
        }
        Iterator<Move> anotherOne = b.legalMoves(BLACK);
        while (anotherOne.hasNext()) {
            Move currMove = anotherOne.next();
            if (currMove != null) {
                sizeTest.add(currMove);
            }
        }
        assertEquals((4 * 4 + 5) * 2 + 4 * 2, sizeTest.size());
    }

    @Test
    public void LegalMoveEdgeCaseTest() {
        Board b = new Board();
        b.put(SPEAR, 0, 2);
        b.put(SPEAR, 0, 4);
        b.put(SPEAR, 1, 3);
        b.put(SPEAR, 1, 4);
        b.put(SPEAR, 1, 2);
        b.put(SPEAR, 2,0);
        b.put(SPEAR, 4, 0);
        b.put(SPEAR, 3, 1);
        b.put(SPEAR, 5,0);
        b.put(SPEAR, 2, 1);
        b.put(SPEAR, 4, 1);
        b.put(SPEAR, 5, 1);
        b.put(SPEAR, 7, 1);
        b.put(SPEAR, 7,0);

        b.put(SPEAR, 6, 1);
        b.put(SPEAR, 9, 2);
        b.put(SPEAR, 9, 4);
        b.put(SPEAR, 8, 3);
        b.put(SPEAR, 8, 2);
        b.put(SPEAR, 8, 4);

        System.out.println(b.toString());
        int i = 0;
        Iterator<Move> thisOne = b.legalMoves(WHITE);
        while (thisOne.hasNext()){
            Move a = thisOne.next();
            if (a != null) {
                i += 1;
            }
        }
        System.out.println(i);

    }




}


