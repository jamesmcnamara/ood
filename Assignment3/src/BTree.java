import java.util.*;
import org.junit.Assert.*;

/**
 * 1. James McNamara
 * 2. jamcnam@ccs.neu.edu
 * 3. Burlington, VT is the smallest largest city in any state. 
 */


/**
 * Concrete iterable class which sorts and iterates over binary tree's
 * @author jamcnam
 * @version 2013-10-08
 */
public class BTree implements Iterable<String> {

    /**Comparator for this BTree*/
    Comparator<String> myCompare;

    /**Counter for the number of iterators currently running*/
    int active;


    private BT tree;

    /**
     * Constructor
     * @param comp <code>Comparator</code> for <code>String</code>s
     */
    BTree (Comparator<String> comp) {
        myCompare = comp;
        active = 0;
        tree = new EmptyTree(myCompare);
    }

    /**
     * Factory method to generate 
     * an empty binary search tree
     * with the given <code>Comparator</code>
     *
     * @param comp the given <code>Comparator</code>
     * @return new empty binary search tree that uses the 
     *         given <code>Comparator</code> for ordering
     */
    public static BTree binTree(Comparator<String> comp) {
        return new BTree(comp);
    }

    /**
     * Modifies: 
     * this binary search tree by inserting the 
     * <code>String</code>s from the given 
     * <code>Iterable</code> collection
     * The tree will not have any duplicates 
     * - if an item to be added equals an item
     * that is already in the tree, it will not be added.
     *
     * @param iter the given <code>Iterable</code> collection
     */
    public void build(Iterable<String> iter) {
        if (active > 0) {
            throw new ConcurrentModificationException();
        }
        Iterator<String> iterator = iter.iterator();
        while(iterator.hasNext()) {
            tree = tree.add(iterator.next());
        }
    }

    /**
     * Effect: 
     * Produces a <code>String</code> that consists of 
     * all <code>String</code>s in this tree 
     * separated by comma and a space, 
     * generated in the order defined by this tree's 
     * <code>Comparator</code>.
     * So for a tree with <code>Strings</code> 
     * "hello" "bye" and "aloha" 
     * ordered lexicographically, 
     * the result would be "aloha, bye, hello"
     *
     */

    public String toString() {
        Iterator<String> iter = this.iterator();
        String s = "";
        while (iter.hasNext()) {
            s = s + ", " + iter.next();
        }
        return s.substring(2, s.length());
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
        if (o == null) {
            return false;
        }
        else if (o instanceof BTree) {
            return this.equals((BTree) o);
        }
        else {
            return false;
        }
    }

    /**
     * Equals method for BTrees
     * @param bt <code>BTree</code>
     * @return <code>boolean</code>
     */
    public boolean equals(BTree bt) {
        return myCompare.getClass().equals(bt.myCompare.getClass())
                && tree.equals(bt.tree);
    }

    /**
     * Effect: 
     * Produces an integer that is compatible 
     * with the implemented  equals method 
     * and is likely to be different 
     * for objects that are not equal.
     * @return <code>int</code>
     */
    public int hashCode() {
        return this.tree.hashCode();
    }

    /**
     * Iterator method for the BTree class
     * @return <code>Iterator</code> over <code>String</code>s 
     */
    public Iterator<String> iterator() {
        return new BTreeGen();
    }
    /**
     * Private inner class creates an iterator for this BTree 
     * @author jamcnam
     * @version 2013-10-07
     */
    private class BTreeGen implements Iterator<String> {

        private ArrayList<String> arList;

        /**
         * Constructor
         */
        BTreeGen(){
            active = active + 1;
            arList = new ArrayList<String>();
            arList = tree.listRep(arList);
        }

        /**
         * Determines whether this iterator has any more items left to return
         * @return <code>boolean</code> 
         */
        public boolean hasNext() {
            return arList.size() > 0;
        }

        /**
         * Returns the next String in the iterator as determined by the 
         * <code>Comparator</code> and increments the iterator
         * @return <code>String</code>
         */
        public String next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            String min = arList.get(0);
            int index = 0;
            for (int i = 1; i < arList.size(); i++) {
                if (myCompare.compare(arList.get(i), min) < 0) {
                    min = arList.get(i);
                    index = i;
                }
            }
            arList.remove(index);
            if (!this.hasNext()) {
                active = active - 1;
            }
            return min;
        }

        /**
         * Throws an Unsupported Operation Exception
         * remove not supported by this iterator
         */
        public void remove() {
            throw new UnsupportedOperationException("Remove is not " + 
                    "supported by this iterator.");
        }
    }

}

/**
 * BT is an abstract Binary Tree representation
 * @author jamcnam
 * @version 2013-10-08
 */
abstract class BT {

    /**
     * This binary tree's comparator
     */
    Comparator<String> myCompare;

    /**
     * Abstract function to add the given String to this BT
     * @param s <code>String</code>
     * @return <code>BT</code>
     */
    public abstract BT add(String s);

    /**
     * Returns an <code>ArrayList</code> representation of this BT 
     * @param arList <code>ArrayList</code> of <code>Strings</code>
     * @return <code>ArrayList</code> of <code>Strings</code>
     */
    abstract ArrayList<String> listRep(ArrayList<String> arList);

    /**
     * Determines whether this BT is an Empty Tree
     * @return <code>boolean</code>
     */
    abstract boolean isEmpty();
    
}

/**
 * Concrete implementation of an Empty BT
 * @author jamcnam
 * @version 2013-10-08
 */
class EmptyTree extends BT {
    
