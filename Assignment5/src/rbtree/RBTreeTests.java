package rbtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import tester.*;

public class RBTreeTests {
    BT lexTree = EmptyTree.getInstance();
    BT lenTree = EmptyTree.getInstance();
    ArrayList<String> arList = new ArrayList<String>(Arrays.asList(
            "anastrophe","antilitter","browser", "athanasius", 
            "appraisals", "colpitis", "clydebank", 
            "conceptualisations","annually", "bersagliere", 
            "apache","cade", "chevet", "asynapsis", "augustly", "coke",
            "astonished", "conductometric", "crony",
            "attractivity", "altruistically","airtight",
            "clinched", "bakery", "chromatograph", "acetification"));
    Comparator<String> lexComp = new StringByLex();
    Comparator<String> lenComp = new StringByLength();

    void initLex() {
        for (String s : arList) {
            lexTree = lexTree.add(s, lexComp);
        }
    }

    void initLen() {
        for (String s: arList) {
            lenTree = lenTree.add(s, lenComp);
        }
    }
    
    void reset() {
        lexTree = EmptyTree.getInstance();
        lenTree = EmptyTree.getInstance();
    }

    void testIsEmpty(Tester t) {
        reset();
        t.checkExpect(lexTree.isEmpty(), true);
        initLex();
        t.checkExpect(lexTree.isEmpty(), false);
    }
    
    void testAdd(Tester t) {
        reset();
        t.checkExpect(lexTree.isEmpty(), true);
        lexTree = lexTree.add("hello", lexComp);
        lexTree = lexTree.add("salut", lexComp);
        lexTree = lexTree.add("aloha", lexComp);
        t.checkExpect(lexTree.isEmpty(), false);
        t.checkExpect(lexTree.contains("hello", lexComp), true);
        t.checkExpect(lexTree.contains("hi", lexComp), false);
        t.checkExpect(lexTree.getLeft().contains("aloha", lexComp), true);
        t.checkExpect(lexTree.getRight().contains("salut", lexComp), true);
        t.checkExpect(lexTree.getLeft().contains("salut", lexComp), false);
        
        reset();
        t.checkExpect(lenTree.isEmpty(), true);
        lenTree = lenTree.add("hello", lenComp);
        lenTree = lenTree.add("gutentag", lenComp);
        lenTree = lenTree.add("yo", lenComp);
        t.checkExpect(lenTree.isEmpty(), false);
        t.checkExpect(lenTree.contains("hello", lenComp), true);
        t.checkExpect(lenTree.contains("hi", lenComp), true);
        t.checkExpect(lenTree.getLeft().contains("yo", lenComp), true);
        t.checkExpect(lenTree.getRight().contains("gutentag", lenComp), true);
        t.checkExpect(lenTree.getLeft().contains("hello", lenComp), false);
    }
    
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
        t.checkExpect(lexTree.getLeft().contains("aloha", lexComp), true);
        t.checkExpect(lexTree.getRight().contains("salut", lexComp), true);
        t.checkExpect(lexTree.getLeft().contains("salut", lexComp), false);
        
        reset();
        lenTree = lenTree.add("hello", lenComp);
        lenTree = lenTree.add("gutentag", lenComp);
        lenTree = lenTree.add("yo", lenComp);
        t.checkExpect(lenTree.contains("hello", lenComp), true);
        t.checkExpect(lenTree.contains("ttttt", lenComp), true);
        t.checkExpect(lenTree.contains("tttttt", lenComp), false);
    }
    
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
    
    void testIsRedAndHasRedChild(Tester t) {
        //NOTE: this method is part of the rep invariant
        //As a condition of the flow of the program, this method
        //can never return true at a non intermediary step
        reset();
        t.checkExpect(lexTree.isRedAndHasRedChild(), false);
        t.checkExpect(lenTree.isRedAndHasRedChild(), false);
        initLex();
        initLen();
        while (lexTree.getRest() != EmptyTree.getInstance()) {
            lexTree = lexTree.getRest();
            t.checkExpect(lexTree.isRedAndHasRedChild(), false);
        }
        
    }

}