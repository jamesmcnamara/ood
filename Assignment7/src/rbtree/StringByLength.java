package rbtree;
import java.util.Comparator;



/**
 * Comparator class who's compare method determines which of the two strings 
 * is longer 
 * @author jamcnam
 * @version 2013-10-07
 */
public class StringByLength implements Comparator<String> {

    /**
     * Determines the degree that s2 is greater than s1
     * if compare returns a positive value, s2 is longer than s1
     * if compare returns 0, s2 and s1 are the same length
     * if compare returns a negative value, s1 is longer than s2
     * @param s1 <code>String</code>
     * @param s2 <code>String</code>
     * @return <code>int</code>
     */
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }

    /**
     * Determines whether the given object is a
     * <code>StringByLength</code> Comparator
     * @param o <code>Object</code>
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
        return (o instanceof StringByLength);
    }

    /**
     * returns an integer representation of this comparator
     * @return int
     */
    public int hashCode() {
        return 1;
    }
    
    /**
     * Returns a String representation of this Comparator
     * @return <code>String</code>
     */
    public String toString() {
        return "StringByLength";
    }
}
