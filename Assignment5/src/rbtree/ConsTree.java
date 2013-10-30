package rbtree;

import java.util.Comparator;



/**
 * Concrete implementation of a Red-Black BST with Values 
 * @author jamcnam
 * @version 2013-10-08
 */
public class ConsTree extends BT{
    /**
     * Left Binary Tree
     */
    BT myLeft;

    /**
     * Right Binary Tree
     */
    BT myRight;

    /**
     * Current Node's value 
     */
    String myString;

    /**
     * Constructor
     * @param comp <code>Comparator</code>
     * @param left <code>BT</code>
     * @param right <code>BT</code>
     * @param value <code>String</code>
     * 
     * Representation Invariant
     * this.myCompare is of type Comparator<String> &&
     * this.myLeft is of type BT &&
     * this.myRight is of type BT &&
     * this.myString.getClass() == "String" &&
     * this.myCompare.compare(myString, s) > 0 for all myStrings s in myLeft &&
     * this.myCompare.compare(myString, s) < 0 for all myStrings s in myRight &&
     * this.red is of type boolean &&
     * if (this.red) -> !this.myLeft.red &&
     * (this.red) -> !this.myRight.red &&
     * Every route from root to node has the same count of black nodes 
     * 
     */
    ConsTree(String value, BT left, BT right, boolean color) {
        myString = value;
        myLeft = left;
        myRight = right;
        red = color;
    }
    
