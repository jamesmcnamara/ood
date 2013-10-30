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
    protected abstract boolean isEmpty();

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
    
    /**
     * Implements the Red-Black paradigm by looking for instances of 
     * Red-Red families, and running a local refactoring procedure
     * to produce a more balance BST structure
     * @return <code>BT</code>
     */
    protected abstract BT balance();
    
    /**
     * Determines whether a node is colored red and has a child who is red
     * @return boolean
     */
    protected abstract boolean isRedAndHasRedChild();
    
    /**
     * Returns this BT's left subtree
     * Only ConsTree's have left subtrees so this must be called with care
     * @return <code>BT</code>
     */
    protected abstract BT getLeft();
    
    /**
     * Returns this BT's right subtree
     * Only ConsTree's have right subtrees so this must be called with care
     * @return <code>BT</code>
     */
    protected abstract BT getRight();
    
    /**
     * Returns this BT's left subtree
     * Only ConsTree's have strings so this must be called with care
     * @return <code>String</code>
     */
    protected abstract String getString();
    
    /**
     * Returns a new BT that exactly mimics this BT,
     * save that is has the opposite color
     * @return <code>BT</code>
     */
    protected abstract BT invertColor();
    
    /**
     * Returns a new BT that exactly mimics this BT,
     * save that if the Tree was red, it will now be black
     * @return <code>BT</code>
     */
    public abstract BT makeBlack();
    
    /**
     * Determines how many Red nodes in this tree have red children
     * should be 0, used in the repOK
     * @return <code>int</code>
     */
    public abstract int redTest();
    
    /**
     * returns the number of strings stored in this BT
     * @return <code>int</code>
     */
    public abstract int count();
    
    /**
     * Returns the size of the maximum length path from root to node
     * @return <code>int</code>
     */
    public abstract int height();
    
    /**
     * returns the count of black nodes in the path from root to node
     * with the least number of black nodes
     * should be exactly equal to max black count
     * @return <code>int</code>
     */
    public abstract int maxBlackCount();
    

    /**
     * returns the count of black nodes in the path from root to node
     * with the largest number of black nodes
     * should be exactly equal to min black count
     * @return <code>int</code>
     */
    public abstract int minBlackCount();
    
    /**
     * Determines whether this object is a valid representation of a BT
     * @return <code>boolean</code>
     */
    public abstract boolean repOK();
    
    /**
     * EFFECT:
     * Determines whether every object in the left subtree is less than this node
     * and every object in the right subtree is greater than this node
     * WARNING: EXTREMELY TIME CONSUMING O(n^2)
     * Only run when in debug mode
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    public abstract boolean inOrder(Comparator<String> comp);
    
    /**
     * Ensures that every string in this tree is greater than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    public abstract boolean greaterThan(String s, Comparator<String> comp);
    
    /**
     * Ensures that every string in this tree is less than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    public abstract boolean lessThan(String s, Comparator<String> comp);
}
