import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Timer object used as a means of testing the speed of the various methods
 * across the BTree Object 
 * @author jamcnam
 * @version 2013-10-19 
 */
class TimerTest {

    /**
     * Testing framework used when there is exists a limit on the number of 
     * words to be tested
     * Given a list of wordcounts, comparators, file names, and Strings, 
     * the method will run through every possible combination of the variables,
     * and print to the console results regarding the time the method took
     * @param wordCount <code>ArrayList</code> of <code>Integer</code>s
     * @param compList <code>ArrayList</code> of <code>Comparator</code>s 
     * of <code>String</code>s
     * @param fileNames <code>ArrayList</code> of <code>String</code>s
     * @param functions <code>ArrayList</code> of <code>String</code>s
     */
    public void loopTest(ArrayList<Integer> wordCount,
            ArrayList<Comparator<String>> compList, 
            ArrayList<String> fileNames,
            ArrayList<String> functions) {
        long startTime = 0;
        long endTime = 0;
        for (Integer w : wordCount) {
            for (Comparator<String> comp : compList) {
                for (String f : fileNames) {
                    BTree b = BTree.binTree(comp);
                    for (String fxn : functions) {
                        startTime = System.currentTimeMillis();
                        if (fxn == "Build") {                            
                            buildTime(b, new StringIterator(f), w);
                        }
                        else if (fxn == "Iterate") {
                            iterateTime(b);
                        }
                        else if (fxn == "Contains") {
                            containsTime(b);
                        }
                        endTime = System.currentTimeMillis();
                        System.out.println("Comp," + comp + ",Words," + w
                                + ",File," + f + ",Function," + fxn + 
                                ",Runtime," + (endTime - startTime));
                    }
                }
            }
        }

    }


    /**
     * Runs the BTree build method on the given BTree, iterating through
     * the given <code>StringIterator</code> a number of times corresponding
     * to the number of strings
     * @param bt <code>BTree</code>
     * @param iter <code>StringIterator</code>
     * @param numStrings <code>int</code>
     */
    public static void buildTime(BTree bt, StringIterator iter,
            int numStrings) {
        bt.build(iter, numStrings);
    }

    /**
     * Runs BTree's iterator from start to finish, traversing every
     * item of the list
     * @param bt <code>BTree</code>
     */
    public static void iterateTime(BTree bt) {
        Iterator<String> it = bt.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    /**
     * Runs BTree's contains method for every single item in the "contains"
     * text file
     * @param bt <code>BTree</code>
     */
    public static void containsTime(BTree bt) {
        StringIterator iter = new StringIterator("contains.txt");

        while (iter.hasNext()) {
            bt.contains(iter.next());
        }
    }


    /**
     * main method of the TimeTester class
     * Runs all of the timing tests over the arrays of word counts, files,
     * comparators, and functions
     * @param args Command line arguments 
     */
    public static void main(String [] args) {
        
        /**Instance of the TimerTest class for testing */
        TimerTest t = new TimerTest();
        
        /** List of the number of words to iterate through on 
         * each of the tests */
        ArrayList<Integer> wordCount = new ArrayList<Integer>(
                Arrays.asList(2000, 4000, 8000, 16000, 20000, 24000));

        /**List of the different comparators to use for building BTrees */
        @SuppressWarnings("unchecked")
        ArrayList<Comparator<String>> compList = 
                new ArrayList<Comparator<String>>(Arrays.asList(
                        new StringByLex(), new StringReverseByLex(), 
                        new StringWithOutPrefixByLex()));

        /** List of the raw text files*/
        ArrayList<String> rawText = new ArrayList<String>(Arrays.asList(
                "lexicographically_ordered.txt", "reverse_ordered.txt",
                "prefix_order.txt", "random_order.txt"));
        
        /** List of the classical literature text files */
        ArrayList<String> classicalLit = new ArrayList<String>(Arrays.asList(
                "illiad.txt", "hippooath_DUPLICATES.txt"));


        /** List of the methods to test */
        ArrayList<String> functions = new ArrayList<String>(
                Arrays.asList("Build", "Iterate", "Contains"));


        t.loopTest(wordCount, compList, rawText, functions);
        System.out.println("\n\n-- BEGIN CLASSICAL LITERATURE -- \n\n");
        t.loopTest(new ArrayList<Integer>(Arrays.asList(-1)), compList, 
                classicalLit, functions);

    }

}