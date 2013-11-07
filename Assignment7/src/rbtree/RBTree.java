package rbtree;

import java.util.Comparator;

/**
 * RBTree is an abstract Binary Tree representation
 * @author jamcnam
 * @version 2013-10-08
 */
public abstract class RBTree<T> {

    /**All Red-Black Tree's must be either Red or Black    */
    boolean red;
    
    /**
     * Comparator
     */
    Comparator<T> myCompare;

    /**EFFECT:
     * Abstract function to add the given String to this RBTree, and
     * cast the root to black
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public abstract RBTree<T> add(T t);

    /**EFFECT:
     * Abstract function to insert the given String to this RBTree, and
     * rebalance the tree via the red-black algorithm
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public abstract RBTree<T> insert(T t);

    /**EFFECT:
     * Determines whether this RBTree is an Empty Tree
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
    public abstract boolean contains(T t);

    /**EFFECT:
     * Implements the Red-Black paradigm by looking for instances of 
     * Red-Red families, and running a local refactoring procedure
     * to produce a more balance BST structure
     * @return <code>RBTree</code>
     */
    protected abstract RBTree<T> balance();

    /**EFFECT:
     * Determines whether a node is colored red and has a child who is red
     * @return boolean
     */
    protected abstract boolean isRedAndHasRedChild();

    /**EFFECT:
     * Returns this RBTree's left subtree
     * Only ConsTree's have left subtrees so this must be called with care
     * @return <code>RBTree</code>
     */
    protected abstract RBTree<T> getLeft();

    /**EFFECT:
     * Returns this RBTree's right subtree
     * Only ConsTree's have right subtrees so this must be called with care
     * @return <code>RBTree</code>
     */
    protected abstract RBTree<T> getRight();

    /**EFFECT:
     * Returns this RBTree's left subtree
     * Only ConsTree's have strings so this must be called with care
     * @return <code>String</code>
     */
    protected abstract T getValue();

    /**EFFECT:
     * Returns a new RBTree that exactly mimics this RBTree,
     * save that is has the opposite color
     * @return <code>RBTree</code>
     */
    protected abstract RBTree<T> invertColor();

    /**EFFECT:
     * Returns a new RBTree that exactly mimics this RBTree,
     * save that if the Tree was red, it will now be black
     * @return <code>RBTree</code>
     */
    public abstract RBTree<T> makeBlack();

    /**EFFECT:
     * returns the number of strings stored in this RBTree
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
     * Determines whether this object is a valid representation of a RBTree
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
    public abstract boolean inOrder();

    /**EFFECT:
     * Ensures that every string in this tree is greater than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected abstract boolean greaterThan(T t);

    /**EFFECT:
     * Ensures that every string in this tree is less than the given string 
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected abstract boolean lessThan(T t);

    /**EFFECT:
     * Returns the string that is located in the left most node of this tree
     * @return <code>String</code>
     */
    public abstract T getFirst();

    /**EFFECT:
     * Returns this RBTree with the left most node removed
     * @return <code>RBTree</code>
     */
    public abstract RBTree<T> getRest();

    /**EFFECT
     * Prints out various statistics about this RBTree to the console
     */
    public abstract void analytics();
    
    /**EFFECT
     * Hook for allowing visitors to extend the functionality of the
     * implementation
     * Returns an object of a type specified by the 
     * 
     * @param visitor
     * @return
     */
    public abstract <R> R accept(RBTreeVisitor<T, R> visitor);
}
