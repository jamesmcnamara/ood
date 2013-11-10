package rbtree;

import java.util.Comparator;

/**
 * Comparator for comparaing two integers
 * Seriously, how is this not built in?
 * @author jamcnam
 * @version 2013-11-07
 */
public class IntBySize implements Comparator<Integer> {

    /**EFFECT
     * Returns an integer representation of the distance
     * between the given integers
     * Positive: i1 is greater
     * Zero: i1 and i2 are equal
     * Negative: i2 is greater
     * @return <code>Integer</code>
     */
    public int compare(Integer i1, Integer i2) {
        return i1-i2;
    }
    
    /**EFFECT:
     * Produces a boolean that describes whether the given
     * object is an IntBySize
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
        return o instanceof IntBySize;
    }
    
    /**EFFECT:
     * Returns an integer representation of this comparator
     * @return <code>Integer</code>
     */
    public int hashCode() {
        return 3;
    }
    
    /**EFFECT:
     * Returns a <code>String</code> representation of this comparator
     * @return <code>String</code>
     */
    public String toString() {
        return "IntBySize";
    }
}
