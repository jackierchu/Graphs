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
        // FIXME FIXED
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
        // FIXME FIXED
    }

    @Override
    public int maxVertex() {
        return max(_vertices);
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
//        int idx = _vertices.indexOf(v);
//        int result = _adjList.get(idx).size();
//        return result;
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
//        if(!contains(u) || !contains(v)){
//            return false;
//        }
//        int uIdx = _vertices.indexOf(u);
//        int vIdx = _vertices.indexOf(v);
//
//        ArrayList<Integer> uValue = _adjList.get(uIdx);
//        ArrayList<Integer> vValue = _adjList.get(vIdx);
//        return (uValue.contains(u) || vValue.contains(v));
        if (!contains(u) || !contains(v)) return false;
        for (int[] e : _edges) {
            if ((e[0] == u  && e[1] == v)
                    || (!isDirected() && e[0] == v && e[1] == u)) {
                return true;
            }
        }
        return false;
        // FIXME FIXED
    }

    /** Implemented method. */
    private boolean edgeContains(int u, int v) { return edgeIndex(u, v) != -1; }

    @Override
    public int add() {
//        ArrayList<Integer> list = new ArrayList<>(_vertices);
//        int min = findMin(list);
//        resize(min);
//        _vertices.add(min);
//        _adjList.add(min, new ArrayList<>());
//        return min;
        int curMin;
        // empty or first add
        if (_vertices.size() == 0) {
            curMin = 1;
        } else {
            // get the current min in _vertices
            curMin = Collections.min(_vertices);
            // a look back to check from 1 to curMin has a slot
            curMin = checkEmptySlot(curMin);
            // find next available integer not in current _vertices list
            while (_vertices.contains(curMin)) {
                curMin++;
            }
        }
        //if curMin <= 1 just return the current and do nothing
        _vertices.add(curMin);
        //add an empty list to adjList
        _adjList.add(new ArrayList<>());
        return curMin;
        // FIXME FIXED
    }

    /** NEW Implemented helper method. */
    private int checkEmptySlot(int max) {
        int minSlot = max;
        for (int i = 1; i <=max ; i++) {
            if (!_vertices.contains(i)){
                return i;
            }
        }
        return minSlot+1;
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
//        if(!contains(u)) {
//            _vertices.add(u);
//            ArrayList<Integer> incomingU = new ArrayList<Integer>();
//            resize(u);
//            incomingU.add(v);
//            _adjList.add(u, incomingU);
//        } else if (!contains(u, v)) {
//            ArrayList<Integer> incomingU = _adjList.get(u);
//            incomingU.add(v);
//        }
//
//        if (!contains(v)) {
//            _vertices.add(v);
//        }
//
//        if (!edgeContains(u, v)) {
//            _edges.add(new int[]{u, v});
//        }
//
//        if (!isDirected()) {
//            resize(v);
//            ArrayList<Integer> incomingV = _adjList.get(v);
//
//            if (incomingV != null) {
//                if (!incomingV.contains(u)) {
//                    incomingV.add(u);
//                }
//            }
//            else {
//                ArrayList<Integer> tempV = new ArrayList<>();
//                tempV.add(u);
//                _adjList.add(v, tempV);
//            }
//        }
//
//        return edgeId(u, v);
        // Leaves U enter V in directed graph
        if (isDirected()) {
            // Add an edge incident on U and V
            if(!edgeContains(u, v)){
                _edges.add(new int[]{u, v});
            }
            // Get index of u
            int uIndex = _vertices.indexOf(u);
            // Get u's adj list
            ArrayList<Integer> uAdjs = _adjList.get(uIndex);
            uAdjs.add(v);
            // update _adjList
            _adjList.set(uIndex, uAdjs);
            // update _edges
        } else { //undirected graph is two way
            int min = Math.min(u,v);
            int max = Math.max(u,v);
            // don't want to add two incidents in _edges
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
        // Returns a unique positive number identifying the edge
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
//        if(contains(v)) {
//            removeEdges(v);
//            int item = _vertices.indexOf(v);
//            _vertices.remove(item);
//            _adjList.set(v, new ArrayList<>());
//            clearUpEdges(v);
//        }
//        // FIXME FIXED
//        clearUp();
        if (contains(v)) {
            // remove from _adjacent list
            removeAdjacentList(v);
            removeEdges(v);
            // remove from _vertices list
//            int vIndex = _vertices.indexOf(v);
//            _vertices.set(vIndex, 0);
            _vertices.remove(Integer.valueOf(v));
        }
        // FIXME FIXED
    }

    /** NEW Implemented method. */
    private void removeAdjacentList(int v) {
        // remove the v from adjacent list by index
        int vIndex = _vertices.indexOf(v);
//        _adjList.remove(vIndex);
        _adjList.set(vIndex, new ArrayList<>());
        // remove v from other's adjacent list
        for (ArrayList<Integer> list : _adjList) {
            list.remove(Integer.valueOf(v));
        }
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
//        ArrayList<int[]> updated = new ArrayList<>();
//        for(int i = 0; i < _edges.size(); i++){
//            int[] pairOfEdges = _edges.get(i);
//
//            if (pairOfEdges[0] != v && pairOfEdges[1] != v) {
//                updated.add(pairOfEdges);
//            }
//        }
//        _edges = updated;
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
//        if (contains(u, v)) {
//            _adjList.get(u).remove(v);
//            if(edgeContains(u, v)) {
//                int item = edgeIndex(u ,v);
//                _edges.remove(item);
//            }
//        }
//        // FIXME FIXED
//        clearUp();
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
        // FIXME FIXED
        return Iteration.iteration(_vertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        // FIXME FIXED
//        return Iteration.iteration(_adjList.get(v));
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
        // Cantor pairing
        if (!isDirected()){
            int max = Math.max(u,v);
            int min = Math.min(u,v);
            u = min;
            v = max;
        }
        return (u + v) * (u + v + 1) * u / 2;
    }
    // FIXME FIXED
}
