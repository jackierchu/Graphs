package graph;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author
 */
public class GraphTest {

    // Add tests.  Here's a sample.

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());

    }

    @Test
    public void DirectedGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        g.add();
        assertEquals("Current graph has vertices", 1, g.vertexSize());
        assertEquals("Current graph has edges",0, g.edgeSize());
        g.remove(1);
        assertEquals("Current graph has vertices", 0, g.vertexSize());
        assertEquals("Current graph has edges",0, g.edgeSize());
        g.add(2,3);
        assertEquals("Current graph has vertices", 2, g.vertexSize());
        assertEquals("Current graph has edges",1, g.edgeSize());
        assertTrue(g.isDirected());
    }

    @Test
    public void undirectedTest() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        assertEquals(1, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.remove(1);
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add(1,2);
        assertEquals(2, g.vertexSize());
        assertEquals(1, g.edgeSize());
        g.add();
        assertEquals(3, g.vertexSize());
        assertEquals(3, g.maxVertex());

        g.add(3,1);
        g.add(3,4);
        assertEquals(4, g.vertexSize());
        assertEquals(4, g.maxVertex());
        assertEquals(3, g.edgeSize());
        g.add(6, 6);
        assertEquals(5, g.vertexSize());
        assertEquals(5, g.add());
        g.add(6,2);
        assertEquals(5,g.edgeSize());
        g.remove(7);
        assertEquals(6, g.vertexSize());
        g.remove(6);
        assertEquals(5, g.vertexSize());
        assertEquals(3, g.edgeSize());
        g.add(5,6);
        assertEquals(6, g.vertexSize());
        assertEquals(4, g.edgeSize());

        Iteration<Integer> iteration = g.successors(5);
        int count = 0;
        while (iteration.hasNext()) {
            iteration.next();
            count ++;
        }
        assertEquals(1, count);

        Iteration<Integer> iteration1 = g.successors(6);
        count = 0;
        while (iteration1.hasNext()) {
            iteration1.next();
            count ++;
        }
        assertEquals(1, count);
        g.remove(1);
        assertEquals(5, g.vertexSize());
        assertEquals(2, g.edgeSize());
    }

}
