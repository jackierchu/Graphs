package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.LinkedList;

import java.util.List;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author
 */
public abstract class ShortestPaths {

    /** Implemented function. */
    private final Comparator<Integer> NodeComparator = (o1, o2) -> {
        double edge = getWeight(o1) + estimatedDistance(o1);
        double newEdge = getWeight(o2) + estimatedDistance(o2);
        if (edge < newEdge) {
            return -1;
        } else if (edge > newEdge) {
            return 1;
        }
        return o1 - o2;
    };

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _fringe = new TreeSet<>(NodeComparator);
        // FIXME FIXED
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        // FIXME FIXED
        _fringe.add(_source);
        for (int v : _G.vertices()) {
            setWeight(v, Double.MAX_VALUE);
        }
        setWeight(_source, 0);
        while (_fringe.isEmpty() == false) {
            int currentOne = _fringe.pollFirst();
            if (currentOne == _dest) return;
            for (int successor: _G.successors(currentOne)) {
                double old = getWeight(successor);
                double newer = getWeight(currentOne) + getWeight(currentOne, successor);
                if (old > newer) {
                    _fringe.remove(successor);
                    setWeight(successor, newer);
                    _fringe.add(successor);
                    setPredecessor(successor, currentOne);
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        // FIXME FIXED
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        // FIXME FIXED
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        // FIXME FIXED
        LinkedList<Integer> result = new LinkedList<>();
        while (getPredecessor(v) != 0) {
            result.addFirst(v);
            v = getPredecessor(v);
        }
        if (result.getFirst() != _source) {
            result.addFirst(_source);
        }
        return result;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    // FIXME FIXED

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    // FIXME FIXED
    /** Implemented the fringe. */
    protected TreeSet<Integer> _fringe;

}
