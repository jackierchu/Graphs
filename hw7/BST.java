import java.util.LinkedList;
import java.util.Iterator;

/** A binary search tree with arbitrary Objects as keys.
 *  @author Jacqueline Chu
 */
public class BST {
    /** Root of tree. */
    private BSTNode root;

    /** A BST containing the elements in the sorted list LIST. */
    public BST(LinkedList list) {
        root = linkedListToTree(list.iterator(), list.size());
    }

    /**
     * Provide a descriptive comment for this method here. FIXED
     */
    private BSTNode linkedListToTree(Iterator iter, int n) {
        if (n <= 0) {
            return null;
        }

        BSTNode leftNode = linkedListToTree(iter, n/2);
        root = new BSTNode(iter.next());
        BSTNode rightNode = linkedListToTree(iter, n - n / 2 - 1);
        root.left = leftNode;
        root.right = rightNode;
        return root;
    }

    /**
     * Prints the tree to the console.
     */
    private void print() {
        print(root, 0);
    }

    /** Print NODE and its subtrees, indented D levels.  */
    private void print(BSTNode node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    /**
     * Node for BST.
     */
    static class BSTNode {

        /** Item. */
        protected Object item;

        /** Left child. */
        protected BSTNode left;

        /** Right child. */
        protected BSTNode right;
        public BSTNode(Object i){
            item = i;
        }
    }
}
