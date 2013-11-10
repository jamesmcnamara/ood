
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Comparator;


import rbtree.EmptyTree;
import rbtree.IntBySize;
import rbtree.RBTree;
import rbtree.RBTreeVisitor;
import rbtree.StringByLex;
import tester.*;

/**
 * Tests of the visitor classes 
 * @author james
 *
 */
public class ExamplesVisitors {
    
    /**Array Lists random sort */
    ArrayList<String> randIter  = new ArrayList<String>(Arrays.asList(
            "anastrophe", "antilitter", "browser", "athanasius", "appraisals",
            "colpitis", "clydebank", "conceptualisations", "annually", 
            "bersagliere", "apache", "cade", "chevet", "asynapsis", "augustly",
            "coke", "astonished", "conductometric", "crony", "attractivity",
            "altruistically", "airtight", "clinched", "bakery", 
            "chromatograph","compartments", "attractable", "affirmative",
            "acetification", "alkyd", "capework", "colloquoy", "concertize",
            "castrate", "astrocytoma", "causticisations", "ascites", "anode",
            "believes", "ballplayer", "bleary", "circumstanced", "cladophora", 
            "condignly", "carboy", "blared", "canzonet", "bolo", "bane", 
            "chromatographically", "askers", "conducting", "copter", 
            "boisterousness", "consumptions", "accreting", "collembolan", 
            "brimmed", "chagall", "convert", "borosilicate", "caviler", 
            "contravening", "bachelorises", "bookmakers", "chuckle", "crabby",
            "anthologises", "behest", "cheated", "communizes", "catania", 
            "condominiums", "clunky", "backrest", "capreolate", "abscissas", 
            "clambering", "amorphously", "chemicalize", "bharal", "chaperoned",
            "armer", "conversancy", "cotter", "blizzards", "buhl", "bangka", 
            "abscesses", "antibacterial", "buffalo", "burping", "basilisk", 
            "constringe", "anaglyph", "castration", "contingence", "bedraggle",
            "auras", "chromatograms", "aureole")); 
    
    /**ArrayList of integers 1-3*/
    ArrayList<Integer> oneTwoThree = 
            new ArrayList<Integer>(Arrays.asList(1, 2, 3));
    
    /**comparator for strings (lexicographic)*/
    Comparator<String> byLex = new StringByLex();
    
    /**comparator for integers*/
    Comparator<Integer> byInt = new IntBySize();
    
    /**RBTree of Strings, sorted lexicographically */
    RBTree<String> stringTree = EmptyTree.emptyFactory(byLex);
    
    /**RBTree of integers, sorted numerically */
    RBTree<Integer> intTree = EmptyTree.emptyFactory(byInt);
    
    /**PathLengths Visitor for Strings*/
    RBTreeVisitor<String, ArrayList<Integer>> stringPathVisitor = 
            new PathLengths<String>();
    
    /**CountNodes Visitor for Strings*/
    RBTreeVisitor<String, ArrayList<Integer>> stringCountVisitor = 
            new CountNodes<String>();
    
    /**Height Visitor for Strings*/
    RBTreeVisitor<String, Integer> stringHeightVisitor = 
            new Height<String>();
    
    /**BlackHeight Visitor for Strings*/
    RBTreeVisitor<String, Integer> stringBlackHeightVisitor = 
            new BlackHeight<String>();
    
    /**PathLengths Visitor for Integers*/
    RBTreeVisitor<Integer, ArrayList<Integer>> intPathVisitor = 
            new PathLengths<Integer>();
    
    /**CountNodes Visitor for Integers*/
    RBTreeVisitor<Integer, ArrayList<Integer>> intCountVisitor = 
            new CountNodes<Integer>();
    
    /**Height Visitor for Integers*/
    RBTreeVisitor<Integer, Integer> intHeightVisitor = 
            new Height<Integer>();
    
    /**BlackHeight Visitor for Integers*/
    RBTreeVisitor<Integer, Integer> intBlackHeightVisitor = 
            new BlackHeight<Integer>();
    
   /**initializes the RBTrees with values*/
   void init() { 
       for (String s : randIter) {
           stringTree = stringTree.add(s);
       }
       for(int i = 1; i < 50; i = i + 1) {
           intTree = intTree.add(((i * 23) / 6) % 250);
       }   
   }
   
