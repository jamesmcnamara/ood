package rbtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class TestTree<T> {
    StringIterator randIter = new StringIterator("random_order.txt");
    StringIterator lexIter = new StringIterator("lexicographically_ordered.txt");
    
    Comparator<String> byLex = new StringByLex();
    
    RBTree<String> randTree = new EmptyTree<String>(byLex);
    RBTree<String> lexTree = new EmptyTree<String>(byLex);
    
    RBTreeVisitor<String, ArrayList<Integer>> pathVisitor = new PathLengths<String>();
    RBTreeVisitor<String, ArrayList<Integer>> countVisitor = new CountNodes<String>();
    RBTreeVisitor<String, Integer> heightVisitor = new Height<String>();
    RBTreeVisitor<String, Integer> blackHeightVisitor = new BlackHeight<String>();
    
    RBTree<T> init(Iterable<T> iterable, RBTree<T> tree, int count) {
        Iterator<T> iter = iterable.iterator();
        while(iter.hasNext() && count > 0) {
            tree = tree.add(iter.next());
            count--;
        }
        return tree;
    }
    
    public static void main(String[] args) {
        TestTree<String> t = new TestTree<String>();
        t.randTree = t.init(t.randIter, t.randTree, 200);
        t.lexTree = t.init(t.lexIter, t.lexTree, 200);
        System.out.println("----------PathTest---------\n");
        System.out.println(t.randTree.accept(t.pathVisitor));
        System.out.println(t.randTree.height());
        System.out.println("\n----------CountNodes---------\n");
        System.out.println(t.randTree.accept(t.countVisitor));
        System.out.println(t.randTree.count());
        System.out.println("\n----------Height---------\n");
        System.out.println(t.randTree.accept(t.heightVisitor));
        System.out.println(t.randTree.height());
        System.out.println("\n----------BlackHeight---------\n");
        System.out.println(t.randTree.accept(t.blackHeightVisitor));
        System.out.println(t.randTree.minBlackCount());
    }
}