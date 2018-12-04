package graph;
import net.sf.saxon.ma.arrays.ArrayFunctionSet;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Collections.max;
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
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
    }

    @Override
    public int maxVertex() {
        return max(_vertices);
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        ArrayList<Integer> filled = new ArrayList<>();
        int maxValue = max(_vertices);
        for (int i=1; i<=maxValue; i++){
            if(_vertices.contains(i)){
                filled.add(i);
            } else {
                filled.add(0);
            }
        }
        int vIndex = filled.indexOf(v);
        if(vIndex == -1){
            return 0;
        }
        return _adjList.get(vIndex).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (!contains(u) || !contains(v)) return false;
        for (int[] e : _edges) {
            if ((e[0] == u  && e[1] == v)
                    || (!isDirected() && e[0] == v && e[1] == u)) {
                return true;
            }
        }
        return false;
    }

    /** Implemented method. */
    private boolean edgeContains(int u, int v) { return edgeIndex(u, v) != -1; }

    @Override
    public int add() {
        int curMin;
        if (_vertices.size() == 0) {
            curMin = 1;
        } else {
            curMin = Collections.min(_vertices);
            curMin = checkEmptySlot(curMin);
            while (_vertices.contains(curMin)) {
                curMin++;
            }
        }
        _vertices.add(curMin);
        _adjList.add(new ArrayList<>());
        return curMin;
    }

    /** NEW Implemented helper method. */
    private int checkEmptySlot(int max) {
        int minSlot = max;
        for (int i = 1; i <=max ; i++) {
            if (!_vertices.contains(i)) {
                return i;
            }
        }
        return minSlot+1;
    }

    @Override
    public int add(int u, int v) {
        if (isDirected()) {
            if(!edgeContains(u, v)){
                _edges.add(new int[]{u, v});
            }
            int uIndex = _vertices.indexOf(u);
            ArrayList<Integer> uAdjs = _adjList.get(uIndex);
            uAdjs.add(v);
            _adjList.set(uIndex, uAdjs);
        } else {
            int min = Math.min(u,v);
            int max = Math.max(u,v);
            if(!edgeContains(min, max)){
                _edges.add(new int[]{min, max});
            }

            int uIndex = _vertices.indexOf(u);
            int vIndex = _vertices.indexOf(v);
            ArrayList<Integer> uAdjs = _adjList.get(uIndex);
            ArrayList<Integer> vAdjs = _adjList.get(vIndex);
            if(!uAdjs.contains(v)){
                uAdjs.add(v);
            }
            if(!vAdjs.contains(u)){
                vAdjs.add(u);
            }
            _adjList.set(uIndex, uAdjs);
            _adjList.set(vIndex, vAdjs);
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            removeAdjacentList(v);
            removeEdges(v);
            _vertices.remove(Integer.valueOf(v));
        }
    }

    /** NEW Implemented method. */
    private void removeAdjacentList(int v) {
        int vIndex = _vertices.indexOf(v);
        _adjList.set(vIndex, new ArrayList<>());
        for (ArrayList<Integer> list : _adjList) {
            list.remove(Integer.valueOf(v));
        }
    }

    /** Implemented helper method for remove(int v). */
    private void removeEdges(int v) {
        if (!_edges.isEmpty()) {
            ArrayList<int[]> updated = new ArrayList<>();
            for (int i = 0; i < _edges.size(); i++) {
                int[] pair = _edges.get(i);

                if (pair[0] != v && pair[1] != v) {
                    updated.add(pair);
                }
            }
            _edges = updated;
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            if (edgeContains(u, v)) {
                int item = edgeIndex(u, v);
                _edges.remove(item);
            }
        }
    }

    /** Implemented helper method that is used in remove(int u, int v) and at the top. */
    private int edgeIndex(int u, int v) {
        int size = _edges.size();
        for (int i = 0; i < size; i++) {
            int[] edge = _edges.get(i);
            if(!isDirected() && u > v) {
                if (edge[0] == v && edge[1] == u) {
                    return i;
                }
            } else if (edge[0] == u && edge[1] == v) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(_vertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> filled = new ArrayList<>();
        int maxValue = max(_vertices);
        for (int i=1; i<=maxValue; i++){
            if(_vertices.contains(i)){
                filled.add(i);
            } else {
                filled.add(0);
            }
        }
        int vIndex = filled.indexOf(v);
        if(vIndex == -1){
            return Iteration.iteration(new ArrayList<>());
        }
        return Iteration.iteration(_adjList.get(vIndex));
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(_edges);
    }

    @Override
    protected void checkMyVertex(int v) {
        super.checkMyVertex(v);
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected()){
            int max = Math.max(u,v);
            int min = Math.min(u,v);
            u = min;
            v = max;
        }
        return (u + v) * (u + v + 1) * u / 2;
    }
}
