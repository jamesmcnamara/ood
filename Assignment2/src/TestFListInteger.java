/**
 * 1. James McNamara
 * 2. jamcnam@ccs.neu.edu
 * 3. When Google Chieftain Larry Page was at Stanford, he built an Ink Jet 
 *    printer out of Legos. He needed a printer that could print posters.
 */


/**
 * TestFListInteger is used to test the abstract class FListInteger 
 * for any errors in implementation
 * @author jamcnam
 * @version 2013-09-24
 */
public class TestFListInteger {

    private final Integer one = 1;
    private final Integer three = 3;
    private final Integer tenTwentyFour = 1024;
    private final Integer thirtyEight = 38;


    private FListInteger f0; // emptyList
    private FListInteger f1; // (1)
    private FListInteger f2; // (3, 1)
    private FListInteger f3; // (1,1024,38)
    private FListInteger f4; // (1,38)
    private FListInteger f5; // (3,1024,38)
    private FListInteger f6; // (3, 38)
    private FListInteger f7; // (1, 3, 1024, 38)
    private FListInteger f8; // (1, 3, 1024, 38)
    private FListInteger f9; // (38,1024,1)

    /**
     * Basic method for initializing the FListIntegers
     * once set, they will not be modified   
     */
    public void create() {
        try {
            f0 = FListInteger.emptyList();
            f1 = FListInteger.add(f0, one);
            f2 = FListInteger.add(f1, three);
            f3 = FListInteger.add(FListInteger.add(FListInteger.add(f0, 
                    thirtyEight), tenTwentyFour), one);
            f4 = FListInteger.add(FListInteger.add(f0, thirtyEight), one);
            f5 = FListInteger.add(FListInteger.add(FListInteger.add(f0, 
                    thirtyEight), tenTwentyFour), three);
            f6 = FListInteger.add(FListInteger.add(f0, thirtyEight), three);
            f7 = FListInteger.add(f5, one);
            f8 = FListInteger.add(f5, one);
            f9 = FListInteger.add(FListInteger.add(f1, tenTwentyFour),
                    thirtyEight);
        }
        catch (Exception someException) {
            System.out.println("Error thrown during creation: "
                    + someException);
        }
    }

    /**
     * Attempts to test the methods of the abstract class as they were 
     * specified in the algebraic specifications
     * catches any errors and prints them to the console
     */
    private void accessors() {
        try {
            //isEmpty tests
            assertTrue("isEmptyf0", FListInteger.isEmpty(f0));
            assertTrue("isEmptyEmptyList", 
                    FListInteger.isEmpty(FListInteger.emptyList()));
            assertFalse("isEmptyf9", FListInteger.isEmpty(f9));

            //get tests
            assertTrue("getf11", FListInteger.get(f1, 0) == 1);
            assertTrue("getf73", FListInteger.get(f7, 3) == 38);
            assertTrue("getf93f11", 
                    FListInteger.get(f9, 3) == FListInteger.get(f1, 1));
            assertFalse("getf92f32", 
                    FListInteger.get(f9, 2) == FListInteger.get(f3, 2));

            //set tests
            assertTrue("setf102", 
                    FListInteger.set(f1, 0, 2).equals(
                            FListInteger.add(f0, 2)));
            assertTrue("setf501", FListInteger.set(f5, 0, 1).equals(f3));
            assertTrue("setf611", FListInteger.set(f6, 1, 1).equals(f2));
            assertTrue("setf401", FListInteger.set(f4, 0, 3).equals(f6));

            assertFalse("setf02", FListInteger.set(f1, 0, 2).equals(f1));

            //size
            assertTrue("sizef0", FListInteger.size(f0) == 0);
            assertTrue("sizef9", FListInteger.size(f9) == 3);
            assertTrue("sizef8", FListInteger.size(f8) == 4);
            assertTrue("sizef7f8", FListInteger.size(f7) 
                    == FListInteger.size(f8));
        }
        catch (Exception someException) {
            System.out.println(
                    "Exception was encountered during accessors test:\n"
                            + someException + "\n" + totalTests + 
                    " have been run so far.\n");
        }
    }

