package rbtree;


import java.util.Comparator;


/**
 * Concrete implementation of an Empty RBTree
 * @author jamcnam
 * @version 2013-10-08
 */
public class EmptyTree<T> extends RBTree<T> {

    /**
     * Constructor
     * 
     * Representation Invariant:
     * this.red is boolean
     */
    protected EmptyTree(Comparator<T> myCompare) {
        this.myCompare = myCompare;
        this.red = false;
        
    }
    
    public static <T> EmptyTree<T> emptyFactory(Comparator<T> comp) {
        return new EmptyTree<T>(comp);
    }

    /**
     * Boolean method that can be used to test if this is a legitimate
     * representation of an EmptyTree 
     * @return boolean
     */
    public boolean repOK() {
        return this instanceof EmptyTree &&
                !red;
    }

    /**
     * Determines if this RBTree<T> is an EmptyTree
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * Returns a <code>String</code> representation of this tree
     * @return <code>String</code>
     */
    public String toString() {
        return "";
    }

    /**
     * Effect: 
     * Produces false if o is not an instance of RBTreeree.
     * Produces true if this tree and the given RBTreeree 
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
        if (o instanceof EmptyTree) {
            return equals((EmptyTree<?>) o);
        }
        else {
            return false;
        }
    }

    /**
     * Determines if this <code>EmptyTree</code> is equal to the given 
     * RBTreeree
     * @param bt <code>RBTree</code>
     * @return <code>boolean</code>
     */
    public boolean equals(EmptyTree<?> bt) {
        return bt.isEmpty() && bt.myCompare.equals(myCompare);
    }

    /**
     * Returns a hash representation of this RBTree<T> 
     * @return <code>int</code>
     */
    public int hashCode() {
        return 0;
    }


    /**
     * Returns a RBTree<T> which represents this RBTree<T> plus the given 
     * <code>String</code> inserted in sorted order
     * @param s <code>String</code> 
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public RBTree<T> add(T t) {
        return insert(t);
    }

    /**
     * Returns a RBTree<T> which represents this RBTree<T> plus the given 
     * <code>String</code> inserted in sorted order
     * @param s <code>String</code> 
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public RBTree<T> insert(T t) {
        return new ConsTree<T>(t, myCompare, this, this, true);
    }

    /**
     * Determines whether this tree contains the given string, 
     * sorted by the given comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    public boolean contains(T t) {
        return false;
    }

    /**EFFECT
     * Empty Trees are balanced, return this
     * @return RBTree
     */
    protected RBTree<T> balance() {
        return this;
    }

    /**EFFECT
     * Empty Trees have no children, return false
     * @return boolean
     */
    protected boolean isRedAndHasRedChild() {
        return false;
    }

    /**EFFECT
     * Empty Trees have no children, throw exception
     * @return RBTree
     */
    protected RBTree<T> getLeft() {
        throw new RuntimeException("Empty Tree has no left");
    }

    /**EFFECT
     * Empty Trees have no children, throw exception
     * @return RBTree
     */
    protected RBTree<T> getRight() {
        throw new RuntimeException("Empty Tree has no right");
    }

    /**EFFECT
     * Empty Trees have no Strings, throw exception
     * @return RBTree
     */
    protected T getValue() {
        throw new RuntimeException("Empty Tree has no String");
    }

    /**EFFECT
     * Empty Trees are all black, throw exception
     * @return RBTree
     */
    protected RBTree<T> invertColor() {
        throw new RuntimeException("All EmptyTrees are black");
    }

    /**EFFECT
     * Empty Trees are all black, throw exception
     * @return RBTree
     */
    public RBTree<T> makeBlack() {
        return this;
    }

    /**EFFECT
     * Empty Trees has no elements, return 0
     * @return int
     */
    public int count() {
        return 0;
    }

    /**EFFECT
     * Empty Trees have no elements, return 0
     * @return int
     */
    public int height() {
        return 0;
    }

    /**EFFECT
     * Empty Trees have no path from root to node, return 0
     * @return RBTree
     */
    public int maxBlackCount() {
        return 0;
    }

    /**EFFECT
     * Empty Trees have no path from root to node, return 0
     * @return RBTree
     */
    public int minBlackCount() {
        return 0;
    }

    /**EFFECT
     * Empty trees are all ordered by any comparator
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    public boolean inOrder() {
        return true;
    }

    /**EFFECT
     * Empty trees are all ordered by any comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    protected boolean greaterThan(T t) {
        return true;
    }

    /**EFFECT
     * Empty trees are all ordered by any comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return boolean
     */
    protected boolean lessThan(T t) {
        return true;
    }

    /**EFFECT
     * Empty trees have no first, throw exception
     * @return <code>String</code>
     */
    public T getFirst() {
        throw new RuntimeException("EmptyTree has no string");
    }

    /**EFFECT
     * Empty trees have no rest, throw exception
     * @return <code>RBTree</code>
     */
    public RBTree<T> getRest() {
        System.out.println("Get Rest " + myCompare.toString());
        return this;
    }

    /**EFFECT
     * Empty Trees have no statistics to report on
     */
    public void analytics() {
        System.out.println("This tree is empty.");
    }
    
    /**
     * 
     */
    public <R> R accept(RBTreeVisitor<T, R> visitor) {
        return visitor.visitEmpty(myCompare, (red ? "RED":"BLACK"));
    }
    
   
}
