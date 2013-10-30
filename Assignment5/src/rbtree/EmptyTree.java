package rbtree;

import java.util.Comparator;


/**
 * Concrete implementation of an Empty BT
 * @author jamcnam
 * @version 2013-10-08
 */
public class EmptyTree extends BT{

	/**
	 * Constructor
	 * Singleton pattern
	 * 
	 * Representation Invariant:
	 * this.red is boolean
	 */
	private EmptyTree() {
		this.red = false;
	}

	/**The single instance of the EmptyTree class*/
	private static EmptyTree leaf = new EmptyTree();

	/**EFFECT
	 * returns the single Empty Tree instance
	 * @return
	 */
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

	/**EFFECT
	 * Empty Trees are balanced, return this
	 * @return BT
	 */
	protected BT balance() {
		return EmptyTree.getInstance();
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
	 * @return BT
	 */
	protected BT getLeft() {
		throw new RuntimeException("Empty Tree has no left");
	}

	/**EFFECT
	 * Empty Trees have no children, throw exception
	 * @return BT
	 */
	protected BT getRight() {
		throw new RuntimeException("Empty Tree has no right");
	}

	/**EFFECT
	 * Empty Trees have no Strings, throw exception
	 * @return BT
	 */
	protected String getString() {
		throw new RuntimeException("Empty Tree has no String");
	}

	/**EFFECT
	 * Empty Trees are all black, throw exception
	 * @return BT
	 */
	protected BT invertColor() {
		throw new RuntimeException("All Empty Tree's are black");
	}

	/**EFFECT
	 * Empty Trees are all black, throw exception
	 * @return BT
	 */
	public BT makeBlack() {
		return invertColor();
	}

	/**EFFECT
	 * Empty Trees has no elements, return 0
	 * @return int
	 */
	protected int count() {
		return 0;
	}

	/**EFFECT
	 * Empty Trees have no elements, return 0
	 * @return int
	 */
	protected int height() {
		return 0;
	}

	/**EFFECT
	 * Empty Trees have no path from root to node, return 0
	 * @return BT
	 */
	protected int maxBlackCount() {
		return 0;
	}

	/**EFFECT
	 * Empty Trees have no path from root to node, return 0
	 * @return BT
	 */
	protected int minBlackCount() {
		return 0;
	}

	/**EFFECT
	 * Empty trees are all ordered by any comparator
	 * @return boolean
	 */
	public boolean inOrder(Comparator<String> comp) {
		return true;
	}

	/**EFFECT
	 * Empty trees are all ordered by any comparator
	 * @return boolean
	 */
	protected boolean greaterThan(String s, Comparator<String> comp) {
		return true;
	}

	/**EFFECT
	 * Empty trees are all ordered by any comparator
	 * @return boolean
	 */
	protected boolean lessThan(String s, Comparator<String> comp) {
		return true;
	}

	/**EFFECT
     * Empty trees have no first, throw exception
     * @return <code>String</code>
     */
	public String getFirst() {
		throw new RuntimeException("EmptyTree has no string");
	}

	/**EFFECT
     * Empty trees have no rest, throw exception
     * @return <code>BT</code>
     */
	public BT getRest() {
		return this;
	}

	/**EFFECT
     * Empty Trees have no statistics to report on
     */
	public void analytics() {
		System.out.println("This tree is empty.");
	}
}
