package rbtree;
import java.util.Comparator;
import rbtree.StringByLength;
import rbtree.StringByLex;
import tester.Tester;

/**
 * White Box Tests for comparators
 * @author jamcnam
 * @version 2013-11-01
 */
public class ComparatorsTests {
    /** Test Comparator */
    Comparator<String> lex = new StringByLex();
    /** Test Comparator */
    Comparator<String> len = new StringByLength();
    
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
    }
    
    /**EFFECT:
     * Test the hash code of the comparator
     * @param t <code>Tester</code>
     */
    public void testHashCode(Tester t) {
        t.checkExpect(lex.hashCode(), lex.hashCode());
        t.checkExpect(len.hashCode(), len.hashCode());
        t.checkExpect(len.hashCode() != lex.hashCode(), true);
    }
    
    /**EFFECT:
     * Test the toString of the comparator
     * @param t <code>Tester</code>
     */
    public void testToString(Tester t) {
        t.checkExpect(lex.toString(), "StringByLex");
        t.checkExpect(len.toString(), "StringByLength");
    }
    
    /**EFFECT:
     * Test the equals code of the comparator
     * @param t <code>Tester</code>
     */
    public void testEquals(Tester t) {
        Comparator<String> lex2 = new StringByLex();
        Comparator<String> len2 = new StringByLength();
        t.checkExpect(lex.equals(lex2), true);
        t.checkExpect(len.equals(len2), true);
        t.checkExpect(lex.equals(len2), false);
        t.checkExpect(len.equals(lex2), false);
        t.checkExpect(lex.equals(null), false);
        t.checkExpect(len.equals(null), false);
    }
}