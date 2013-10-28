/**
 * 1. James McNamara
 * 2. jamcnam@ccs.neu.edu
 * 3. All consumer GPS devices are required to automatically shut-off if they
 *    reach an altitude of 18,000m or a speed of 1,900 km/h. This is to 
 *    prevent them from being used to guide a missle.
 */

/**
 * The abstract class FListInteger is an abstract representation of a Integer
 * stack
 * @author jamcnam
 * @version 2013-09-27
 */
abstract class FListInteger {

    /**
     * Generates a new <code>Empty</code>
     * @return <code>FListInteger</code>
     */
    static FListInteger emptyList() {
        return new Empty();
    }

    /**
     * Adds the given <code>Integer</code> n to the given 
     * <code>FListInteger</code>
     * @param f an <code>FListInteger</code>
     * @param n an <code>Integer</code>
     * @return <code>FListInteger</code>
     */
    static FListInteger add(FListInteger f, Integer n) {
        return new Add(f, n);
    }

    /**
     * Determines whether the given <code>FListInteger</code> is empty
     * @param f <code>FListInteger</code>
     * @return <code>boolean</code>
     */
    static boolean isEmpty(FListInteger f) {
        return f.isEmptyM();
    }

    /**
     * returns the n-th <code>Integer</code> in the given 
     * <code>FListInteger</code>
     * @param f <code>FListInteger</code>
     * @param n <code>Integer</code>
     * @return <code>Integer</code>
     */
    static Integer get(FListInteger f, int n) {
        return f.getM(n);
    }

    /**
     * Returns an <code>FListInteger</code> with the n1-th <code>Integer</code>
     * in the given <code>FListInteger</code> set to the value n0
     * @param f <code>FListInteger</code>
     * @param n0 <code>int</code>
     * @param n1 <code>Integer</code>
     * @return <code>FListInteger</code>
     */
    static FListInteger set(FListInteger f, int n0, Integer n1) {
        return f.setM(n0, n1);
    }

    /**
     * Returns the number of <code>Integer</code>s in the given 
     * <code>FListInteger</code>
     * @param f <code>FListInteger</code>
     * @return <code>int</code>
     */
    static int size(FListInteger f) {
        return f.sizeM();
    }

    /**
     * Determines whether this <code>FListInteger</code> is equal to the 
     * given <code>Object</code> o
     * @param o <code>Object</code>
     * @return <code>boolean</code>
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        else if (o instanceof FListInteger) {
            return this.equalsM((FListInteger) o);
        }
        else {
            return false;
        }
    }

    /**
     * Abstract method called by isEmpty to be implemented in the extended
     * classes
     * @return <code>boolean</code>
     */
    abstract boolean isEmptyM();

    /**Abstract method called by get to be implemented in the extended
     * classes
     * @param n <code>int</code>
     * @return <code>Integer</code>
     */
    abstract Integer getM(int n);

    /**Abstract method called by set to be implemented in the extended
     * classes
     * @param n0 <code>int</code>
     * @param n1 <code>Integer</code>
     * @return <code>FListInteger</code>
     */
    abstract FListInteger setM(int n0, Integer n1);

    /**Abstract method called by size to be implemented in the extended
     * classes
     * @return <code>int</code>
     */
    abstract int sizeM();

    /**Abstract method called by equals to be implemented in the extended
     * classes
     * @param f <code>FListInteger</code>
     * @return <code>boolean</code>
     */
    abstract boolean equalsM(FListInteger f);
}

/**
 * Empty is a concrete extension of the abstract data type 
 * <code>FListInteger</code>
 * It represents and <code>FListInteger</code> with no elements 
 * @author jamcnam
 * @version 2013-09-27
 */
class Empty extends FListInteger {

    private int size;

    /** Constructor */
    Empty() {
        this.size = 0;
    }

    /**Implements is empty by returning true 
     * @return <code>boolean</code>*/
    boolean isEmptyM() {
        return true;
    }

    /**Method not defined by algebraic specification. Returns 0 
     * for convenience sake.
     * @param n <code>int</code>
     * @return <code>Integer</code>*/
    Integer getM(int n) {
        return 0;
    }

