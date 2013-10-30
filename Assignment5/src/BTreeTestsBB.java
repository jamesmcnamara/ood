import java.util.*;
import tester.*;

public class BTreeTestsBB {
	Comparator<String> stringByLex = new StringByLex();
	Comparator<String> stringByLen = new StringByLength();
	
	BTree bByLex = BTree.binTree(stringByLex);
	BTree bByLex2 = BTree.binTree(stringByLex);
	BTree bByLen = BTree.binTree(stringByLen);
	
	ArrayList<String> lexList = new ArrayList<String>();
	ArrayList<String> randList = new ArrayList<String>();
	
	StringIterator lexIter = new StringIterator("lexicographically_ordered.txt");
	StringIterator lexIter2 = new StringIterator("lexicographically_ordered.txt");
	StringIterator randIter = new StringIterator("random_order.txt");
	StringIterator randIter2 = new StringIterator("random_order.txt");
	public void init() {
		fillList(lexList,  lexIter , 200);
		fillList(randList, randIter, 200);
	}
	
	public void reset() {
		bByLex = BTree.binTree(stringByLex);		
		bByLex2 = BTree.binTree(stringByLex);
		bByLen = BTree.binTree(stringByLen);
		
		lexList.clear();
		randList.clear();
		
		lexIter = new StringIterator("lexicographically_ordered.txt");
		randIter = new StringIterator("random_order.txt");
		lexIter2 = new StringIterator("lexicographically_ordered.txt");
		randIter2 = new StringIterator("random_order.txt");
	}
	
	private static void fillList(ArrayList<String> l, 
			Iterator<String> iter, int n) {
		while (iter.hasNext() && n != 0) {
			l.add(iter.next());
			n = n - 1;
		}
	}
	
	public void testBinTree(Tester t) {
		BTree b = BTree.binTree(stringByLex);
		t.checkExpect(b instanceof BTree, true);
		
	}
	
	public void testBuild(Tester t) {
		reset();
		t.checkExpect(bByLex.contains("aardvark"), false);
		t.checkExpect(bByLen.contains("aardvarks"), false);
		bByLex.build(lexIter);
		bByLen.build(randIter);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLen.contains("aardvarks"), true);
		t.checkExpect(bByLex.contains("zathura"), false);
	}
	
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
	
	public void testContains(Tester t) {
		reset();
		t.checkExpect(bByLen.contains("Hello"), false);
		t.checkExpect(bByLen.contains("Goodbye"), false);
		bByLen.build(new ArrayList<String>(Arrays.asList("Hello")));
		t.checkExpect(bByLen.contains("Hello"), true);
		t.checkExpect(bByLen.contains("Goodbye"), false);
	}
	
	public void testToString(Tester t) {
		reset();
		
		t.checkExpect(bByLex.toString(), "");
		bByLex.build(new ArrayList<String>(Arrays.asList("Hi", "Aloha", 
				"Bonjour", "Wilkommen", "Jambo", "Bienvenido", "Nieho")));
		t.checkExpect(bByLex.toString(),
				"Aloha, Bienvenido, Bonjour, Hi, Jambo, Nieho, Wilkommen");
		
		
		t.checkExpect(bByLen.toString(), "");
		bByLen.build(new ArrayList<String>(
				Arrays.asList("Hello", "Aloha", "Goodbye")));
		
		//duplicates are ignored
		t.checkExpect(bByLen.toString(), "Hello, Goodbye");
	}
	
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
		bByLex.build(arList);
		bByLex2.build(arListBackwards);
		
		//BTrees with the same data but different structure are different
		t.checkExpect(bByLex.equals(bByLex2), false);
		
		//However BTrees with the same data have the same string representation
		t.checkExpect(bByLex.toString().equals(bByLex2.toString()), true);
	}
	
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