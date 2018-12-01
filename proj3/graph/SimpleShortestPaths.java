package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** All the predecessors of the vertex. */
    int[] predecessors;
    /** Double array that has the weights for every vertex. */
    double[] weights;

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);  // FIXME? FIXED
        int maxVertex = G.maxVertex();
        predecessors = new int[maxVertex + 1];
        weights = new double[maxVertex + 1];
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        // FIXME FIXED
        return weights[v];
    }

    @Override
    protected void setWeight(int v, double w) {
        // FIXME FIXED
        weights[v] = w;
    }

    @Override
    public int getPredecessor(int v) {
        // FIXME
        return predecessors[v];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        // FIXME FIXED
        predecessors[getPredecessor(v)] = u;
    }

    // FIXME FIXED

}
