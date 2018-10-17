import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of a BST based String Set.
 * @author Jacqueline Chu
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    /** Implemented function that returns if a node is in the tree
     * or if there is no node. */
    private Node locater(String s) {
        if (root == null) {
            return null;
        }
        Node n;
        n = root;
        while (true) {
            int c = s.compareTo(n.s);
            Node afternext;
            if (c < 0) {
                afternext = n.left;
            } else if (c > 0) {
                afternext = n.right;
            } else {
                return n;
            }
            if (afternext == null) {
                return n;
            } else {
                n = afternext;
            }
        }
    }

    @Override
    public void put(String s) {
        Node endResult = locater(s);
        if (endResult == null) {
            root = new Node(s);
        } else {
            int x = s.compareTo(endResult.s);
            if (x < 0) {
                endResult.left = new Node(s);
            } else if (x > 0) {
                endResult.right = new Node(s);
            }
        }
    }

    @Override
    public boolean contains(String s) {
        Node end = locater(s);
        return end != null && s.equals(end.s);
    }

    public List<String> newList(Node n) {
        ArrayList<String> output = new ArrayList<String>();
        if (n.left != null) {
            output.addAll(newList(n.left));
        }
        output.add(n.s);
        while (n.right != null) {
            output.addAll(newList(n.right));
        }
        return output;

    }

    @Override
    public List<String> asList() {
        return newList(root);
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
}