    /**Method not defined by algebraic specification. Returns this  
     * Empty for convenience sake.
     * @param n0 <code>int</code>
     * @param n1 <code>Integer</code>
     * @return <code>FListInteger</code>*/
    FListInteger setM(int n0, Integer n1) {
        return this;
    }

    /**
     * Returns the size of this <code>FListInteger</code> (0)
     * @return <code>int</code>
     */
    int sizeM() {
        return this.size;
    }

    /**Returns a <code>String</code> representation of this object
     * @return <code>String</code>
     */
    public String toString() {
        return "[]";
    }

    /**Determines whether the given <code>FListInteger</code> is equivalent
     * to this <code>Empty</code>
     * @param f <code>FListInteger</code>
     * @return <code>boolean</code>
     */
    public boolean equalsM(FListInteger f) {
        return f.isEmptyM();
    }

    /**Returns a hashCode for this object
     * @return <code>int</code>
     */
    public int hashCode() {
        return 0;
    }
}

/**
 * Add is a concrete extension of the ADT <code>FListInteger</code>.
 * Add represents an Integer stack which contains non-zero elements 
 * @author jamcnam
 * @version 2013-09-27 
 */
class Add extends FListInteger {
    private FListInteger myFList;
    private Integer myValue;
    private int size;

    /** Constructor 
     * @param f <code>FListInteger</code>
     * @param n <code>Integer</code>*/
    Add(FListInteger f, Integer n) {
        this.myFList = f;
        this.myValue = n;
        this.size = 1 + f.sizeM();
    }

    /**Implements is empty by returning false
     * @return <code>boolean</code>*/
    boolean isEmptyM() {
        return false;
    }

    /**Implements the get method. Returns the n-th element in this 
     * <code>FListInteger</code>
     * @param n <code>int</code>
     * @return <code>Integer</code>*/
    Integer getM(int n) {
        if (n == 0) {
            return this.myValue; 
        }
        else if (n > 0) {
            return this.myFList.getM(n - 1);
        }
        else {
            throw new RuntimeException("Values of n must be non-negative.");
        }
    }

    /**Implements the set method. Returns a <code>FListInteger</code>
     * with the n0-th <code>Integer</code> set to n1, and all other
     * values equivalent to this <code>FListInteger</code>
     * @param depth <code>int</code>
     * @param value <code>Integer</code>
     * @return <code>FListInteger</code>*/
    FListInteger setM(int depth, Integer value) {
        if (depth == 0) {
            return new Add(this.myFList, value);
        }
        else if (depth > 0) {
            return new Add(this.myFList.setM(depth - 1, value), this.myValue);
        }
        else {
            throw new RuntimeException("Values of n0 must be non-negative.");
        }
    }

    /** Returns the size of this <code>FListInteger</code>
     * @return <code>int</code>
     */
    int sizeM() {
        return this.size;
    }

    /** Returns a <code>String</code> representation of this 
     * <code>FListInteger</code> 
     * @return <code>String</code>
     */
    public String toString() {
        if (this.myFList.isEmptyM()) {
            return "[" + this.myValue + "]";
        }
        else {
            return "[" + this.myValue + ", " + 
                    this.myFList.toString().substring(1, 
                            this.myFList.toString().length());
        }
    }

    /** Implements the equalsM method of the ADT <code>FListInteger</code>
     * @param f <code>FListInteger</code>
     * @return <code>boolean</code>
     */
    public boolean equalsM(FListInteger f) {
        if (this.sizeM() == f.sizeM()) {
            for (int i = 0; i < this.sizeM(); i = i + 1) {
                if (!(this.getM(i).equals(f.getM(i)))) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /** Returns a hashCode for this <code>FListInteger</code> that is 
     * non-communative i.e. <code>FListInteger</code>s with the same values
     * in different orders will have different hashCodes
     * @return <code>int</code>
     */
    public int hashCode() {
        return this.myValue + 100 * this.myFList.hashCode(); 
    }
}