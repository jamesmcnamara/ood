import java.util.*;
import tester.*;

public class BTreeTestsBB {
	Comparator<String> stringByLex = new StringByLex();
	Comparator<String> stringByLen = new StringByLength();
	BTree bByLex = BTree.binTree(stringByLex);
	BTree bByLen = BTree.binTree(stringByLen);
	ArrayList<String> lexList = new ArrayList<String>();
	ArrayList<String> randList = new ArrayList<String>();
	StringIterator lex = new StringIterator("lexicographically_ordered.txt");
	StringIterator rand = new StringIterator("random_order.txt");
	
	public void init() {
		fillList(lexList, lex, 200);
		fillList(randList, rand, 200);
	}
	
	public void reset() {
		bByLex = BTree.binTree(stringByLex);
		bByLen = BTree.binTree(stringByLen);
		lexList.clear();
		randList.clear();
		lex = new StringIterator("lexicographically_ordered.txt");
		rand = new StringIterator("random_order.txt");
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
		bByLex.build(lex);
		bByLen.build(rand);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLen.contains("aardvarks"), true);
		t.checkExpect(bByLex.contains("zathura"), false);
	}
	
	public void testBuildNumStrings(Tester t) {
		reset();
		
		//Test with a small number to see that only some strings are added
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lex, 10);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), false);
		
		//Test with more strings, to show that increasing the number
		//increases the count
		reset();
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lex,100);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), true);
		
		//test with a negative number to show that all strings are added
		reset();
		t.checkExpect(bByLex.contains("abberations"), false);
		t.checkExpect(bByLex.contains("aardvark"), false);
		bByLex.build(lex,-1);
		t.checkExpect(bByLex.contains("aardvark"), true);
		t.checkExpect(bByLex.contains("abberations"), true);
	}
	
	public void testContains(Tester t) {
		reset();
		t.checkExpect(bByLen.contains("Hello"), false);
		t.checkExpect(bByLen.contains("Aloha"), false);
		t.checkExpect(bByLen.contains("Goodbye"), false);
		bByLen.build(new ArrayList<String>(Arrays.asList("Hello")));
		t.checkExpect(bByLen.contains("Hello"), true);
		t.checkExpect(bByLen.contains("Aloha"), true);
		t.checkExpect(bByLen.contains("Goodbye"), false);
	}
}