package rbtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import tester.*;

/**
 * White Box tests for the red black tree implementation
 * @author jamcnam
 * @version 2013-11-1
 */
public class ExamplesRBTree {
    /** Empty Tree */
    BT lexTree = EmptyTree.getInstance();
    /** Empty Tree */
    BT lenTree = EmptyTree.getInstance();
    /** Array List */
    ArrayList<String> arList = new ArrayList<String>(Arrays.asList(
            "anastrophe", "antilitter", "browser", "athanasius", 
            "appraisals", "colpitis", "clydebank", 
            "conceptualisations", "annually", "bersagliere", 
            "apache", "cade", "chevet", "asynapsis", "augustly", "coke",
            "astonished", "conductometric", "crony",
            "attractivity", "altruistically", "airtight",
            "clinched", "bakery", "chromatograph", "acetification"));
    /** Lexicographic Comparator */
    Comparator<String> lexComp = new StringByLex();
    /** Lexicographic Comparator */
    Comparator<String> lenComp = new StringByLength();

    /**MODIFIES:
     * builds LexTree into a lexicographically sorted RBTree
     */
    void initLex() {
        for (String s : arList) {
            lexTree = lexTree.add(s, lexComp);
        }
    }

    /**MODIFIES:
     * builds LexTree into a RBTree sorted by string length
     */
    void initLen() {
        for (String s: arList) {
            lenTree = lenTree.add(s, lenComp);
        }
    }

    /**MODIFIES:
     * LexTree and LenTree into Empty Trees
     */
    void reset() {
        lexTree = EmptyTree.getInstance();
        lenTree = EmptyTree.getInstance();
    }

    /**EFFECT:
     * tests the equality method of the red black tree
     * @param t <code>Tester</code>
     */
    void testEquals(Tester t) {
        reset();
        t.checkExpect(lexTree.equals(lenTree), true);
        t.checkExpect(lexTree.equals(false), false);
        BT b = lenTree;
        t.checkExpect(b.equals(lenTree), true);
        t.checkExpect(b.equals(false), false);
        initLex();
        for (String s: arList) {
            b = b.add(s, lexComp);
        }
        t.checkExpect(b.equals(lexTree), true);
        t.checkExpect(b.equals(false), false);
    }

    /**EFFECT:
     * tests the isEmpty method of the red black tree
     * to see if a tree is an EmptyTree
     * @param t <code>Tester</code>
     */
    void testIsEmpty(Tester t) {
        reset();
        t.checkExpect(lexTree.isEmpty(), true);
        initLex();
        t.checkExpect(lexTree.isEmpty(), false);
    }

