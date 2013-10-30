package rbtree;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Concrete implementation of an Empty BT
 * @author jamcnam
 * @version 2013-10-08
 */
public class EmptyTree extends BT{

    /**
     * Constructor
     * @param comp
     * 
     * Representation Invariant:
     * this.myCompare is of type Comparator<String>
     */
    private EmptyTree() {
        this.red = false;
    }
    
    private static EmptyTree leaf = new EmptyTree();
    
    public static EmptyTree getInstance() {
        return leaf;
    }
    
    /**
     * Boolean method that can be used to test if this is a legitimate
     * representation of an EmptyTree 
     * @return boolean
     */
    public boolean repOK() {
        return this instanceof EmptyTree &&
        		this.red == false;
    }

    /**
     * Determines if this BT is an EmptyTree
     * @return <code>boolean</code>
     */
    public boolean isEmpty(){
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
     * Determines if this <code>EmptyTree</code> is equal to the given 
     * BTree
     * @param bt <code>BT</code>
     * @return <code>boolean</code>
     */
    public boolean equals(BT bt) {
        return bt.isEmpty();
    }

    /**
     * Returns a hash representation of this BT 
     * @return <code>int</code>
     */
    public int hashCode() {
        return 0;
    }

    /**
     * Generates an ArrayList representation of this BT
     * @return <code>ArrayList</code> of <code>Strings</code>
     */
    public ArrayList<String> listRep(ArrayList<String> arList) {
        return arList;
    }

    /**
     * Returns a BT which represents this BT plus the given 
     * <code>String</code> inserted in sorted order
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public BT add(String s, Comparator<String> comp) {
        return new ConsTree(s,getInstance(), getInstance(), true);
    }

    /**
     * Determines whether this tree contains the given string, 
     * sorted by the given comparator
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     */
    public boolean containsM(String s, Comparator<String> comp) {
        return false;
    }
    
    protected BT balance() {
        return EmptyTree.getInstance();
    }
    
    protected boolean isRedAndHasRedChild() {
        return false;
    }
    
    protected BT getLeft() {
        throw new RuntimeException("Empty Tree has no left");
    }
    
    protected BT getRight() {
        throw new RuntimeException("Empty Tree has no right");
    }
    
    protected String getString() {
        throw new RuntimeException("Empty Tree has no String");
    }
    
    protected BT invertColor() {
        throw new RuntimeException("All Empty Tree's are black");
    }
    
    public int redTest() {
        return 0;
    }

    public BT makeBlack() {
        return invertColor();
    }
    
    public int count() {
    	return 0;
    }
    
    public int height() {
    	return 0;
    }
    
    public int maxBlackCount() {
    	return 0;
    }
    
    public int minBlackCount() {
    	return 0;
    }
    
    /**
     *  
     */
    public boolean inOrder(Comparator<String> comp) {
    	return true;
    }
}
