import java.util.Comparator;
import tester.Tester;
import rbtree.IntBySize;

/**
 * White Box Tests for comparators
 * @author jamcnam
 * @version 2013-11-01
 */
public class ExamplesComparator {
    /** Test Comparator - lexicographic */
    Comparator<String> lex = new StringByLex();
    /** Test Comparator - length */
    Comparator<String> len = new StringByLength();
    /**Test Comparator - integers */
    Comparator<Integer> size = new IntBySize();
    
    /**EFFECT:
     * Test the compare method of the comparator
     * @param t <code>Tester</code>
     */
    public void testCompare(Tester t) {
        t.checkExpect(lex.compare("hello", "goodbye") > 0, true);
        t.checkExpect(lex.compare("hello", "yo") < 0, true);
        t.checkExpect(lex.compare("hello", "hello") == 0, true);
        
        t.checkExpect(len.compare("hello", "goodbye") < 0, true);
        t.checkExpect(len.compare("hello", "yo") > 0, true);
        t.checkExpect(len.compare("hello", "hello") == 0, true);
        
        t.checkExpect(size.compare(1, 2), -1);
        t.checkExpect(size.compare(8, 3), 5);
        t.checkExpect(size.compare(9, 9), 0);
    }
    
    /**EFFECT:
     * Test the hash code of the comparator
     * @param t <code>Tester</code>
     */
    public void testHashCode(Tester t) {
        t.checkExpect(lex.hashCode(), lex.hashCode());
        t.checkExpect(len.hashCode(), len.hashCode());
        t.checkExpect(len.hashCode() != lex.hashCode(), true);
        t.checkExpect(len.hashCode() != size.hashCode(), true);
    }
    
    /**EFFECT:
     * Test the toString of the comparator
     * @param t <code>Tester</code>
     */
    public void testToString(Tester t) {
        t.checkExpect(lex.toString(), "StringByLex");
        t.checkExpect(len.toString(), "StringByLength");
        t.checkExpect(size.toString(), "IntBySize");
    }
    
    /**EFFECT:
     * Test the equals code of the comparator
     * @param t <code>Tester</code>
     */
    public void testEquals(Tester t) {
        Comparator<String> lex2 = new StringByLex();
        Comparator<String> len2 = new StringByLength();
        Comparator<Integer> size2 = new IntBySize();
        t.checkExpect(lex.equals(lex2), true);
        t.checkExpect(len.equals(len2), true);
        t.checkExpect(size.equals(size2), true);
        t.checkExpect(lex.equals(len2), false);
        t.checkExpect(len.equals(lex2), false);
        t.checkExpect(size.equals(lex2), false);
        t.checkExpect(lex.equals(null), false);
        t.checkExpect(len.equals(null), false);
        t.checkExpect(size.equals(""), false);
        
    }
}