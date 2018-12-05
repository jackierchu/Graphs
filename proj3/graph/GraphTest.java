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


    @Test(timeout = 1000)
    public void undirectedGraphTestTogether() {
        UndirectedGraph g = initializeUndirectedGraph();
        g.add(5, 5);
        g.add(1, 2);
        g.add(2, 3);
        g.add(1, 3);
        g.add(1, 4);
        g.remove(1);
        g.add();
        assertEquals("Edge size error", 2, g.edgeSize());
        assertEquals("Max Vertex error", 5, g.maxVertex());
        assertFalse("Edge contains error", g.contains(1, 3));
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

    @Test(timeout = 1000)
    public void directedGraphAddVertex() {
        DirectedGraph g = new DirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        assertEquals("Directed Graph Add Vertex wrong size",
                5, g.vertexSize());
    }

    @Test(timeout = 1000)
    public void directedGraphRemoveVertex() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        assertEquals("Directed Graph remove Vertex wrong size",
                3, g.vertexSize());
        assertTrue("Directed Graph remove item error", g.contains(2));
        g.remove(2);
        assertEquals("Directed Graph remove Vertex wrong size",
                2, g.vertexSize());
        assertTrue("Directed Graph remove item error", g.contains(3));
        assertFalse("Directed Graph remove item error", g.contains(2));
        assertEquals("Directed Graph remove Vertex wrong size",
                2, g.vertexSize());
    }

    @Test(timeout = 1000)
    public void directedGraphRemoveAndAddKeepMin() {
        DirectedGraph g = new DirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        g.remove(1);
        g.remove(2);
        g.remove(3);
        g.remove(4);
        g.add();
        assertTrue("DirectedGraph Add number after remove, should keep min",
                g.contains(1));
        assertTrue("DirectedGraph Add number after remove, should keep min",
                g.contains(5));
        assertFalse("DirectedGraph Add number after remove, should keep min",
                g.contains(6));
        assertFalse("DirectedGraph Add number after remove, should keep min",
                g.contains(4));
    }

    @Test(timeout = 1000)
    public void directedGraphAddEdge() {
        DirectedGraph g = new DirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        g.add(1, 2);
        g.add(2, 3);
        assertTrue("DirectedGraphAddEdge Error", g.contains(2, 3));
        assertFalse("DirectedGraphAddEdge Error", g.contains(3, 2));
    }

    @Test(timeout = 1000)
    public void directedGraphRemoveEdge() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        assertEquals("DG remove edge error", 1, g.edgeSize());
        g.remove(1, 2);
        assertEquals("DG remove edge error", 0, g.edgeSize());
    }

    @Test(timeout = 1000)
    public void undirectedGraphEdgeId() {
        UndirectedGraph g = initializeUndirectedGraph();
        int id1 = g.edgeId(1, 2);
        int id2 = g.edgeId(1, 2);
        int id3 = g.edgeId(2, 1);
        assertEquals("Direct Graph EdgeId Error", id1, id2);
        assertEquals("Direct Graph EdgeId Error", id2, id3);
    }

    @Test(timeout = 1000)
    public void directedGraphEdgeId() {
        DirectedGraph g = new DirectedGraph();
        int count = 5;
        while (count > 0) {
            g.add();
            count--;
        }
        int id1 = g.edgeId(1, 2);
        int id2 = g.edgeId(1, 2);
        int id3 = g.edgeId(2, 1);
        assertEquals("Direct Graph EdgeId Error", id1, id2);
        assertNotEquals("Direct Graph EdgeId Error", id1, id3);
    }

    @Test(timeout = 1000)
    public void directedGraphTestSuccessors() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(1, 3);
        assertEquals("DirectedGraph Successors error", 2, g.outDegree(1));
    }

    @Test(timeout = 1000)
    public void directedGraphTestPredecessors() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 1);
        g.add(3, 1);
        assertEquals("DirectedGraph Successors error", 2, g.inDegree(1));
    }

}
