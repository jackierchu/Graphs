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
}