    /**EFFECT
     * Boolean method that can be used in testing to verify that this object
     * is a legal representation
     * @return boolean
     */
    public boolean repOK() {
    	return (myLeft instanceof BT) &&
    			(myRight instanceof BT) &&
    			(myString instanceof String) &&
    			(red == true || red == false) &&
    			(!myLeft.isRedAndHasRedChild()) &&
    			(!myRight.isRedAndHasRedChild()) &&
    			(myRight.minBlackCount() == myRight.maxBlackCount()) &&
    			(myLeft.minBlackCount() == myLeft.maxBlackCount()) &&
    			(myRight.minBlackCount() == myLeft.maxBlackCount()) &&
    			myLeft.repOK() && myRight.repOK();
    }
    
    
    /**EFFECT:
     * Determines whether this Tree is empty
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return false;
    }
    
    /**EFFECT:
     * Concatenates the <code>String</code>s in the tree into one 
     * <code>String</code>
     * @return <code>String</code>
     */
    public String toString() {
        String s = "";
        //If the left side is a ConsTree, add its values to the String
        if (!myLeft.isEmpty()) {
            s = myLeft.toString() + ", ";
        }
        
        //add my value to the string
        s = s + myString;
        
        //if the right tree is a ConsTree, add its values to the String
        if (!myRight.isEmpty()) {
            s = s + ", " + myRight.toString();
        }
        
        return s;
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
    
    /**EFFECT:
     * Determines if this ConsTree is equal to the given BT
     * @param bt <code>BT</code>
     * @return <code>boolean</code>
     */
    public boolean equals(BT bt) {
        if (bt instanceof ConsTree) {
            return this.equals((ConsTree) bt);
        }
        else {
            return false;
        }
    }
    
    /**EFFECT
     * Determines if this ConsTree is equal to the given ConsTree
     * @param bt <code>BT</code>
     * @return <code>boolean</code>
     */
    public boolean equals(ConsTree bt) {
        return bt.myString.equals(bt.myString)
                && myLeft.equals(bt.myLeft) 
                && myRight.equals(bt.myRight);
    }
    
    /**EFFECT
     * Returns an integer representation of this ConsTree
     * @return <code>int</code>
     */
    public int hashCode() {
        return myLeft.hashCode() * 10 + myString.hashCode() 
                + myRight.hashCode() * 10;
    }

    /**EFFECT
     * Returns a BT that represents this tree plus the given String
     * sorted by this BT's Comparator
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public BT add(String s, Comparator<String> myCompare) {
        int compare = myCompare.compare(s, myString);
        
        //if the given string is less than this string,
        //put it into the left subtree and rebalance
        if (compare < 0) {
            return new ConsTree(myString, myLeft.add(s, myCompare), 
                    myRight, red).balance();
        }
        //if the given string is greater than this string,
        //put it into the right subtree and rebalance
        else if (compare > 0) {
            return new ConsTree(myString, myLeft, 
                    myRight.add(s, myCompare), red).balance(); 
        }
        
        //if the string is already in this sub tree, do nothing
        else {
            return this;
        }
    }

    /**EFFECT
     * Determines whether this tree contains the given <code>String</code>
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>Boolean</code>
     */
    public boolean containsM(String s, Comparator<String> myCompare) {
        int comp = myCompare.compare(myString, s);
        if (comp < 0) {
            return this.myRight.containsM(s, myCompare);
        }
        else if (comp > 0) {
            return this.myLeft.containsM(s, myCompare);
        }
        else {
            return true;
        }
    }
    
    /**EFFECT
     * Examines a local area for unbalanced nodes and 
     * returns a node that is locally balanced 
     * Specifically, this examines whether the current 
     * node is the black grandparent of a Red node with 
     * a Red child
     * If so, it examines which of the four possible 
     * combinations it is, and applies the specific
     * refactoring necessary to ensure that this part
     * of the tree is balanced
     * @return <code>BT</code>
     */
    protected BT balance() {
        if (!red && myLeft.isRedAndHasRedChild()) {
        	
        	//if the left and the left's left are red,
        	//the left tree comes up and everything 
        	//else falls into it's relative place
            if (this.myLeft.getLeft().red) {
                BT newLeft = myLeft.getLeft().invertColor();
                BT newRight = new ConsTree(myString, myLeft.getRight(),
                        myRight, false);
                return new ConsTree(myLeft.getString(), newLeft, 
                        newRight, true);
            }
            //if the left and the left's right are red,
        	//the left's right comes up and everything 
        	//else falls into it's relative place
            else {
                BT newLeft = new ConsTree(myLeft.getString(), 
                        myLeft.getLeft(), 
                        myLeft.getRight().getLeft(),
                        false);
                BT newRight = new ConsTree(myString,
                        myLeft.getRight().getRight(),
                        myRight,
                        false);
                return new ConsTree(myLeft.getRight().getString(),
                        newLeft,
                        newRight,
                        true);
            }
        }
        else if (!red && myRight.isRedAndHasRedChild()) {
        	//if the right and the rights's left are red,
        	//the right's left comes up and everything 
        	//else falls into it's relative place
            if (this.myRight.getLeft().red) {
                BT newLeft = new ConsTree (myString, myLeft,
                        myRight.getLeft().getLeft(),
                        false);
                BT newRight = new ConsTree(myRight.getString(),
                        myRight.getLeft().getRight(),
                        myRight.getRight(),
                        false);
                return new ConsTree(myRight.getLeft().getString(),
                        newLeft,
                        newRight,
                        true);
            }
            //if the right and the right's right are red,
        	//the right tree comes up and everything 
        	//else falls into it's relative place
            else {
                BT newLeft = new ConsTree(myString,
                        myLeft,
                        myRight.getLeft(),
                        false);
                BT newRight = myRight.getRight().invertColor();
                return new ConsTree(myRight.getString(),
                        newLeft,
                        newRight,
                        true);
            }
        }
        //else the local area is balanced
        else return this;
    }
    
    /**EFFECT
     * Returns the string that is furthest to the left in this tree
     * @return <code>String</code> 
     */
    public String getFirst() {
    	if (myLeft.isEmpty()) {
    		return myString;
    	}
    	else {
    		return myLeft.getFirst();
    	}
    }
    
    /**EFFECT
     * Returns a tree that mimics this BT, except that it lacks the 
     * furthest left String
     * @return <code>BT</code> 
     */
    public BT getRest() {
    	if (myLeft.isEmpty()) {
    		if (myRight.isEmpty()) {
    			return EmptyTree.getInstance();
    		}
    		else {
    			return myRight;
    		}
    	}
    	else {
    		//If my left is not empty, return all elements in this tree,
    		// along with the getRest of the left subtree
    		return new ConsTree(myString, myLeft.getRest(), myRight, red);
    	}
    }
    
    /**EFFECT
     * Determines whether this node is red and has a child which is red
     * @return boolean
     */
    protected boolean isRedAndHasRedChild() {
        return this.red && (this.myLeft.red || this.myRight.red);
    }
    
    /**PRODUCES
     * This tree's left tree
     * @return <code>BT</code> 
     */
    protected BT getLeft() {
        return this.myLeft;
    }
    
    /**PRODUCES
     * This tree's right tree
     * @return <code>BT</code> 
     */
    protected BT getRight() {
        return this.myRight;
    }
    
    /**PRODUCES
     * This tree's right tree
     * @return <code>String</code> 
     */
    protected String getString() {
        return this.myString;
    }
    
    /**PRODUCES
     * This tree, with its color flipped
     * @return <code>BT</code> 
     */
    protected BT invertColor() {
        return new ConsTree(myString, myLeft, myRight, !red);
    }
    
    /**PRODUCES
     * This tree, but if it's color had previously been red,
     * it will now be black
     * @return <code>BT</code> 
     */
    public BT makeBlack() {
        if (red) {
            return this.invertColor();
        }
        else return this;
    }
    
    /**EFFECT
     * Returns the number of strings in this tree
     * @return <code>int</code>
     */
    protected int count() {
    	return 1 + myLeft.count() + myRight.count();
    }
    
    /**EFFECT
     * Returns the number of levels in this tree
     * For a tree of count n, this should be 
     * on the order of lg n
     * @return <code>int</code>
     */
    protected int height() {
    	return 1 + Math.max(myLeft.height(), myRight.height());
    }
    
    /**EFFECT
     * Finds the path in this tree from root to node which has the most
     * black nodes
     * Should be equivalent to the path which has the least black nodes
     * @return <code>int</code>
     */
    protected int maxBlackCount() {
    	if (red) {
    		return Math.max(myLeft.maxBlackCount(), 
    				myRight.maxBlackCount());
    	}
    	else {
    		return 1 + Math.max(myLeft.maxBlackCount(), 
    				myRight.maxBlackCount());
    	}
    }
    /**EFFECT
     * Finds the path in this tree from root to node which has the least
     * black nodes
     * Should be equivalent to the path which has the most black nodes
     * @return <code>int</code>
     */
    protected int minBlackCount() {
    	if (red) {
    		return Math.min(myLeft.minBlackCount(), 
    				myRight.minBlackCount());
    	}
    	else {
    		return 1 + Math.min(myLeft.minBlackCount(), 
    				myRight.minBlackCount());
    	}
    }
    
    /**EFFECT
     * Determines whether every string in this tree is greater 
     * than every string in its left subtree and less than 
     * every string in its right subtree, as compared via
     * the given <code>Comparator</code> of <code>String</code>s
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    public boolean inOrder(Comparator<String> comp) {
    	return myRight.greaterThan(myString, comp) &&
    			myLeft.lessThan(myString, comp) &&
    			myRight.inOrder(comp) &&
    			myLeft.inOrder(comp);
    }
    
    /**EFFECT
     * Determines whether every node in this tree is greater than
     * the given <code>String</code>, as compared by the given 
     * <code>Comparator</code> of <code>String</code>s
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected boolean greaterThan(String s, Comparator<String> comp) {
    	return comp.compare(myString, s) > 0 &&
    			myLeft.greaterThan(s, comp) &&
    			myRight.greaterThan(s, comp);
    }
    
    /**EFFECT
     * Determines whether every node in this tree is less than
     * the given <code>String</code>, as compared by the given 
     * <code>Comparator</code> of <code>String</code>s
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected boolean lessThan(String s, Comparator<String> comp) {
    	return comp.compare(myString, s) < 0 &&
    			myLeft.lessThan(s, comp) &&
    			myRight.lessThan(s, comp);
    }
    
    /**EFFECT
     * Prints to the console various statistics about the given 
     * tree which may be useful for debugging
     */
    public void analytics() {
    	System.out.println("\nThe tree has " + count() + " members\n" +
    				"There is a minimum of " + minBlackCount() + 
    				" and a maximum of " + maxBlackCount() + " black nodes" +
    				"\nThe tree has a height of " + height() +
    				"\nThe representation is ok? " + repOK());
    }
    
}
