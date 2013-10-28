package rbtree;

import java.util.ArrayList;
import java.util.Comparator;



/**
 * Concrete implementation of a BT with Values 
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
     * Currenty Node's value 
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
     * this.myCompare.compare(myString, s) < 0 for all myStrings s in myRight
     * 
     */
    ConsTree(String value, BT left, BT right, boolean color) {
        this.myString = value;
        this.myLeft = left;
        this.myRight = right;
        this.red = color;
    }
    
    /**
     * Boolean method that can be used in testing to verify that this object
     * is a legal representation
     * @return boolean
     */
    public boolean repOK() {
        return this.myLeft instanceof ConsTree ||
                this.myLeft instanceof EmptyTree &&
                this.myRight instanceof ConsTree ||
                this.myRight instanceof EmptyTree &&
                this.myString instanceof String;
    }

    /**
     * Determines whether this Tree is empty
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return false;
    }
    /**
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
            s = ", " + myRight.toString();
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
    
    /**
     * Is this ConsTree equal to the given BT?
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
    /**
     * Is this ConsTree equal to that ConsTree?
     * @param bt <code>BT</code>
     * @return <code>boolean</code>
     */
    public boolean equals(ConsTree bt) {
        return bt.myString.equals(bt.myString)
                && myLeft.equals(bt.myLeft) 
                && myRight.equals(bt.myRight);
    }
    /**
     * Return the hashCode of this ConsTree
     * @return <code>int</code>
     */
    public int hashCode() {
        return myLeft.hashCode() * 10 + myString.hashCode() 
                + myRight.hashCode() * 10;
    }
    /**
     * Returns an ArrayList with the elements of the ConsTree
     * @param arList <code>ArrayList<String></code>
     * @return <code>ArrayList<String></code>
     */
    public ArrayList<String> listRep(ArrayList<String> arList) {
        arList.add(this.myString);
        arList = this.myLeft.listRep(arList);
        arList = this.myRight.listRep(arList);
        return arList;
    }

    /**
     * Returns a BT that represents this tree plus the given String
     * sorted by this BT's Comparator
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>BT</code>
     */
    public BT add(String s, Comparator<String> myCompare) {
        int compare = myCompare.compare(myString, s);
        if (compare < 0) {
            return new ConsTree(myString, myLeft, 
                    myRight.add(s, myCompare), red).balance();
        }
        else if (compare > 0) {
            return new ConsTree(myString, 
                    myLeft.add(s, myCompare), myRight, red).balance(); 
        }
        else {
            return this;
        }
    }

    /**
     * Determines whether this tree contains the given <code>String</code>
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>Boolean</code>
     */
    public boolean containsM(String s, Comparator<String> myCompare) {
        int comp = myCompare.compare(myString, s);
        if (comp > 0) {
            return this.myRight.containsM(s, myCompare);
        }
        else if (comp < 0) {
            return this.myLeft.containsM(s, myCompare);
        }
        else {
            return true;
        }
    }
    
    /**
     * Balance Method
     */
    protected BT balance() {
        if (!red && myLeft.isRedAndHasAdjacentReds()) {
            if (this.myLeft.getLeft().red) {
                BT newLeft = myLeft.getLeft().invertColor();
                BT newRight = new ConsTree(myString, myLeft.getRight(),
                        myRight, false);
                return new ConsTree(myLeft.getString(), newLeft, 
                        newRight, true);
            }
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
        else if (!red && myRight.isRedAndHasAdjacentReds()) {
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
        else return this;
        
    }
    
    protected boolean isRedAndHasAdjacentReds() {
        return this.red && (this.myLeft.red || this.myRight.red);
    }
    
    protected BT getLeft() {
        return this.myLeft;
    }
    
    protected BT getRight() {
        return this.myRight;
    }
    
    protected String getString() {
        return this.myString;
    }
    
    protected BT invertColor() {
        return new ConsTree(myString, myLeft, myRight, !red);
    }
    
    public int redTest() {
        if (this.isRedAndHasAdjacentReds()) {
            return 1 + myLeft.redTest() +
                myRight.redTest();
        }
        else {
            return myLeft.redTest() +
                    myRight.redTest();
        }
    }
    
    public int blackCount() {
        int left = myLeft.blackCount();
        int right = myRight.blackCount();
        if (left == right) {
            if (red) {
                return left;
            }
            else {
                return left + 1;
            }
        }
        else {
            throw new RuntimeException("One side has" 
        + " more blacks than the other");
        }
    }
    
    public BT makeBlack() {
        if (red) {
            return this.invertColor();
        }
        else return this;
    }
    
    public int count() {
    	return 1 + myLeft.count() + myRight.count();
    }
    
    public int height() {
    	return 1 + Math.max(myLeft.height(), myRight.height());
    }
}
