package amazons;

import org.junit.Test;
import static org.junit.Assert.*;

public class SquareTest {
    @Test
    public void testSquare() {
        Square thirtyTwo = Square.sq(32);
        Square thirtyFive = Square.sq("d6");

        assertEquals(thirtyTwo, thirtyFive.queenMove(4, 3));
        assertEquals(thirtyFive, thirtyTwo.queenMove(0, 3));

        Square twentyfour = Square.sq(2,4);
        assertEquals(twentyfour, thirtyFive.queenMove(5, 1));
        assertEquals(thirtyFive, twentyfour.queenMove(1, 1));

        Square sixtySeven = Square.sq("g", "9");
        assertEquals(sixtySeven, thirtyFive.queenMove(1, 3));
        assertEquals(thirtyFive, sixtySeven.queenMove(5, 3));

        Square nintyFive = Square.sq(95);
        assertEquals(nintyFive, thirtyFive.queenMove(2,6));
        assertEquals(thirtyFive, nintyFive.queenMove(6, 6));

        Square seventyOne = Square.sq(7, 1);
        assertEquals(seventyOne, thirtyFive.queenMove(3, 4));
        assertEquals(thirtyFive, seventyOne.queenMove(7, 4));

        assertNull(thirtyFive.queenMove(2, 9));
        assertNull(thirtyFive.queenMove(4, 10));
        assertNull(thirtyFive.queenMove(-2, 3));
        assertNull(thirtyFive.queenMove(11, 13));

    }

    public void testSquareMove() {
        Square thirtyTwo = Square.sq(32);
        Square thirtyFive = Square.sq("d6");

        assertEquals(thirtyTwo, thirtyFive.queenMove(4, 3));
        assertEquals(thirtyFive, thirtyTwo.queenMove(0, 3));

        Square twentyfour = Square.sq(2,4);
        assertEquals(twentyfour, thirtyFive.queenMove(5, 1));
        assertEquals(thirtyFive, twentyfour.queenMove(1, 1));

        Square sixtySeven = Square.sq("g", "9");
        assertEquals(sixtySeven, thirtyFive.queenMove(1, 3));
        assertEquals(thirtyFive, sixtySeven.queenMove(5, 3));

        Square nintyFive = Square.sq(95);
        assertEquals(nintyFive, thirtyFive.queenMove(2,6));
        assertEquals(thirtyFive, nintyFive.queenMove(6, 6));

        Square seventyOne = Square.sq(7, 1);
        assertEquals(seventyOne, thirtyFive.queenMove(3, 4));
        assertEquals(thirtyFive, seventyOne.queenMove(7, 4));

        assertNull(thirtyFive.queenMove(2, 9));
        assertNull(thirtyFive.queenMove(4, 10));
        assertNull(thirtyFive.queenMove(-2, 3));
        assertNull(thirtyFive.queenMove(11, 13));

    }

    @Test
    public void TestWithNumber() {
        Square zero = Square.sq(0);
        assertEquals("a1", zero.toString());
        assertEquals(0, zero.col());
        assertEquals(0, zero.row());

        Square one = Square.sq(1);
        assertEquals("a2", one.toString());
        assertEquals(0, one.col());
        assertEquals(1, one.row());

        Square fiftyOne = Square.sq(51);
        assertEquals("f2", fiftyOne.toString());
        assertEquals(5, fiftyOne.col());
        assertEquals(1, fiftyOne.row());

        Square eightyeight = Square.sq(88);
        assertEquals("i9", eightyeight.toString());
        assertEquals(8, eightyeight.col());
        assertEquals(8, eightyeight.row());

        Square nintyNine = Square.sq(99);
        assertEquals("j10", nintyNine.toString());
        assertEquals(9, nintyNine.col());
        assertEquals(9, nintyNine.row());
    }

    @Test
    public void TestWithString() {
        Square zero = Square.sq("a1");
        assertEquals("a1", zero.toString());
        assertEquals(0, zero.col());
        assertEquals(0, zero.row());

        Square nine = Square.sq("a9");
        assertEquals("a9", nine.toString());
        assertEquals(0, nine.col());
        assertEquals(8, nine.row());

        Square fiftyfive = Square.sq("f6");
        assertEquals("f6", fiftyfive.toString());
        assertEquals(5, fiftyfive.col());
        assertEquals(5, fiftyfive.row());

        Square seventyOne = Square.sq("h2");
        assertEquals("h2", seventyOne.toString());
        assertEquals(7, seventyOne.col());
        assertEquals(1, seventyOne.row());

        Square nintyEight = Square.sq("j9");
        assertEquals("j9", nintyEight.toString());
        assertEquals(9, nintyEight.col());
        assertEquals(8, nintyEight.row());
    }

    @Test
    public void toStringTestWColRow() {
        Square one = Square.sq(1, 1);
        assertEquals("b2", one.toString());
        assertEquals(1, one.col());
        assertEquals(1, one.row());

        Square sixtyFive = Square.sq(5, 4);
        assertEquals("f5", sixtyFive.toString());
        assertEquals(5, sixtyFive.col());
        assertEquals(4, sixtyFive.row());

        Square nintyTwo = Square.sq(9, 1);
        assertEquals("j2", nintyTwo.toString());
        assertEquals(9, nintyTwo.col());
        assertEquals(1, nintyTwo.row());
    }

    @Test
    public void directionOfTest() {
        Square twentyFour = Square.sq(24);

        Square fiftyOne = Square.sq("f","2");
        assertEquals(7, fiftyOne.direction(twentyFour));
        assertEquals(3, twentyFour.direction(fiftyOne));

        Square fortySix = Square.sq("e7");
        assertEquals(5, fortySix.direction(twentyFour));
        assertEquals(1, twentyFour.direction(fortySix));

        Square twentyFive = Square.sq(25);
        assertEquals(4, twentyFive.direction(twentyFour));
        assertEquals(0, twentyFour.direction(twentyFive));

        Square fiftyFour = Square.sq(5,4);
        assertEquals(6, fiftyFour.direction(twentyFour));
        assertEquals(2, twentyFour.direction(fiftyFour));
    }

    @Test
    public void correctQueenMoveTest() {
        Square twentyFour = Square.sq(24);

        Square twentyNine = Square.sq(29);
        assertTrue(twentyFour.isQueenMove(twentyNine));

        Square sixty =Square.sq(60);
        assertTrue(twentyFour.isQueenMove(sixty));

        Square seventyNine = Square.sq(7, 9);
        assertTrue(twentyFour.isQueenMove(seventyNine));

        Square six = Square.sq("a", "7");
        assertTrue(twentyFour.isQueenMove(six));
    }

    @Test
    public void wrongQueenMoveTest() {
        Square twentyFour = Square.sq(24);

        Square eightyFive = Square.sq(8, 5);
        assertFalse(twentyFour.isQueenMove(eightyFive));

        Square thirtyOne = Square.sq("d2");
        assertFalse(twentyFour.isQueenMove(thirtyOne));

        Square copyTwentyFour = Square.sq("c5");
        assertFalse(twentyFour.isQueenMove(copyTwentyFour));
    }
}


