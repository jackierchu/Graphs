package graph;
import net.sf.saxon.ma.arrays.ArrayFunctionSet;

import java.util.ArrayList;
import java.util.Collections;
/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author
 */
abstract class GraphObj extends Graph {

    /** An ArrayList containing all vertices in the graph. */
    private ArrayList<Integer> _vertices;

    /** An ArrayList containing edges incident on two vertices. */
    private ArrayList<int[]> _edges;

    /** An ArrayList containing all vertices in the graph. */
    private ArrayList<ArrayList<Integer>> _adjList;

    /** A new, empty Graph. */
    GraphObj() {
        _vertices = new ArrayList<Integer>();
        _edges = new ArrayList<int[]>();
        _adjList = new ArrayList<ArrayList<Integer>>();
        // FIXME FIXED
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
        // FIXME FIXED
    }

    @Override
    public int maxVertex() {
        return Collections.max(_vertices);
        // FIXME FIXED
    }

    @Override
    public int edgeSize() {
        return _edges.size();
        // FIXME FIXED
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        int idx = _vertices.indexOf(v);
        int result = _adjList.get(idx).size();
        return result;
        // FIXME FIXED
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.contains(u);
        // FIXME FIXED
    }

    @Override
    public boolean contains(int u, int v) {
        if(!contains(u)) return false;

        ArrayList<Integer> incomingU = _adjList.get(u);

        if(incomingU != null){
            return incomingU.contains(v);
        }
        return false;
        // FIXME FIXED
    }

    /** Implemented method. */
    private boolean edgeContains(int u, int v) { return edgeIndex(u, v) != -1; }

    @Override
    public int add() {
        ArrayList<Integer> list = new ArrayList<>(_vertices);
        int min = findMin(list);
        resize(min);
        _vertices.add(min);
        _adjList.add(min, new ArrayList<>());
        return min;
        // FIXME FIXED
    }

    /** Implemented Helper Function. */
    private void resize(int x) {
        int current = vertexSize();
        if (x >= current) {
            while (_adjList.size() <= x) {
                _adjList.add(new ArrayList<>());
            }
        }
    }
    /** Implemented Helper Function. */
    private int findMin(ArrayList<Integer> vertices){
        int size = vertices.size();
        for(int i = 0; i < size; i++){
            int value = Math.abs(vertices.get(i));
            if(value - 1 < size && vertices.get(value - 1) > 0){
                int reverse = - vertices.get(value - 1);
                vertices.set(value - 1, reverse);
            }
        }
        for(int i = 0; i < size; i++){
            if (vertices.get(i) > 0) {
                return i+1;
            }
        }
        return size + 1;
    }

    @Override
    public int add(int u, int v) {
        if(!contains(u)) {
            _vertices.add(u);
            ArrayList<Integer> incomingU = new ArrayList<Integer>();
            resize(u);
            incomingU.add(v);
            _adjList.add(u, incomingU);
        } else if (!contains(u, v)) {
            ArrayList<Integer> incomingU = _adjList.get(u);
            incomingU.add(v);
        }

        if (!contains(v)) {
            _vertices.add(v);
        }

        if (!edgeContains(u, v)) {
            _edges.add(new int[]{u, v});
        }

        if (!isDirected()) {
            resize(v);
            ArrayList<Integer> incomingV = _adjList.get(v);

            if (incomingV != null) {
                if (!incomingV.contains(u)) {
                    incomingV.add(u);
                }
            }
            else {
                ArrayList<Integer> tempV = new ArrayList<>();
                tempV.add(u);
                _adjList.add(v, tempV);
            }
        }

        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if(contains(v)) {
            removeEdges(v);
            int item = _vertices.indexOf(v);
            _vertices.remove(item);
            _adjList.set(v, new ArrayList<>());
            clearUpEdges(v);
        }
        // FIXME FIXED
        clearUp();
    }

    /** Implemented helper method for remove(int v). */
    private void clearUp() {
        if (vertexSize() == 0) {
            _adjList.clear();
            _vertices.clear();
            _edges.clear();
        }
    }

    /** Implemented helper method for remove(int v). */
    private void clearUpEdges(int v) {
        for(int i = 0; i < _adjList.size(); i++){
            ArrayList<Integer> adj = _adjList.get(i);
            if (adj != null && adj.contains(v)) {
                int removeThis = adj.indexOf(v);
                adj.remove(removeThis);
            }
        }
    }

    /** Implemented helper method for remove(int v). */
    private void removeEdges(int v) {
        ArrayList<int[]> updated = new ArrayList<>();
        for(int i = 0; i < _edges.size(); i++){
            int[] pairOfEdges = _edges.get(i);

            if (pairOfEdges[0] != v && pairOfEdges[1] != v) {
                updated.add(pairOfEdges);
            }
        }
        _edges = updated;
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            _adjList.get(u).remove(v);
            if(edgeContains(u, v)) {
                int item = edgeIndex(u ,v);
                _edges.remove(item);
            }
        }
        // FIXME FIXED
        clearUp();
    }

    /** Implemented helper method that is used in remove(int u, int v) and at the top. */
    private int edgeIndex(int u, int v) {
        int size = _edges.size();
        for (int i = 0; i < size; i++) {
            int[] edge = _edges.get(i);
            if(edge[0] == u && edge[1] == v) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iteration<Integer> vertices() {
        // FIXME FIXED
        return Iteration.iteration(_vertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        // FIXME FIXED
        return Iteration.iteration(_adjList.get(v));
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        // FIXME FIXED
        return Iteration.iteration(_edges);
    }

    @Override
    protected void checkMyVertex(int v) {
        // FIXME FIXED
        super.checkMyVertex(v);
    }

    @Override
    protected int edgeId(int u, int v) {
        // FIXME FIXED
        return edgeIndex(u, v);
    }

    // FIXME FIXED
}
