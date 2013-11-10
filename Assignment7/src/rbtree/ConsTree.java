package rbtree;


import java.util.Comparator;



/**
 * Concrete implementation of a Red-Black BST with Values 
 * @author jamcnam
 * @version 2013-10-08
 */
public class ConsTree<T> extends RBTree<T> {
    /**
     * Left Binary Tree
     */
    RBTree<T> myLeft;

    /**
     * Right Binary Tree
     */
    RBTree<T> myRight;

    /**
     * Current Node's value 
     */
    T myValue;
    
    int hashCode;

    /**
     * Constructor
     * @param left <code>RBTree</code>
     * @param right <code>RBTree</code>
     * @param value <code>String</code>
     * @param color <code>boolean</code>
     * 
     * Representation Invariant
     * this.myLeft is of type RBTree<T> &&
     * this.myRight is of type RBTree<T> &&
     * this.myValue.getClass() == "String" &&
     * this.myCompare.compare(myValue, s) > 0 for all myValues s in myLeft &&
     * this.myCompare.compare(myValue, s) < 0 for all myValues s in myRight &&
     * this.red is of type boolean &&
     * if (this.red) -> !this.myLeft.red &&
     * (this.red) -> !this.myRight.red &&
     * Every route from root to node has the same count of black nodes 
     * 
     */
    ConsTree(T value, Comparator<T> comp,  RBTree<T> left, RBTree<T> right, 
            boolean color) {
        myValue = value;
        myCompare = comp;
        myLeft = left;
        myRight = right;
        red = color;
        hashCode = myLeft.hashCode() * 10 + myValue.hashCode() 
                + myRight.hashCode() * 10;
    }

    /**EFFECT
     * Boolean method that can be used in testing to verify that this object
     * is a legal representation
     * @return boolean
     */
    public boolean repOK() {
        return (myLeft instanceof RBTree) &&
                (myRight instanceof RBTree) &&
                (red || !red) &&
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
        s = s + myValue.toString();

        //if the right tree is a ConsTree, add its values to the String
        if (!myRight.isEmpty()) {
            s = s + ", " + myRight.toString();
        }

        return s;
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
        if (o instanceof ConsTree) {
            return this.equals((ConsTree<?>) o);
        }
        else {
            return false;
        }
    }

    /**EFFECT
     * Determines if this ConsTree is equal to the given ConsTree
     * @param bt <code>RBTree</code>
     * @return <code>boolean</code>
     */
    public boolean equals(ConsTree<?> bt) {
        return bt.myValue.equals(myValue)
                && bt.myCompare.equals(myCompare)
                && bt.myLeft.equals(myLeft) 
                && bt.myRight.equals(myRight);
    }

    /**EFFECT
     * Returns an integer representation of this ConsTree
     * @return <code>int</code>
     */
    public int hashCode() {
        return hashCode;
    }

