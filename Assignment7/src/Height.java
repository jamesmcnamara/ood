

import java.util.Comparator;

import rbtree.RBTree;
import rbtree.RBTreeVisitor;

/**
 * Visitor class for the RBTree which returns the max height of the tree 
 * @author jamcnam
 * @version 2013-11-7
 * @param <T> some data type that the RBTree is parametrized over
 */
public class Height<T> implements RBTreeVisitor<T, Integer> {

    /**EFFECT
     * Visit method for the empty node
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @param color <code>String</code> representing the color of this node
     * @return <code>Integer</code> (0 because empty nodes are not counted)
     */
    public Integer visitEmpty(Comparator<T> comp, String color) {
        return 0;
    }
   

    /**EFFECT
     * Visit method for the cons node
     * calculates the maximum height down either the left or right
     * sub-tree, and adds 1 for the current node
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @param color <code>String</code>
     * @param data value of the current node, of object type T 
     * @param left <code>RBTree</code> parametrized over type T
     * @param right <code>RBTree</code> parametrized over type T
     */
    public Integer visitNode(Comparator<T> comp, String color, T data,
            RBTree<T> left, RBTree<T> right) {
        return 1 + Math.max(left.accept(this), right.accept(this));
    }
}
