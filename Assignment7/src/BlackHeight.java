

import java.util.Comparator;

import rbtree.RBTree;
import rbtree.RBTreeVisitor;

/**
 * Visitor class used for determining the number of Black nodes in
 * each path from root to empty 
 *  
 * @author jamcnam
 * @version 2013-11-07
 * @param <T> some data type that the RBTree is parameterized over
 */
public class BlackHeight<T> implements RBTreeVisitor<T, Integer> {
    
    /**EFFECT
     * Visit method for the empty node
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @param color <code>String</code> representing the color of this node
     * @return <code>Integer</code> (0 because empty nodes are not counted)
     */
    public Integer visitEmpty(Comparator<T> comp, String color) {
        return 0;
    }
    
    public Integer visitNode(Comparator<T> comp, String color, T data,
            RBTree<T> left, RBTree<T> right) {
        int min = Math.min(left.accept(this), right.accept(this));
        int max = Math.max(left.accept(this), right.accept(this));
        if (min == max) {
            return (color.equals("BLACK") ? min + 1 : min);
        }
        else {
            throw new RuntimeException("This tree has a path with more " + 
                    "black nodes than the others");
        }
    }
}