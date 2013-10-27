package rbtree;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * BT is an abstract Binary Tree representation
 * @author jamcnam
 * @version 2013-10-08
 */
public abstract class BT{

    /**All Red-Black Tree's must be either Red or Black    */
    boolean red;
    
    /**
     * Abstract function to add the given String to this BT
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public abstract BT add(String s, Comparator<String> comp);

    /**
     * Returns an <code>ArrayList</code> representation of this BT 
     * @param arList <code>ArrayList</code> of <code>Strings</code>
     * @return <code>ArrayList</code> of <code>Strings</code>
     */
    public abstract ArrayList<String> listRep(ArrayList<String> arList);

    /**
     * Determines whether this BT is an Empty Tree
     * @return <code>boolean</code>
     */
    abstract boolean isEmpty();

    /**
     * Determines if the given string is contained in this tree
     * if it is sorted by the given comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    public abstract boolean containsM(String s, Comparator<String> comp);
    
    /**
     * Effect: 
     * Produces false if o is not an instance of BTree.
     * Produces true if this tree and the given BTree 
     * contain the same <code>String</code>s and
     * are ordered by the same <code>Comparator</code>.
     * So if the first tree was built with Strings 
     * "hello" "bye" and "aloha" ordered
     * lexicographically,  and the second tree was built 
     * with <code>String</code>s "aloha" "hello" and "bye"  
     * and ordered lexicographically, 
     * the result would be true.
     *
     * @param o the object to compare with this
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
      if (o instanceof BT) {
            return this.equals((BT) o);
        }
        else {
            return false;
        }
    }
    
    abstract BT balance();
    
    abstract boolean hasAdjacentReds();
    
    abstract BT getLeft();
    
    abstract BT getRight();
    
    abstract String getString();
    
    abstract BT invertColor();
    
    public abstract BT makeBlack();
    
    public abstract int blackCount();
    public abstract boolean redTest();

}