   /**Resets the values of all class objects back to default */
   void reset() {
       stringTree = EmptyTree.emptyFactory(byLex);
       intTree = EmptyTree.emptyFactory(byInt);
       
       stringPathVisitor = new PathLengths<String>();
       stringCountVisitor = new CountNodes<String>();
       stringHeightVisitor = new Height<String>();
       stringBlackHeightVisitor = new BlackHeight<String>();
       
       intPathVisitor = new PathLengths<Integer>();
       intCountVisitor = new CountNodes<Integer>();
       intHeightVisitor = new Height<Integer>();
       intBlackHeightVisitor = new BlackHeight<Integer>();
   }
   
   /**EFFECT:
    * Tests whether the height visitor correctly identifies the  
    * Height of the tree calling it
    * @param t Tester
    */
   void testHeight(Tester t) {
       reset();
       t.checkExpect(stringTree.accept(stringHeightVisitor), 0);
       t.checkExpect(intTree.accept(intHeightVisitor), 0);
       init();
       t.checkExpect(stringTree.accept(stringHeightVisitor), 9);
       t.checkExpect(intTree.accept(intHeightVisitor), 7);
       t.checkExpect(stringTree.accept(stringHeightVisitor), 
               stringTree.height());
       t.checkExpect(intTree.accept(intHeightVisitor), 
               intTree.height());
   }
   
   /**EFFECT:
    * Tests whether the black height visitor correctly identifies the  
    * number of black nodes in each path of the tree calling it
    * @param t Tester
    */
   void testBlackHeight(Tester t) {
       reset();
       t.checkExpect(stringTree.accept(stringBlackHeightVisitor), 0);
       t.checkExpect(intTree.accept(intBlackHeightVisitor), 0);
       
       init();
       t.checkExpect(stringTree.accept(stringBlackHeightVisitor), 5);
       t.checkExpect(intTree.accept(intBlackHeightVisitor), 5);
       
       t.checkExpect(stringTree.accept(stringBlackHeightVisitor), 
               stringTree.minBlackCount());
       t.checkExpect(stringTree.accept(stringBlackHeightVisitor), 
               stringTree.maxBlackCount());
       
       t.checkExpect(intTree.accept(intBlackHeightVisitor), 
               intTree.minBlackCount());
       t.checkExpect(intTree.accept(intBlackHeightVisitor), 
               intTree.maxBlackCount());
   }
   
   /**EFFECT:
    * Tests whether the path visitor correctly returns the  
    * length of every path from root to empty in the tree calling it
    * @param t Tester
    */
   void testPathLengths(Tester t) {
       reset();
       //check empty tree returns an arrayList with just one 0
       t.checkExpect(stringTree.accept(stringPathVisitor), 
               new ArrayList<Integer>());
       t.checkExpect(intTree.accept(intPathVisitor), 
               new ArrayList<Integer>());
       
       reset();
       
       //add 3 strings to integer RB tree, yielding tree :
       //          2
       //        1   3
       //       e e e e
       //such that the path from root to empty for all empties
       //is 2
       for (Integer i : oneTwoThree) {
           intTree = intTree.add(i);
       }
       t.checkExpect(intTree.accept(intPathVisitor), 
               new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
       
       reset();
       init();
       
       //check that the number of entries in the list is exactly
       //1 greater than the number of elements in the tree
       t.checkExpect(stringTree.accept(stringPathVisitor).size(), 
               stringTree.count() + 1);
       t.checkExpect(intTree.accept(intPathVisitor).size(), 
               intTree.count() + 1);
   }
   
   /**EFFECT:
    * Tests whether the count visitor correctly returns the  
    * count of red and black nodes of the tree calling it
    * @param t Tester
    */
   void testCountNodes(Tester t) {
       reset();
       //Empty tree produces an empty count
       t.checkExpect(stringTree.accept(stringCountVisitor).toString(),
               "[0, 0, 0]");
       reset();
       
       //ensure that the visitor produces the correct statistics through out
       for (int i = 1; i < randIter.size(); i++) {
           //add an element to the tree
           stringTree = stringTree.add(randIter.get(i));
           
           //check that the total count of nodes matches the number of strings
           //we have inserted, which should also be equal to the tree's count
           ArrayList<Integer> counter = stringTree.accept(stringCountVisitor);
           t.checkExpect(counter.get(0), i);
           t.checkExpect(counter.get(0), stringTree.count());
           
           //reset the visitor for the next iteration
           stringCountVisitor = new CountNodes<String>();
       }
       //ensure that the count of red nodes is equal to the total count minus
       //the count of black nodes
       ArrayList<Integer> finalTally = stringTree.accept(stringCountVisitor);
       t.checkExpect(finalTally.get(2), finalTally.get(0) - finalTally.get(1));
   }
}