
import java.util.ArrayList;
import java.util.Comparator;

import rbtree.RBTree;
import rbtree.RBTreeVisitor;
/**
 * Visitor class used for determining the length every path
 * from root to empty
 * @author jamcnam
 * @param <T> some data type that the RBTree is parameterized over
 * @version 2013-11-03
 */
public class PathLengths<T> 
    implements RBTreeVisitor<T, ArrayList<Integer>> {
    
    /**keeps track of how deep we have traveled into the tree*/
    int depth;
    
    /**Does this tree have any elements?*/
    boolean treeIsNotEmpty = false;
    
    /**An ArrayList used to keep track of the length of each path*/
    private ArrayList<Integer> paths = new ArrayList<Integer>();
    
    /**EFFECT:
     * Produces an ArrayList
     * Returns the class global ArrayList paths, with the depth value in field
     * 0 appended to end of the list to signify that the path to this empty
     * takes that many steps  
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @param color <code>String</code>
     * @return <code>ArrayList</code> of <code>Integer</code>s
     */
    public ArrayList<Integer> visitEmpty(Comparator<T> comp, String color) {
        if (treeIsNotEmpty) {
            paths.add(depth);
        }
        return paths;
    }

    /**EFFECT:
     * Produces an ArrayList
     * Increments the depth that we have traversed to, adds the length to each
     * of the empty elements in the left side of the tree, resets the 
     * depth counter, and then adds the length to each of the empty nodes in
     * the right subtree
     * When complete, it returns a new array with the depth counter removed 
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @param color <code>String</code>
     * @param data datum object of type T (whatever the RBTree is
     *         parameterized over)
     * @param left <code>RBTree</code> parameterized over type T
     * @param right <code>RBTree</code> parameterized over type T
     * @return <code>ArrayList</code> of <code>Integer</code>s
     */
    public ArrayList<Integer> visitNode(Comparator<T> comp, String color, 
            T data, RBTree<T> left, RBTree<T> right) {
        //If we are in a node class, then this tree has at least one element
        treeIsNotEmpty = true;
        
        //increment the depth counter
        depth = depth + 1;
        
        //save the level of this node
        int level = depth;
        
        //add every path from the left sub-tree, reset the depth counter, 
        //and add every path from the right sub-tree
        left.accept(this);
        depth = level;
        right.accept(this);
   
        return paths;
    }
}