    /**EFFECT:
     * tests the Insert method of the red black tree
     * to ensure that strings are inserted into the tree
     * and that they are put in the right place
     * @param t <code>Tester</code>
     */
    void testInsert(Tester t) {
        reset();
        t.checkExpect(lexTree.isEmpty(), true);
        lexTree = lexTree.insert("hello", lexComp);
        lexTree = lexTree.insert("salut", lexComp);
        lexTree = lexTree.insert("aloha", lexComp);
        t.checkExpect(lexTree.isEmpty(), false);
        t.checkExpect(lexTree.contains("hello", lexComp), true);
        t.checkExpect(lexTree.contains("hi", lexComp), false);
        t.checkExpect(lexTree.getLeft().contains("aloha", lexComp), true);
        t.checkExpect(lexTree.getRight().contains("salut", lexComp), true);
        t.checkExpect(lexTree.getLeft().contains("salut", lexComp), false);
        //insert does not ensure that the root node 
        t.checkExpect(lexTree.red, true);

        //ensure that the insert method uses the given comparator
        //to build the tree
        reset();
        t.checkExpect(lenTree.isEmpty(), true);
        lenTree = lenTree.insert("hello", lenComp);
        lenTree = lenTree.insert("gutentag", lenComp);
        lenTree = lenTree.insert("yo", lenComp);
        t.checkExpect(lenTree.isEmpty(), false);
        t.checkExpect(lenTree.contains("hello", lenComp), true);
        t.checkExpect(lenTree.contains("hi", lenComp), true);
        t.checkExpect(lenTree.getLeft().contains("yo", lenComp), true);
        t.checkExpect(lenTree.getRight().contains("gutentag", lenComp), true);
        t.checkExpect(lenTree.getLeft().contains("hello", lenComp), false);
        t.checkExpect(lenTree.red, true);
    }
    /**EFFECT:
     * tests the Add method of the red black tree
     * to ensure the tree is built correctly
     * @param t <code>Tester</code>
     */
    void testAdd(Tester t) {
        reset();
        lexTree = lexTree.add("hello", lexComp);
        lexTree = lexTree.add("salut", lexComp);
        lexTree = lexTree.add("aloha", lexComp);
        t.checkExpect(lexTree.isEmpty(), false);
        t.checkExpect(lexTree.contains("hello", lexComp), true);
        t.checkExpect(lexTree.getLeft().contains("aloha", lexComp), true);
        //Add should ensure that the root is black
        t.checkExpect(lexTree.red, false);

        reset();
        lenTree = lenTree.add("hello", lenComp);
        lenTree = lenTree.add("gutentag", lenComp);
        lenTree = lenTree.add("yo", lenComp);
        t.checkExpect(lenTree.contains("hello", lenComp), true);
        t.checkExpect(lenTree.red, false);
    }

    /**EFFECT:
     * tests the contains method of the red black tree
     * to see if a tree can correctly identify if a String
     * is part of its member set
     * @param t <code>Tester</code>
     */
    void testContains(Tester t) {
        reset();
        t.checkExpect(lexTree.isEmpty(), true);
        t.checkExpect(lexTree.contains("hello", lexComp), false);
        t.checkExpect(lexTree.contains("salut", lexComp), false);
        t.checkExpect(lexTree.contains("aloha", lexComp), false);
        lexTree = lexTree.add("hello", lexComp);
        lexTree = lexTree.add("salut", lexComp);
        lexTree = lexTree.add("aloha", lexComp);
        t.checkExpect(lexTree.contains("hello", lexComp), true);
        t.checkExpect(lexTree.contains("hi", lexComp), false);
        //test that the strings are being put in the right spot
        t.checkExpect(lexTree.getLeft().contains("aloha", lexComp), true);
        t.checkExpect(lexTree.getRight().contains("salut", lexComp), true);
        t.checkExpect(lexTree.getLeft().contains("salut", lexComp), false);

        reset();
        lenTree = lenTree.add("hello", lenComp);
        lenTree = lenTree.add("gutentag", lenComp);
        lenTree = lenTree.add("yo", lenComp);
        t.checkExpect(lenTree.contains("hello", lenComp), true);
        //Length comparator only cares about length and not content
        t.checkExpect(lenTree.contains("ttttt", lenComp), true);
        t.checkExpect(lenTree.contains("tttttt", lenComp), false);
    }

    /**EFFECT:
     * tests the count method of the red black tree
     * to see that it correctly understands the number
     * of strings in the tree
     * @param t <code>Tester</code>
     */
    void testCount(Tester t) {
        reset();
        t.checkExpect(lexTree.count(), 0);
        initLex();
        t.checkExpect(lexTree.count(), 26);
        t.checkExpect(lexTree.getLeft().count(), 10);
        t.checkExpect(lexTree.getRight().count(), 15);
        t.checkExpect(lexTree.getRight().getLeft().count(), 5);
        t.checkExpect(lexTree.getRight().getRight().count(), 9);
    }