    /**EFFECT
     * Returns a RBTree<T> that represents this tree plus the given String
     * sorted by this RBTree's Comparator, with the root node (this node)
     * cast as black
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public RBTree<T> add(T t) {
        return insert(t).makeBlack();
    }

    /**EFFECT
     * Returns a RBTree<T> that represents this tree plus the given String
     * sorted by this RBTree's Comparator, that is balanced via
     * to the red-black algorithm
     * @param s <code>String</code>
     * @param myCompare <code>Comparator</code> of <code>String</code>s
     * @return <code>RBTree</code>
     */
    public RBTree<T> insert(T t) {
        int compare = myCompare.compare(t, myValue);

        //if the given string is less than this string,
        //put it into the left subtree and rebalance
        if (compare < 0) {
            return new ConsTree<T>(myValue, myCompare, myLeft.insert(t), 
                    myRight, red).balance();
        }
        //if the given string is greater than this string,
        //put it into the right subtree and rebalance
        else if (compare > 0) {
            return new ConsTree<T>(myValue, myCompare, myLeft, 
                    myRight.insert(t), red).balance(); 
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
    public boolean contains(T t) {
        int comp = myCompare.compare(myValue, t);
        //if the comparison is negative, check the right subtree
        if (comp < 0) {
            return this.myRight.contains(t);
        }
        //if the comparison is positive, check the left subtree
        else if (comp > 0) {
            return this.myLeft.contains(t);
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
     * @return <code>RBTree</code>
     */
    protected RBTree<T> balance() {
        if (!red && myLeft.isRedAndHasRedChild()) {

            //if the left and the left's left are red,
            //the left tree comes up and everything 
            //else falls into its relative place
            if (this.myLeft.getLeft().red) {
                RBTree<T> newLeft = myLeft.getLeft().invertColor();
                RBTree<T> newRight = new ConsTree<T>(myValue, myCompare, myLeft.getRight(),
                        myRight, false);
                return new ConsTree<T>(myLeft.getValue(), myCompare, newLeft, 
                        newRight, true);
            }
            //if the left and the left's right are red,
            //the left's right comes up and everything 
            //else falls into its relative place
            else {
                RBTree<T> newLeft = new ConsTree<T>(myLeft.getValue(), 
                        myCompare,
                        myLeft.getLeft(), 
                        myLeft.getRight().getLeft(),
                        false);
                RBTree<T> newRight = new ConsTree<T>(myValue,
                        myCompare,
                        myLeft.getRight().getRight(),
                        myRight,
                        false);
                return new ConsTree<T>(myLeft.getRight().getValue(),
                        myCompare,
                        newLeft,
                        newRight,
                        true);
            }
        }
        else if (!red && myRight.isRedAndHasRedChild()) {
            //if the right and the rights's left are red,
            //the right's left comes up and everything 
            //else falls into its relative place
            if (this.myRight.getLeft().red) {
                RBTree<T> newLeft = new ConsTree<T>(myValue, myCompare, myLeft,
                        myRight.getLeft().getLeft(),
                        false);
                RBTree<T> newRight = new ConsTree<T>(myRight.getValue(),
                        myCompare,
                        myRight.getLeft().getRight(),
                        myRight.getRight(),
                        false);
                return new ConsTree<T>(myRight.getLeft().getValue(),
                        myCompare,
                        newLeft,
                        newRight,
                        true);
            }
            //if the right and the right's right are red,
            //the right tree comes up and everything 
            //else falls into its relative place
            else {
                RBTree<T> newLeft = new ConsTree<T>(myValue,
                        myCompare,
                        myLeft,
                        myRight.getLeft(),
                        false);
                RBTree<T> newRight = myRight.getRight().invertColor();
                return new ConsTree<T>(myRight.getValue(),
                        myCompare,
                        newLeft,
                        newRight,
                        true);
            }
        }
        //else the local area is balanced
        else {
            return this;
        }
    }

    /**EFFECT
     * Returns the string that is furthest to the left in this tree
     * @return <code>String</code> 
     */
    public T getFirst() {
        if (myLeft.isEmpty()) {
            return myValue;
        }
        else {
            return myLeft.getFirst();
        }
    }

    /**EFFECT
     * Returns a tree that mimics this RBTree, except that it lacks the 
     * furthest left String
     * @return <code>RBTree</code> 
     */
    public RBTree<T> getRest() {
        if (myLeft.isEmpty()) {
            if (myRight.isEmpty()) {
                return new EmptyTree<T>(myCompare);
            }
            else {
                return myRight;
            }
        }
        else {
            //If my left is not empty, return all elements in this tree,
            // along with the getRest of the left subtree
            return new ConsTree<T>(myValue, myCompare, myLeft.getRest(),
                    myRight, red);
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
     * @return <code>RBTree</code> 
     */
    protected RBTree<T> getLeft() {
        return this.myLeft;
    }

    /**PRODUCES
     * This tree's right tree
     * @return <code>RBTree</code> 
     */
    protected RBTree<T> getRight() {
        return this.myRight;
    }

    /**PRODUCES
     * This tree's right tree
     * @return <code>String</code> 
     */
    protected T getValue() {
        return this.myValue;
    }

    /**PRODUCES
     * This tree, with its color flipped
     * @return <code>RBTree</code> 
     */
    protected RBTree<T> invertColor() {
        return new ConsTree<T>(myValue, myCompare, myLeft, myRight, !red);
    }

    /**PRODUCES
     * This tree, but if it's color had previously been red,
     * it will now be black
     * @return <code>RBTree</code> 
     */
    public RBTree<T> makeBlack() {
        if (red) {
            return this.invertColor();
        }
        else {
            return this;
        }
    }

    /**EFFECT
     * Returns the number of strings in this tree
     * @return <code>int</code>
     */
    public int count() {
        return 1 + myLeft.count() + myRight.count();
    }

    /**EFFECT
     * Returns the number of levels in this tree
     * For a tree of count n, this should be 
     * on the order of lg n
     * @return <code>int</code>
     */
    public int height() {
        return 1 + Math.max(myLeft.height(), myRight.height());
    }

    /**EFFECT
     * Finds the path in this tree from root to node which has the most
     * black nodes
     * Should be equivalent to the path which has the least black nodes
     * @return <code>int</code>
     */
    public int maxBlackCount() {
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
    public int minBlackCount() {
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
    public boolean inOrder() {
        return myRight.greaterThan(myValue) &&
                myLeft.lessThan(myValue) &&
                myRight.inOrder() &&
                myLeft.inOrder();
    }

    /**EFFECT
     * Determines whether every node in this tree is greater than
     * the given <code>String</code>, as compared by the given 
     * <code>Comparator</code> of <code>String</code>s
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected boolean greaterThan(T t) {
        return myCompare.compare(myValue, t) > 0 &&
                myLeft.greaterThan(t) &&
                myRight.greaterThan(t);
    }

    /**EFFECT
     * Determines whether every node in this tree is less than
     * the given <code>String</code>, as compared by the given 
     * <code>Comparator</code> of <code>String</code>s
     * @param s <code>String</code>
     * @param comp <code>Comparator</code> of <code>String</code>s
     * @return <code>boolean</code>
     */
    protected boolean lessThan(T t) {
        return myCompare.compare(myValue, t) < 0 &&
                myLeft.lessThan(t) &&
                myRight.lessThan(t);
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
    
    public <R> R accept(RBTreeVisitor<T, R> visitor) {
        return visitor.visitNode(myCompare, (red ? "RED" : "BLACK"),
                myValue, myLeft, myRight);
    }
    

}