    /**
     * Attempts to test that the methods inherited from the object class have
     * been successfully overridden
     * catches any exceptions and prints them to the console, as well as 
     * recording them as failed tests
     */
    private void usual() {
        try {
            //toString tests
            assertTrue("toStringf0", f0.toString().equals("[]"));
            assertTrue("toStringf1", f1.toString().equals("[1]"));
            assertTrue("toStringf7", 
                    f7.toString().equals("[1, 3, 1024, 38]"));

            //equals tests
            assertTrue("equalsf0f0", f0.equals(f0));
            assertTrue("equalsf0emptyList", 
                    f0.equals(FListInteger.emptyList()));
            assertTrue("equalsf0f0", f0.equals(f0));
            assertTrue("equalsf7f8", f7.equals(f8));
            assertFalse("equalsf9f3", f9.equals(f3));

            assertFalse("equalsf9null", f9.equals(null));
            assertFalse("equalsf0null", f0.equals(null));

            //testing hashCode
            hashCodeTest(f0, f0);
            hashCodeTest(f0, FListInteger.emptyList());
            hashCodeTest(f7, f8);
            hashCodeTest(f3, f9);
            hashCodeTest(f0, f9);

        }
        catch (Exception someException) {
            System.out.println("Exception was encountered during usual test:\n"
                    + someException + "\n" + totalTests + 
                    " have been run so far.\n");
        }
    }

    /**
     * Test of the hashCode function
     * If two objects which are equal have the same hashCode, it logs it
     * as a succesful test
     * If two objects which are equal have different hashCodes, it prints out
     * a warning message, and records it as a failed test 
     * If two objects which are not equal have the same hashCodes, it prints 
     * out a warning message, but records it as a successful test  
     * @param firstF an <code>FListInteger</code>
     * @param secondF an <code>FListInteger</code>
     */
    private void hashCodeTest(FListInteger firstF, FListInteger secondF) {
        if ((firstF.hashCode() != secondF.hashCode()) 
                && (firstF.equals(secondF))) {
            System.out.println(firstF.toString() + " and " 
                + secondF.toString() + " should have the same hashCode: "
                    + firstF.hashCode());
            assertTrue(firstF.toString() + " " + secondF.toString(), false);
        }
        else if ((firstF.hashCode() == secondF.hashCode()) 
                && (!firstF.equals(secondF))) {
            System.out.println(firstF.toString() + " and " + secondF.toString() 
                    + " should not have the same hashCode: " 
                    + firstF.hashCode());
            assertTrue(firstF.toString() + " " + secondF.toString(), true);
        }
        else {
            assertTrue("hashCodeTest", true);
        }
    }

    private int totalTests = 0;       // tests run so far
    private int totalErrors = 0;      // errors so far


    /**
     * Prints failure report if the result is false.
     * @param name <code>String</code> name of the test
     * @param result <code>boolean</code> the result to test
     */
    private void assertTrue(String name, boolean result) {
        if (!result) {
            System.out.println("The " + totalTests + " test failed:\n" + 
                    name + "\n");
            totalErrors = totalErrors + 1;
        }
        totalTests = totalTests + 1;
    }

    /**
     * Prints failure report if the result is true.
     * @param name the name of the test
     * @param result the result to test
     */
    private void assertFalse(String name, boolean result) {
        assertTrue(name, !result);
    }

    /**
     * Prints a summary of the tests.
     */
    private void summarize() {
        System.out.println("\n\nThere were " + totalErrors + 
                " errors found in " + totalTests + " tests.");
    }

    /**
     * The main method which will execute the tests
     * @param args <code>String Array</code> containing command line 
     * arguments (will be ignored)
     */
    public static void main(String[] args) {

        TestFListInteger test = new TestFListInteger();

        //creates the FListInteger objects which will be tested
        test.create();

        //run the tests
        test.accessors();
        test.usual();

        //try them again to ensure no change
        test.accessors();
        test.usual();

        //summarize the results found
        test.summarize();


    }

}