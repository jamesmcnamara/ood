import java.util.*;
import tester.*;

public class BTreeTestsBB {
	/**Comparators for sorting */
	Comparator<String> stringByLex = new StringByLex();
	Comparator<String> stringByLen = new StringByLength();
	
	/**BTrees to test*/
	BTree bByLex = BTree.binTree(stringByLex);
	BTree bByLex2 = BTree.binTree(stringByLex);
	BTree bByLen = BTree.binTree(stringByLen);

	/**String iterators*/
	StringIterator lexIter = new StringIterator(
			"lexicographically_ordered.txt");
	StringIterator lexIter2 = new StringIterator(
			"lexicographically_ordered.txt");
	StringIterator randIter = new StringIterator("random_order.txt");
	StringIterator randIter2 = new StringIterator("random_order.txt");
	
	/**MODIFIES:
	 * method will set every object used for testing back to 
	 * its factory settings
	 */
	public void reset() {
		bByLex = BTree.binTree(stringByLex);		
		bByLex2 = BTree.binTree(stringByLex);
		bByLen = BTree.binTree(stringByLen);
				
		lexIter = new StringIterator("lexicographically_ordered.txt");
		randIter = new StringIterator("random_order.txt");
		lexIter2 = new StringIterator("lexicographically_ordered.txt");
		randIter2 = new StringIterator("random_order.txt");
	}
	
	/**EFFECT:
	 * Method tests whether the factory method for BTree operates as specified
	 * @param t <code>Tester</code>
	 */
	public void testBinTree(Tester t) {
		BTree b = BTree.binTree(stringByLex);
		t.checkExpect(b instanceof BTree, true);
		
	}
	
