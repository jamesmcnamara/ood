import java.util.*;

import rbtree.RBTree;
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
public class BTree<T> implements Iterable<T> {

    /**Comparator for this BTree*/
    private Comparator<T> myCompare;

    /**Counter for the number of iterators currently running*/
    private int active;

    /**Red-Black Tree that maintains the strings in this BTree */
    private RBTree<T> tree;

    /**Determines whether or not to run certain time consuming checks to
     * ensure representation invariants */
    private final boolean DEBUG = false;

    /**
     * Constructor
     * @param comp <code>Comparator</code> for <code>String</code>s
     */
    private BTree(Comparator<T> comp) {
        myCompare = comp;
        active = 0;
        tree = EmptyTree.emptyFactory(myCompare);
    }

    /**EFFECT
     * Determines whether or not this BTree is a valid representation
     * of a BTree
     * Note: very expensive computation, should only be run in debug mode 
     * @return <code>boolean</code>
     */
    public boolean repOK() {
        return (this.myCompare instanceof Comparator) &&
                (this.tree instanceof RBTree) &&
                (this.tree.repOK()) &&
                (this.tree.inOrder());
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
    public static <T> BTree<T> binTree(Comparator<T> comp) {
        return new BTree<T>(comp);
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
    public void build(Iterable<T> iter) {
        if (active > 0) {
            throw new ConcurrentModificationException();
        }
        for (T t : iter) {
            tree = tree.add(t);
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
    public void build(Iterable<T> iter, int numStrings) {
        if (active > 0) {
            throw new ConcurrentModificationException();
        }
        if (numStrings < 0) {
            build(iter);
            return;
        }

        Iterator<T> iterator = iter.iterator();
        int i = 0;
        while (iterator.hasNext() && i < numStrings) {
            tree = tree.add(iterator.next());
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
    public boolean contains(T t) {
        return this.tree.contains(t);
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
            return this.equals((BTree<?>) o);
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
    public boolean equals(BTree<T> bt) {
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
    public Iterator<T> iterator() {
        return new BTreeGen();
    }
    /**
     * Private inner class creates an iterator for this BTree 
     * @author jamcnam
     * @version 2013-10-07
     */
    private class BTreeGen implements Iterator<T> {

        private RBTree<T> treeToIter;

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
        public T next() {
            if (hasNext()) {
                //Before you begin iterating, ensure you've incremented
                //the active iterator count
                incActive();

                T t= treeToIter.getFirst();
                treeToIter = treeToIter.getRest();
                //If we are now at the end of the iterator, ensure that
                //the number of active iterators is decremented 
                if (!hasNext()) {
                    active = active - 1;
                }

                return t;
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