    /**EFFECT:
     * tests the black count method of the red black tree
     * to see that it correctly understands the number
     * of black nodes in each path of the node
     * @param t <code>Tester</code>
     */
    void testBlackCount(Tester t) {
        reset();
        t.checkExpect(lexTree.minBlackCount(), 0);
        t.checkExpect(lexTree.maxBlackCount(), 0);
        initLex();
        t.checkExpect(lexTree.minBlackCount(), 3);
        t.checkExpect(lexTree.maxBlackCount(), 3);
        initLex();
        t.checkExpect(lexTree.minBlackCount(), 3);
        t.checkExpect(lexTree.maxBlackCount(), 3);
    }

    /**EFFECT:
     * tests the isRedAndHasRedChild method of the red black tree
     * to see that it correctly recognizes if a tree violates
     * the red-red invariant
     * @param t <code>Tester</code>
     */
    void testIsRedAndHasRedChild(Tester t) {
        reset();
        t.checkExpect(lexTree.isRedAndHasRedChild(), false);
        initLex();

        //nowhere in the tree should there be a red-red violation
        while (lexTree.getRest() != EmptyTree.getInstance()) {
            lexTree = lexTree.getRest();
            t.checkExpect(lexTree.isRedAndHasRedChild(), false);
        }

        reset();
        initLex();
        t.checkExpect(lexTree.invertColor().isRedAndHasRedChild(), true);

    }
    /**EFFECT:
     * tests the height method of the red black tree
     * to see that it correctly understands the number
     * of levels in the tree
     * the number should be roughly on the order of 
     * (3/2) lg n 
     * @param t <code>Tester</code>
     */
    void testHeight(Tester t) {
        reset();
        t.checkExpect(lexTree.height(), 0);
        initLex();
        t.checkExpect(lexTree.count(), 26);
        t.checkExpect(lexTree.height(), 6);

        t.checkExpect(lenTree.height(), 0);
        initLen();
        t.checkExpect(lenTree.count(), 12);
        t.checkExpect(lenTree.height(), 5);
    }

    /**EFFECT:
     * tests the accessor methods of the red black tree
     * to see that it correctly understands the number
     * of strings in the tree
     * @param t <code>Tester</code>
     */
    void testAccessors(Tester t) {
        reset();
        t.checkException(new RuntimeException("Empty Tree has no left"),
                lexTree, "getLeft");
        t.checkException(new RuntimeException("Empty Tree has no right"),
                lexTree, "getRight");
        t.checkException(new RuntimeException("Empty Tree has no String"),
                lexTree, "getString");
        initLex();
        t.checkExpect(lexTree.getString(), "athanasius");
        t.checkExpect(lexTree.getLeft().getString(), "antilitter");
        t.checkExpect(lexTree.getRight().getString(), "cade");
    }

    /**EFFECT:
     * tests the inOrder method of the red black tree
     * to see that every string in the tree is greater
     * than every string to the left, and less than 
     * ever string to the right
     * @param t <code>Tester</code>
     */
    void testInOrder(Tester t) {
        reset();
        t.checkExpect(lexTree.inOrder(lexComp), true);
        t.checkExpect(lexTree.inOrder(lenComp), true);

        initLex();
        t.checkExpect(lexTree.inOrder(lexComp), true);

        //check to make sure that inOrder uses the comparator it's given 
        t.checkExpect(lexTree.inOrder(lenComp), false);
        t.checkExpect(lexTree.getLeft().lessThan(lexTree.getString(), 
                lexComp), true);
        t.checkExpect(lexTree.getRight().greaterThan(lexTree.getString(),
                lexComp), true);
        t.checkExpect(lexTree.getLeft().lessThan(lexTree.getString(), 
                lenComp), false);
        t.checkExpect(lexTree.getRight().greaterThan(lexTree.getString(),
                lenComp), false);
    }

    /**EFFECT:
     * tests the getFirst method of the red black tree
     * to see that it correctly returns the smallest value
     * of all strings in the tree
     * @param t <code>Tester</code>
     */
    void testGetFirst(Tester t) {
        reset();
        t.checkException(
                new RuntimeException("EmptyTree has no string"), 
                lexTree, "getFirst");
        initLex();
        initLen();
        t.checkExpect(lexTree.getFirst(), "acetification");
        t.checkExpect(lenTree.getFirst(), "cade");
    }