	/**EFFECT:
	 * Method tests whether the build method for BTree operates as specified
	 * @param t <code>Tester</code>
	 */
	public void testBuild(Tester t) {
		reset();
		//empty trees have nothing in them
		t.checkExpect(bByLex.contains("aardvark"), false);
		t.checkExpect(bByLen.contains("aardvarks"), false);
		
		bByLex.build(lexIter);
		bByLen.build(randIter);
		
		//Trees that have been built now contain certain strings
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLen.contains("aardvarks"), true);
		t.checkExpect(bByLex.contains("zathura"), false);
	}
	
	/**EFFECT:
	 * Method tests the build method for BTree which consumes a number
	 * of strings to iterate over and ensures that the right number of
	 * strings is being added
	 * @param t <code>Tester</code>
	 */
	public void testBuildNumStrings(Tester t) {
		reset();
		
		//Test with a small number to see that only some strings are added
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lexIter, 10);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), false);
		
		//Test with more strings, to show that increasing the number
		//increases the count
		reset();
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lexIter, 100);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), true);
		
		//test with a negative number to show that all strings are added
		reset();
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lexIter,-1);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), true);
	}
	
	/**EFFECT:
	 * Method tests whether the contains method for BTree correctly
	 * identifies if a string belongs to the given tree
	 * @param t <code>Tester</code>
	 */
	public void testContains(Tester t) {
		reset();
		t.checkExpect(bByLen.contains("Hello"), false);
		t.checkExpect(bByLen.contains("Goodbye"), false);
		
		bByLen.build(new ArrayList<String>(Arrays.asList("Hello")));
		
		t.checkExpect(bByLen.contains("Hello"), true);
		t.checkExpect(bByLen.contains("Goodbye"), false);
	}
	
	/**EFFECT:
	 * Method tests whether the toString method for BTree returns the
	 * correct string representation of a BTree
	 * @param t <code>Tester</code>
	 */
	public void testToString(Tester t) {
		reset();
		
		//empty tree returns an empty string
		t.checkExpect(bByLex.toString(), "");
		
		bByLex.build(new ArrayList<String>(Arrays.asList("Hi", "Aloha", 
				"Bonjour", "Wilkommen", "Jambo", "Bienvenido", "Nieho")));
		
		//sorted, comma seperated string representation without trailing commas
		t.checkExpect(bByLex.toString(),
				"Aloha, Bienvenido, Bonjour, Hi, Jambo, Nieho, Wilkommen");
		
		
		t.checkExpect(bByLen.toString(), "");
		bByLen.build(new ArrayList<String>(
				Arrays.asList("Hello", "Aloha", "Goodbye")));
		
		//duplicates are ignored
		t.checkExpect(bByLen.toString(), "Hello, Goodbye");
	}
	
	/**EFFECT:
	 * Method tests whether the equals method for BTree returns the
	 * correct evaluation of 2 BTree's
	 * @param t <code>Tester</code>
	 */
	public void testEquals(Tester t) {
		reset();
		//base cases
		//objects equal themselves, and other BTrees that
		//have the same comparator
		t.checkExpect(bByLex.equals(bByLen), false);
		t.checkExpect(bByLex.equals(bByLex), true);
		t.checkExpect(bByLex.equals(bByLex2), true);
		
		bByLex.build(lexIter, 1000);
		bByLex2.build(lexIter2, 100);
		
		//BTrees with different data are different
		t.checkExpect(bByLex.equals(bByLex2), false);
		
		reset();
		t.checkExpect(bByLex.equals(bByLex), true);
		t.checkExpect(bByLex.equals(bByLex2), true);
		
		ArrayList<String> arList = 
				new ArrayList<String>(
						Arrays.asList("hi", "bye", "whattup", "goodbye")); 
		ArrayList<String> arListBackwards = 
				new ArrayList<String>(
						Arrays.asList("goodbye","whattup", "bye", "hi"));
		
		//the two trees will end up with the same data, but different structs
		bByLex.build(arList);
		bByLex2.build(arListBackwards);
		
		//BTrees with the same data but different structure are different
		t.checkExpect(bByLex.equals(bByLex2), false);
		
		//However BTrees with the same data have the same string representation
		t.checkExpect(bByLex.toString().equals(bByLex2.toString()), true);
	}
	
	/**EFFECT:
	 * Method tests whether the hashCode method for BTree exhibits the
	 * correct behavior
	 * Should return the same hashCode for equal objects
	 * @param t <code>Tester</code>
	 */
	public void testHashCode(Tester t) {
		reset();
		t.checkExpect(bByLex.hashCode(), bByLex2.hashCode());
		t.checkExpect(bByLex.hashCode() == bByLen.hashCode(), false);
	
		bByLex.build(lexIter, 1000);
		bByLex2.build(lexIter2, 100);
		
		//BTrees with different data have different hashCodes
		t.checkExpect(bByLex.hashCode() == bByLex2.hashCode(), false);
		
		reset();
		t.checkExpect(bByLex.hashCode(), bByLex2.hashCode());
		ArrayList<String> arList = 
				new ArrayList<String>(
						Arrays.asList("hi", "bye", "whattup", "goodbye")); 
		ArrayList<String> arListBackwards = 
				new ArrayList<String>(
						Arrays.asList("goodbye","whattup", "bye", "hi"));
		bByLex.build(arList);
		bByLex2.build(arListBackwards);
		
		//BTrees with the same data but different structures
		//have different hashes
		t.checkExpect(bByLex.equals(bByLex2), false);
		
		reset();
		bByLex.build(arList);
		bByLex2.build(arList);
		//However BTrees with the same data and structure have the same 
		//hash codes
		t.checkExpect(bByLex.hashCode(), bByLex2.hashCode());
	}
	
	/**EFFECT:
	 * Method tests whether the iterator method for BTree returns an
	 * iterator which exhibits the right behavior
	 * @param t <code>Tester</code>
	 */
	public void testIterator(Tester t) {
		reset();
		Iterator<String> bByLexIter = bByLex.iterator(); 
		t.checkExpect(bByLexIter.hasNext(), false);
		
		//can't call next on empty iterator
		t.checkException(new NoSuchElementException(), bByLexIter, "next");
		bByLex.build(lexIter, 100);
		Iterator<String> bByLexIter2 = bByLex.iterator();
		
		//old iterator has not been updated but the new one has
		t.checkExpect(bByLexIter.hasNext(), false);
		t.checkExpect(bByLexIter2.hasNext(), true);
		
		//each call to next advances the iterator
		t.checkExpect(bByLexIter2.next() == bByLexIter2.next(), false);
		
		//cannot modify this BTree while iterating over it
		t.checkException(new ConcurrentModificationException(), 
				bByLex, "build", randIter);
		
		//cannot remove from this iterator
		t.checkException(new UnsupportedOperationException(
				"Remove is not supported by this iterator."), 
					bByLexIter2, "remove");
		
		//empty the iterator
		while (bByLexIter2.hasNext()) {
			bByLexIter2.next();
		}
		
		//show that once the iterator is exhausted, we can 
		//add to the BTree once more
		t.checkExpect(bByLexIter2.hasNext(), false);
		t.checkExpect(bByLex.contains("chromatograms"), false);
		bByLex.build(randIter);
		t.checkExpect(bByLex.contains("chromatograms"), true);
	}
}