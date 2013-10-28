import java.util.*;
import tester.*;

/**
 * The <code>MyAlgorithms</code> class is used for running static methods
 * to manipulate <code>ArrayList</code>s
 * @author James jamcnam
 * @version 2013-09-11
 */
class MyAlgorithms {
    
    /**
     * Implements a bubblesort algorithm on the given 
     * <code>ArrayList</code> to sort it lexicographically 
     * @param slist an <code>ArrayList</code> of <code>String</code>s
     */ 
    public static void sort(ArrayList<String> slist) {
        if (slist == null || slist.size() == 0) {
            return;
        }
        boolean unsorted = true;
        while (unsorted) {
            //each time the loop is entered set the variable to false
            unsorted = false;
            
            //run through the list looking to see if any string is 
            //alphabetically a precedent of its next adjacent neighbor
            for (int i = 0; i < slist.size() - 1; i++) {
                //if so, swap the neighbors 
                if (slist.get(i).compareTo(slist.get(i + 1)) > 0) {
                    String swap = slist.get(i); // the string to be swapped
                    
                    //swap the neighbor with the current string
                    slist.set(i, slist.get(i + 1)); 
                    //place the current string in it's neighbors slot
                    slist.set(i + 1, swap);
                    
                    //reset the boolean switch for at least one more loop
                    unsorted = true;
                }
            }
        }
    }
}
/**
 * The <code>Examples</code> class is used for running test methods
 * on the various <code>MyAlgorithms</code> methods.
 * @author James jamcnam
 * @version 2013-09-11
 */
class Examples {

    private ArrayList<String> los = new ArrayList<String>();
    private ArrayList<String> mtLos = new ArrayList<String>();
    private ArrayList<String> sortedMtLos = new ArrayList<String>();
    private MyAlgorithms algo = new MyAlgorithms();
    
    /**Initializes the list with an unsorted list of <code>String</code>s
     * 
     */
    void init() {
        los.add("Hello");
        los.add("Aloha");
        los.add("Hi");
        los.add("What's crackin'");
        los.add("Sup");
        los.add("Shalom");
        los.add("Ahoy Hoy");
        los.add("Hola");
        los.add("Salut");
    }

    /**Tests to ensure that the <code>sort</code> method correctly sorts
     * the example <code>ArrayList</code>
     * 
     * @param t a <code>Tester</code> object
     */
    void testCheckOrder(Tester t) {
        init();
        t.checkExpect(los.get(0), "Hello");
        t.checkExpect(los.get(1), "Aloha");
        t.checkExpect(los.get(2), "Hi");
        t.checkExpect(los.get(3), "What's crackin'");
        t.checkExpect(los.get(4), "Sup");
        t.checkExpect(los.get(5), "Shalom");
        t.checkExpect(los.get(6), "Ahoy Hoy");
        t.checkExpect(los.get(7), "Hola");
        t.checkExpect(los.get(8), "Salut");
        
        algo.sort(los);
        t.checkExpect(los.get(0), "Ahoy Hoy");
        t.checkExpect(los.get(1), "Aloha");
        t.checkExpect(los.get(2), "Hello");
        t.checkExpect(los.get(3), "Hi");
        t.checkExpect(los.get(4), "Hola");
        t.checkExpect(los.get(5), "Salut");
        t.checkExpect(los.get(6), "Shalom");
        t.checkExpect(los.get(7), "Sup");
        t.checkExpect(los.get(8), "What's crackin'");
        
        t.checkExpect(mtLos, sortedMtLos);
        algo.sort(sortedMtLos);
        t.checkExpect(mtLos, sortedMtLos);
        
        mtLos = null;
        algo.sort(mtLos);
        t.checkExpect(mtLos, null);
        
        
        
        
        
        
    }

}
