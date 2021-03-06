import java.util.*;

import rbtree.BT;
import rbtree.EmptyTree;

/**
 * 1. James McNamara
 * 2. jamcnam@ccs.neu.edu
 * 3. 
 */


/**
 * Concrete iterable class which sorts and iterates over binary tree's
 * @author jamcnam
 * @version 2013-10-08
 */
public class BTree implements Iterable<String> {

    /**Comparator for this BTree*/
    private Comparator<String> myCompare;

    /**Counter for the number of iterators currently running*/
    private int active;

    /**Red-Black Tree that maintains the strings in this BTree */
    private BT tree;

    /**Determines whether or not to run certain time consuming checks to
     * ensure representation invariants */
    private final boolean DEBUG = false;

    /**
     * Constructor
     * @param comp <code>Comparator</code> for <code>String</code>s
     */
    private BTree(Comparator<String> comp) {
        myCompare = comp;
        active = 0;
        tree = EmptyTree.getInstance();
        tree.setComparator(myCompare);
    }

    /**EFFECT
     * Determines whether or not this BTree is a valid representation
     * of a BTree
     * Note: very expensive computation, should only be run in debug mode 
     * @return <code>boolean</code>
     */
    public boolean repOK() {
        return (this.myCompare instanceof Comparator) &&
                (this.tree instanceof BT) &&
                (this.tree.repOK()) &&
                (this.tree.inOrder(myCompare));
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
        for (String s : iter) {
            tree = tree.add(s, myCompare);
        }
        if (DEBUG) {
            if (!repOK()) {
                throw new RuntimeException("Invalid representation" + 
                        " of a BTree");
            }
            else {
                tree.analytics();
            }
        }
    }

    /**
     * Modifies: 
     * this binary search tree by inserting the 
     * first numStrings <code>String</code>s from 
     * the given <code>Iterable</code> collection
     * The tree will not have any duplicates 
     * - if an item to be added equals an item
     * that is already in the tree, it will not be added.
     *
     * @param iter the given <code>Iterable</code> collection
     * @param numStrings number of <code>String</code>s
     *        to iterate through and add to BTree
     *        If numStrings is negative or larger than the number of 
     *        <code>String</code>s in iter then all <code>String</code>s 
     *        in iter should be inserted into the tree 
     */
    public void build(Iterable<String> iter, int numStrings) {
        if (active > 0) {
            throw new ConcurrentModificationException();
        }
        if (numStrings < 0) {
            build(iter);
            return;
        }

        Iterator<String> iterator = iter.iterator();
        int i = 0;
        while (iterator.hasNext() && i < numStrings) {
            tree = tree.add(iterator.next(), myCompare);
            i = i + 1;
        }

        //if in debug mode, test that the representation is still valid
        //if not, throw runtime exception
        //else print data on the tree
        if (DEBUG) {
            if (!repOK()) {
                throw new RuntimeException("Invalid representation" +
                        " of a BTree");
            }
            else {
                tree.analytics();
            }
        }
    }

    /**
     * Effect:
     * Checks to see if this BTree contains s
     * @param s <code>String</code> to look for in this
     * @return whether this contains s
     */
    public boolean contains(String s) {
        return this.tree.contains(s, myCompare);
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
     * @return String
     */

    public String toString() {
        return tree.toString();
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
        if (o instanceof BTree) {
            return this.equals((BTree) o);
        }
        else {
            return false;
        }
    }

    /**EFFECT:
     * Determines if this BTree is Equal to the given BTree
     * @param bt <code>BTree</code>
     * @return <code>boolean</code>
     */
    public boolean equals(BTree bt) {
        return myCompare.getClass().equals(bt.myCompare.getClass())
                && tree.equals(bt.tree);
    }

    /**
     * EFFECT: 
     * Produces an integer that is compatible 
     * with the implemented  equals method 
     * and is likely to be different 
     * for objects that are not equal.
     * @return <code>int</code>
     */
    public int hashCode() {
        return this.tree.hashCode() + this.myCompare.hashCode();
    }

    /**EFFECT:
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

        private BT treeToIter;

        private boolean incremented;
        /**
         * Constructor
         */
        private BTreeGen() {
            incremented = false;
            treeToIter = tree;
        }

        /**
         * Determines whether this iterator has any more items left to return
         * @return <code>boolean</code> 
         */
        public boolean hasNext() {
            return !treeToIter.getRest().isEmpty();
        }

        /**
         * Returns the next String in the iterator 
         * @return <code>String</code>
         */
        public String next() {
            if (hasNext()) {
                //Before you begin iterating, ensure you've incremented
                //the active iterator count
                incActive();

                String s = treeToIter.getFirst();
                treeToIter = treeToIter.getRest();
                //If we are now at the end of the iterator, ensure that
                //the number of active iterators is decremented 
                if (!hasNext()) {
                    active = active - 1;
                }

                return s;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        /** EFFECT:
         * When this iterator's next method is first successfully invoked,
         * increment the number of active iterators running on this 
         * BTree and mark that this Iterator has incremented
         */
        private void incActive() {
            if (!incremented) {
                incremented = true;
                active = active + 1;
            }
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


