/**
 * 1. James McNamara
 * 2. jamcnam@ccs.neu.edu
 * 3. Never draw to an inside straight.
 */

/**
 * <code>FSetString</code> is an immutable abstract data type whose values 
 * represent finite sets with elements of type <code>String</code>.
 * @author jamcnam
 * @version 2013-09-13
 */
abstract class FSetString {
    
    /**
     * Generates a new empty set
     * @return <code>new Empty</code>
     */
    public static FSetString emptySet() {
        return new Empty();
    }
    /**
     * A constructor which adds the given string to the given FSet
     * if it is not already part
     * of the FSet
     * @param f an <code>FSetString</code>
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    public static FSetString insert(FSetString f, String s) {
        return f.addM(s);
    }

    /**
     * Determines whether or not the given <code>FSetString</code> 
     * contains the given <code>String</code>
     * @param f an <code>FSetString</code>
     * @param s a <code>String</code>
     * @return <code>boolean</code>
     */
    public static boolean contains(FSetString f, String s) {
        return f.containsM(s);
    }

    /**
     * Adds the given <code>String</code> to the given <code>FSetString</code>
     * @param f an <code>FSetString</code>
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    public static FSetString add(FSetString f, String s) {
        return f.addM(s);
    }

    /**
     * Returns the number of elements in the given <code>FSetString</code> 
     * @param f an <code>FSetString</code>
     * @return an <code>int</code>
     */
    public static int size(FSetString f) {
        return f.sizeM();
    }

    /**
     * Determines whether the given <code>FSetString</code> has any 
     * <code>String</code>s in it
     * @param f an <code>FSetString</code>
     * @return <code>boolean</code>
     */
    public static boolean isEmpty(FSetString f) {
        return f.isEmptyM();
    }

    /**
     * Determines whether all of the elements in the given 
     * <code>FSetString</code> a are members of the given
     * <code>FSetString</code> b 
     * @param a an <code>FSetString</code>
     * @param b an <code>FSetString</code>
     * @return <code>boolean</code>
     */
    public static boolean isSubset(FSetString a, FSetString b) {
        return a.isSubsetM(b);
    }

    /**
     * Generates an <code>FSetString</code> that has all of the 
     * <code>String</code>s of f except for the <code>String</code> s
     * @param f an <code>FSetString</code>
     * @param s a <code>String</code>
     * @return <code>FSetString</code>
     */
    public static FSetString absent(FSetString f, String s) {
        return f.absentM(s);
    }

    /**
     * Generates an <code>FSetString</code> that has each of the 
     * <code>String</code>s in a and b without any repitition
     * @param a an <code>FSetString</code>
     * @param b an <code>FSetString</code>
     * @return <code>FSetString</code>
     */
    public static FSetString union(FSetString a, FSetString b) {
        return a.unionM(b);
    }

    /**
     * Generates an <code>FSetString</code> that has only the 
     * <code>String</code>s that appear in a and b
     * @param a an <code>FSetString</code>
     * @param b an <code>FSetString</code>
     * @return <code>FSetString</code>
     */
    public static FSetString intersect(FSetString a, FSetString b) {
        return a.intersectM(b);
    }
    
    /**
     * Determines whether the given <code>Object</code> o is an 
     * <code>FSetString</code> and if so, whether it has the same 
     * <code>String</code>s as this <code>FSetString</code>
     * @param o <code>Object</code>
     * @return boolean 
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } 
        else if (o instanceof FSetString) {
            return this.equalsM((FSetString) o);
        } 
        else {
            return false;
        }
    }
    
    /**
     * Returns a <code>String</code> representation of this 
     * <code>FSetString</code>
     * @return a <code>String</code>
     */
    public String toString() {
        return "{...(" + this.sizeM() + " elements)...}";
    }


    /**
     * Contains method to be overridden in extended classes
     * @param s a <code>String</code>
     * @return boolean
     */
    abstract boolean containsM(String s);
    
    /**
     * Add method to be overridden in extended classes
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    abstract FSetString addM(String s);
    
    /**
     * Size method to be overridden in extended classes
     * @return int
     */
    abstract int sizeM();
    
    /**
     * isEmpty method to be overridden in extended classes
     * @return boolean
     */
    abstract boolean isEmptyM();
    
    /**
     * isSubset method to be overridden in extended classes
     * @param f an <code>FSetString</code>
     * @return boolean
     */
    abstract boolean isSubsetM(FSetString f);
    
    /**
     * Absent method to be overridden in extended classes
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    abstract FSetString absentM(String s);
    
    /**
     * union method to be overridden in extended classes
     * @param f an <code>FSetString</code>
     * @return an <code>FSetString</code>
     */
    abstract FSetString unionM(FSetString f);
    
    /**
     * intersect method to be overridden in extended classes
     * @param f an <code>FSetString</code>
     * @return an <code>FSetString</code>
     */
    abstract FSetString intersectM(FSetString f);
    
    /**
     * equals method to be overridden in extended classes
     * @param f an <code>FSetString</code>
     * @return boolean
     */
    abstract boolean equalsM(FSetString f);
    
    /**
     * Returns the number of Strings contained in this <code>FSetString</code>
     * @return an <code>int</code>
     */
    public int hashCode() {
        return this.sizeM();
    }

}
/**
 * <code>Empty</code> is a concrete extension of the ADT 
 * <code>FSetString</code> that represents an <code>FSetString</code> 
 * that contains no <code>String</code>s
 * @author jamcnam
 * @version 2013-09-17
 */
class Empty extends FSetString {