    /**EFFECT:
     * tests the getRest method of the red black tree
     * to see that it returns every value of the rbtree
     * save the for the first  
     * @param t <code>Tester</code>
     */
    void testGetRest(Tester t) {
        reset();
        t.checkExpect(lexTree.getRest(), EmptyTree.getInstance());
        initLex();
        String s = lexTree.getFirst();

        //ensure that every first string in every get rest
        //is always greater than the string that was removed
        while (!lexTree.getRest().isEmpty()) {
            t.checkExpect(lexComp.compare(s, 
                    lexTree.getRest().getFirst()) < 0);

            lexTree = lexTree.getRest();
            s = lexTree.getFirst();
        }
    }

    /**EFFECT:
     * tests the invertColor method of the red black tree
     * to see that it correctly flips the color
     * of the tree
     * @param t <code>Tester</code>
     */
    void testInvertColor(Tester t) {
        reset();
        t.checkException(new RuntimeException("All EmptyTrees are black"), 
                lexTree, "invertColor");
        initLex();
        t.checkExpect(lexTree.red, false);
        lexTree = lexTree.invertColor();
        t.checkExpect(lexTree.red, true);
        t.checkExpect(lexTree.isRedAndHasRedChild(), true);
    }

    /**EFFECT:
     * tests the makeBlack method of the red black tree
     * to see that it correctly sets the value to black
     * @param t <code>Tester</code>
     */
    void testMakeBlack(Tester t) {
        reset();
        t.checkExpect(lexTree.makeBlack(), lexTree);
        initLex();
        lexTree = lexTree.invertColor();
        t.checkExpect(lexTree.red, true);
        lexTree = lexTree.makeBlack();
        t.checkExpect(lexTree.red, false);
        lexTree = lexTree.makeBlack();
        t.checkExpect(lexTree.red, false);
    }

    /**EFFECT:
     * tests the balance method of the red black tree
     * to see that it correctly balances the tree
     * @param t <code>Tester</code>
     */
    void testBalance(Tester t) {
        reset();
        t.checkExpect(lexTree.balance(), EmptyTree.getInstance());
        initLex();
        t.checkExpect(lexTree.balance().equals(lexTree), true);
        lexTree = new ConsTree(lexTree.getString(), 
                lexTree.getLeft().invertColor(),
                lexTree.getRight(), lexTree.red);
        t.checkExpect(lexTree.balance().equals(lexTree), false);

        initLex();
        t.checkExpect(lexTree.balance().equals(lexTree), true);
        lexTree = new ConsTree(lexTree.getString(), 
                lexTree.getLeft(),
                lexTree.getRight().invertColor(), lexTree.red);
        t.checkExpect(lexTree.balance().equals(lexTree), false);
    }

    /**EFFECT:
     * tests the repOK method of the red black tree
     * to see that it correctly identifies valid RBTrees
     * @param t <code>Tester</code>
     */
    void testRepOK(Tester t) {
        reset();
        t.checkExpect(lexTree.repOK(), true);
        initLex();
        t.checkExpect(lexTree.repOK(), true);
        lexTree = new ConsTree(lexTree.getString(), 
                lexTree.getLeft().invertColor(),
                lexTree.getRight(), lexTree.red);
        t.checkExpect(lexTree.repOK(), false);

        reset();
        initLex();
        t.checkExpect(lexTree.repOK(), true);
        lexTree = new ConsTree(lexTree.getString(), 
                lexTree.getLeft(),
                lexTree.getRight().invertColor(), true);
        t.checkExpect(lexTree.repOK(), false);

        reset();
        initLex();
        lexTree = new ConsTree(lexTree.getString(), 
                lexTree.getLeft(),
                lexTree.getRight().makeBlack(), false);

        t.checkExpect(lexTree.repOK(), false);
    }

}