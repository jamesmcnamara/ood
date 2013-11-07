package rbtree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Visitor class used for determining the length every path
 * from root to empty
 * @author jamcnam
 * @param <T> some data type that the RBTree is parameterized over
 * @version 2013-11-03
 */
public class PathLengths<T> 
    implements RBTreeVisitor<T, ArrayList<Integer>> {
    
    /**An ArrayList used to keep track of the length of each path*/
    private ArrayList<Integer> paths = 
            new ArrayList<Integer>(Arrays.asList(0));
    
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
        paths.add(paths.get(0));
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
        //increment the depth counter
        paths.set(0, paths.get(0) + 1);
        
        //save the depth of this node
        int depth = paths.get(0);
        
        //add every path from the left sub-tree, reset the depth counter, 
        //and add every path from the right sub-tree
        left.accept(this);
        paths.set(0, depth);
        right.accept(this);
        
        //"clone" the paths array list, remove the depth counter, return
        ArrayList<Integer> finalPaths = new ArrayList<Integer>(paths);
        finalPaths.remove(0);
        return finalPaths;
    }
}