    /**
     * Constructor
     * @param comp
     */
    EmptyTree(Comparator<String> comp) {
        this.myCompare = comp;
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
     * Determines if this <code>EmptyTree</code> is equal to the given 
     * BTree
     * @param bt <code>BT</code>
     * @return <code>boolean</code>
     */
    public boolean equals(BT bt) {
        return bt.myCompare.getClass().equals(this.myCompare.getClass())
                && bt.isEmpty();
    }

    /**
     * Returns a hash representation of this BT 
     * @return <code>int</code>
     */
    public int hashCode() {
        return myCompare.hashCode();
    }

    /**
     * Generates an ArrayList representation of this BT
     * @return <code>ArrayList</code> of <code>Strings</code>
     */
    ArrayList<String> listRep(ArrayList<String> arList) {
        return arList;
    }

    /**
     * Returns a BT which represents this BT plus the given 
     * <code>String</code> inserted in sorted order
     * @return <code>BT</code>
     */
    public BT add(String s) {
        return new ConsTree(myCompare, new EmptyTree(myCompare), new EmptyTree(myCompare), s);
    }

}

/**
 * Concrete implementation of a BT with Values 
 * @author jamcnam
 * @version 2013-10-08
 */
class ConsTree extends BT {
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
     */
    ConsTree(Comparator<String> comp, BT left, BT right, String value) {
        this.myCompare = comp;
        this.myLeft = left;
        this.myRight = right;
        this.myString = value;
    }

    /**
     * Determines whether this Tree is empty
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return false;
    }
    /**
     * Concatenates the <code>String</code>s in the tree into one <code>String</code>
     * @return <code>String</code>
     */
    public String toString() {
        return this.myLeft.toString() + ", " + this.myString + 
                ", " + this.myRight.toString();
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
        return (myCompare.getClass().equals(bt.myCompare) 
                && myCompare.compare(myString,bt.myString) == 0
                && myLeft.equals(bt.myLeft) 
                && myRight.equals(bt.myRight));
    }
    /**
     * Return the hashCode of this ConsTree
     * @return <code>int</code>
     */
    public int hashCode() {
        return myLeft.hashCode() * 10 + myString.hashCode() + myRight.hashCode()*10;
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

    public BT add(String s) {
        int comparison = myCompare.compare(myString, s);
        if (comparison < 0) {
            return new ConsTree(myCompare, myLeft, myRight.add(s), myString);
        }
        else if (comparison > 0) {
            return new ConsTree(myCompare, myLeft.add(s), myRight, myString);
        }
        else {
            return this;
        }
    }
}


/**
 * Comparator class who's compare method determines which of the two strings 
 * is longer 
 * @author jamcnam
 * @version 2013-10-07
 */
class StringByLength implements Comparator<String> {

    /**
     * Determines the degree that s2 is greater than s1
     * if compare returns a positive value, s2 is longer than s1
     * if compare returns 0, s2 and s1 are the same length
     * if compare returns a negative value, s1 is longer than s2
     * @param s1 <code>String</code>
     * @param s2 <code>String</code>
     * @return <code>int</code>
     */
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }

    /**
     * Determines whether the given object is a
     * <code>StringByLength</code> Comparator
     * @param o <code>Object</code>
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return (o instanceof StringByLength);
    }

    public int hashCode() {
        return 1;
    }
}

/**
 * Comparator class who's compare method determines which of two strings have 
 * the first lexicographic ordering
 * @author jamcnam
 */
class StringByLex implements Comparator<String> {


    /**
     * Determines the degree that s2 is greater than s1
     * if compare returns a positive value, s2 is lexicographically greater
     * if compare returns 0, s2 and s1 are the same order
     * if compare returns a negative value, s1 is lexicographically greater
     * @param s1 <code>String</code>
     * @param s2 <code>String</code>
     * @return <code>int</code>
     */
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }

    /**
     * Determines whether the given object is a
     * <code>StringByLength</code> Comparator
     * @param o <code>Object</code>
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return (o instanceof StringByLex);
    }

    public int hashCode() {
        return 2;
    }
}

class ExampleBTree {
    Comparator<String> lex = new StringByLex();
    Comparator<String> len = new StringByLength();

    BTree bLex = BTree.binTree(lex);
    BTree bLen = BTree.binTree(len);

    ArrayList<String> scattered = new ArrayList<String>();
    ArrayList<String> lexList = new ArrayList<String> ();
    ArrayList<String> lenList = new ArrayList<String> ();

    void init() {
        scattered.add("aloha");
        scattered.add("hello");
        scattered.add("goodbye");
        scattered.add("salut");
        scattered.add("shalom");
        scattered.add("whatup");
        scattered.add("hi");
        scattered.add("gutentag");

        lexList.add("aloha");
        lexList.add("goodbye");
        lexList.add("gutentag");
        lexList.add("hello");
        lexList.add("hi");
        lexList.add("salut");
        lexList.add("shalom");
        lexList.add("whatup");

        lenList.add("hi");
        lenList.add("aloha");
        lenList.add("shalom");
        lenList.add("goodbye");
        lenList.add("gutentag");    
    }

    /*public void testBuild() {
        init();
        assertEquals("toString", bLex.toString() == "");
        bLex.build(scattered);
        assertEquals(bLex.toString(), "aloha, goodbye, " + 
        "gutentag, hello, hi, salut, shalom, whatup");
    }


    public static void main(String[] args) {
        ExampleBTree b= new ExampleBTree();
        b.init();
        b.bLex.build(b.scattered);
        System.out.println(b.bLex.toString());
    }
     */

}

