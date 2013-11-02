package rbtree;

import java.util.Comparator;

/**
 * BT is an abstract Binary Tree representation
 * @author jamcnam
 * @version 2013-10-08
 */
public abstract class BT {

    /**All Red-Black Tree's must be either Red or Black    */
    boolean red;
    
    /**
     * Comparator
     */
    Comparator<String> myCompare;

    /**EFFECT:
     * Abstract function to add the given String to this BT, and
     * cast the root to black
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public abstract BT add(String s, Comparator<String> comp);

    /**EFFECT:
     * Abstract function to insert the given String to this BT, and
     * rebalance the tree via the red-black algorithm
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public abstract BT insert(String s, Comparator<String> comp);

    /**EFFECT:
     * Determines whether this BT is an Empty Tree
     * @return <code>boolean</code>
     */
    public abstract boolean isEmpty();

    /**EFFECT:
     * Determines if the given string is contained in this tree
     * if it is sorted by the given comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    public abstract boolean contains(String s, Comparator<String> comp);

    /**EFFECT:
     * Implements the Red-Black paradigm by looking for instances of 
     * Red-Red families, and running a local refactoring procedure
     * to produce a more balance BST structure
     * @return <code>BT</code>
     */
    protected abstract BT balance();

    /**EFFECT:
     * Determines whether a node is colored red and has a child who is red
     * @return boolean
     */
    protected abstract boolean isRedAndHasRedChild();

    /**EFFECT:
     * Returns this BT's left subtree
     * Only ConsTree's have left subtrees so this must be called with care
     * @return <code>BT</code>
     */
    protected abstract BT getLeft();

    /**EFFECT:
     * Returns this BT's right subtree
     * Only ConsTree's have right subtrees so this must be called with care
     * @return <code>BT</code>
     */
    protected abstract BT getRight();

    /**EFFECT:
     * Returns this BT's left subtree
     * Only ConsTree's have strings so this must be called with care
     * @return <code>String</code>
     */
    protected abstract String getString();

    /**EFFECT:
     * Returns a new BT that exactly mimics this BT,
     * save that is has the opposite color
     * @return <code>BT</code>
     */
    protected abstract BT invertColor();

    /**EFFECT:
     * Returns a new BT that exactly mimics this BT,
     * save that if the Tree was red, it will now be black
     * @return <code>BT</code>
     */
    public abstract BT makeBlack();

    /**EFFECT:
     * returns the number of strings stored in this BT
     * @return <code>int</code>
     */
    protected abstract int count();

    /**EFFECT:
     * Returns the size of the maximum length path from root to node
     * @return <code>int</code>
     */
    protected abstract int height();

    /**EFFECT:
     * returns the count of black nodes in the path from root to node
     * with the least number of black nodes
     * should be exactly equal to max black count
     * @return <code>int</code>
     */
    protected abstract int maxBlackCount();


    /**EFFECT:
     * returns the count of black nodes in the path from root to node
     * with the largest number of black nodes
     * should be exactly equal to min black count
     * @return <code>int</code>
     */
    protected abstract int minBlackCount();

    /**EFFECT:
     * Determines whether this object is a valid representation of a BT
     * @return <code>boolean</code>
     */
    public abstract boolean repOK();

    /**
     * EFFECT:
     * Determines whether every object in the left subtree is less than 
     * this node and every object in the right subtree is greater than 
     * this node
     * Only run when in debug mode
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    public abstract boolean inOrder(Comparator<String> comp);

    /**EFFECT:
     * Ensures that every string in this tree is greater than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected abstract boolean greaterThan(String s, Comparator<String> comp);

    /**EFFECT:
     * Ensures that every string in this tree is less than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected abstract boolean lessThan(String s, Comparator<String> comp);

    /**EFFECT:
     * Returns the string that is located in the left most node of this tree
     * @return <code>String</code>
     */
    public abstract String getFirst();

    /**EFFECT:
     * Returns this BT with the left most node removed
     * @return <code>BT</code>
     */
    public abstract BT getRest();

    /**EFFECT
     * Prints out various statistics about this BT to the console
     */
    public abstract void analytics();
    
    public void setComparator(Comparator<String> comp) {
        myCompare = comp;
    }
}