    /**
     * The Empty <code>FSetString</code> contains no strings
     * @param s a <code>String</code>
     * @return <code>false</code>
     */
    boolean containsM(String s) {
        return false;
    }

    /**
     * Returns a new <code>FSetString</code> that contains the given 
     * <code>String</code>
     * 
     *  @param s <code>String</code>
     *  @return an <code>Insert</code>
     */
    FSetString addM(String s) {
        return new Insert(this, s);
    }

    /**
     * Returns 0 because <code>Empty</code>s have no 
     * <code>String</code>s
     * 
     * @return 0
     */
    int sizeM() {
        return 0;
    }

    /**
     * Returns true because this is of the <code>Empty</code> type
     * @return true
     */
    boolean isEmptyM() {
        return true;
    }

    /**
     * returns true because the empty set is a subset of all 
     * <code>FSetString</code>s
     * @param f an <code>FSetString</code>
     * @return true 
     */
    boolean isSubsetM(FSetString f) {
        return true;
    }

    /**
     * Returns the <code>FSetString</code> f because the empty class 
     * has no <code>String</code>s to contribute to the union
     * @param f an <code>FSetString</code> 
     * @return an <code>FSetString</code>
     */
    FSetString unionM(FSetString f) {
        return f;
    }

    /**
     * Returns this because it does not contain <code>String</code> s
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    FSetString absentM(String s) {
        return this;
    }

    /**
     * Returns the intersection of this class and the given 
     * <code>FSetString</code> f, which is simply this
     * @param f an <code>FSetString</code>
     * @return an <code>FSetString</code>
     */
    FSetString intersectM(FSetString f) {
        return this;
    }

    /**
     * Determines whether the given <code>FSetString</code> has the same
     * <code>String</code>s as this <code>FSetString</code>
     * @param f an <code>FSetString</code>
     * @return boolean
     */
    public boolean equalsM(FSetString f) {
        return f.isEmptyM();
    }
}

/**
 * Insert is a concrete extenion of the ADT <code>FSetString</code>
 * that represents an <code>FSetString</code> that contains at least one 
 * <code>String</code>
 * @author jamcnam
 * @version 2013-09-17
 */
class Insert extends FSetString {

    private FSetString myFSet;
    private String myValue;
    private int mySize;

    /**
     * The constructor of the Insert object
     * @param f an <code>FSetString</code>
     * @param s a <code>String</code>
     */
    Insert(FSetString f, String s) {
        this.myFSet = f;
        this.myValue = s;
        this.mySize = f.sizeM() + 1;
    }

    /**
     * Determines whether this <code>Insert</code> contains the given
     * <code>String</code> s
     * @param s an <code>String</code>
     * @return boolean
     */
    boolean containsM(String s) {
        return s.equals(this.myValue) || this.myFSet.containsM(s);
    }

    /**
     * Returns the number of <code>String</code>s in this 
     * <code>Insert</code>
     * @return <code>int</code>
     */
    int sizeM() {
        return this.mySize;
    }

    /**
     * Returns false because <code>Insert</code>s are not 
     * <code>Empty</code>s
     * @return boolean
     */
    boolean isEmptyM() {
        return false;
    }

    /**
     * Generates an <code>FSetString</code> that represents
     * contains the given <code>String</code> s one time
     * @param s <code>String</code>
     * @return <code>FSetString</code>
     */
    FSetString addM(String s) {
        if (this.containsM(s)) {
            return this;
        } 
        else {
            return new Insert(this, s);
        }
    }

    /**
     * Determines whether this <code>FSetString</code> is a subset of
     * the given <code>FSetString</code> f
     * @param f an <code>FSetString</code>
     * @return boolean
     */
    boolean isSubsetM(FSetString f) {
        return f.containsM(this.myValue) && this.myFSet.isSubsetM(f);
    }

    /**
     * Generates an FSetString that represents this <code>FSetString</code>
     * without the given <code>String</code> s
     * @param s a <code>String</code>
     * @return an <code>FSetString</code>
     */
    FSetString absentM(String s) {
        if (s.equals(this.myValue)) {
            return this.myFSet.absentM(s);
        } 
        else {
            return new Insert(this.myFSet.absentM(s), this.myValue);
        }
    }
    
    /**
     * Generates an <code>FSetString</code> that represents all of the 
     * <code>String</code>s in this <code>FSetString</code> and the given 
     * <code>FSetString</code> f without any repititions
     * @param f an <code>FSetString</code>
     * @return an <code>FSetString</code>
     */
    FSetString unionM(FSetString f) {
        if (f.containsM(this.myValue)) {
            return this.myFSet.unionM(f);
        } 
        else {
            return new Insert(this.myFSet.unionM(f), this.myValue);
        }
    }

    /**
     * Generates an <code>FSetString</code> that represents only the 
     * <code>String</code>s in this <code>FSetString</code> that also 
     * appear in the given <code>FSetString</code> f
     * @param f an <code>FSetString</code>
     * @return <code>FSetString</code>
     */
    FSetString intersectM(FSetString f) {
        if (f.containsM(this.myValue)) {
            return new Insert(this.myFSet.intersectM(f), this.myValue);
        } 
        else {
            return this.myFSet.intersectM(f);
        }
    }

    /**
     * Determines whether the given <code>FSetString</code> has the same
     * <code>String</code>s as this <code>FSetString</code>
     * @param f an <code>FSetString</code>
     * @return boolean
     */
    public boolean equalsM(FSetString f) {
        return (this.intersectM(f).sizeM() == this.unionM(f).sizeM());
    }
}