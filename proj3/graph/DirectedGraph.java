package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int count = 0;
        Iteration<Integer> iterator = predecessors(v);
        while (iterator.hasNext()) {
            iterator.next();
            count ++;
        }
        // FIXME FIXED
        return count;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        Iteration<int[]> iteration = edges();
        ArrayList<Integer> list = new ArrayList<Integer>();

        while (iteration.hasNext()) {
            int[] edge = iteration.next();
            if (edge[1] == v) {
                list.add(edge[0]);
            }
        }
        // FIXME FIXED
        return Iteration.iteration(list);
    }
    // FIXME FIXED
}
