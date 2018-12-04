package graph;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Set;


/** Unit tests for the Graph class.
 *  @author Jacqueline Chu
 */
public class GraphTest {



    private UndirectedGraph initializeUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        return g;
    }

    private DirectedGraph initializeDirectedGraph() {
        DirectedGraph g = new DirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        g.add(2, 1);
        g.add(3, 1);
        g.add(4, 1);
        g.add(1, 5);
        return g;
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());

    }

    @Test
    public void undirectedGraphAdd() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        g.add();
        assertEquals("Current graph has vertices", 1, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());

        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        assertEquals("Current graph has vertices",  6, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
    }

    @Test
    public void undirectedGraphRemove() {
        UndirectedGraph g = new UndirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        g.remove(1);
        assertEquals("Current graph has vertices", 4, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
    }

    @Test
    public void undirectedGraphAddEdgeIncident() {
        UndirectedGraph g = initializeUndirectedGraph();
        assertEquals("Current graph has vertices", 5, g.vertexSize());
        assertEquals("Current graph has edges", 4, g.edgeSize());
    }

    @Test
    public void undirectedGraphAddSameEdgeIncidentRegardlessOrder() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(2, 1);
        assertEquals("Current graph has vertices", 2, g.vertexSize());
        assertEquals("Current graph has edges", 1, g.edgeSize());
    }

    @Test
    public void undirectedGraphRemoveConnectedVertex() {
        UndirectedGraph g = initializeUndirectedGraph();
        g.remove(1);
        assertEquals("Current graph has vertices", 4, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
    }

    @Test
    public void undirectedGraphRemoveEdgeIncidents() {
        UndirectedGraph g = initializeUndirectedGraph();

        g.remove(1, 2);
        assertEquals("Current graph has vertices",  5, g.vertexSize());
        assertEquals("Current graph has edges", 3, g.edgeSize());
        g.remove(1, 3);
        assertEquals("Current graph has vertices", 5, g.vertexSize());
        assertEquals("Current graph has edges", 2, g.edgeSize());
        g.remove(1, 4);
        assertEquals("Current graph has vertices", 5, g.vertexSize());
        assertEquals("Current graph has edges", 1, g.edgeSize());
        g.remove(1, 5);
        assertEquals("Current graph has vertices", 5, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
    }

    @Test
    public void undirectedGraphVerticesIteration() {
        UndirectedGraph emptyGraph = new UndirectedGraph();
        UndirectedGraph g = initializeUndirectedGraph();
        assertFalse("Current graph has no vertex",
                emptyGraph.vertices().hasNext());
        assertTrue("Current graph has next vertex",  g.vertices().hasNext());

        int i = 1;
        for (int v:g.vertices()) {
            assertEquals("Current graph has vertex using iteration", i, v);
            i++;
        }
        emptyGraph.add();
        assertEquals("Current graph has vertex using iteration",
                Integer.valueOf(1), emptyGraph.vertices().next());
    }



    @Test
    public void undirectedTest() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(2, 1);
        assertEquals(2, g.vertexSize());
        assertEquals(1, g.edgeSize());
    }

    @Test
    public void directedGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        g.add();
        assertEquals("Current graph has vertices", 1, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
        g.remove(1);
        assertEquals("Current graph has vertices", 0, g.vertexSize());
        assertEquals("Current graph has edges", 0, g.edgeSize());
    }

    @Test
    public void diGraphPredecessors() {
        DirectedGraph g = initializeDirectedGraph();
        Set<Integer> expected = new HashSet<>(Arrays.asList(2, 3, 4));
        Set<Integer> result = new HashSet<>();
        for (int i: g.predecessors(1)) {
            result.add(i);
        }
        assertEquals("Current graph has edges", expected, result);
    }


}
