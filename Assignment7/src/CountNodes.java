

import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

import rbtree.RBTree;
import rbtree.RBTreeVisitor;

public class CountNodes<T> implements RBTreeVisitor<T, ArrayList<Integer>> {

    ArrayList<Integer> stats = new ArrayList<Integer>(Arrays.asList(0,0,0));
    
    public ArrayList<Integer> visitEmpty(Comparator<T> comp, String color) {
        return stats;
    }

    public ArrayList<Integer> visitNode(Comparator<T> comp, String color,
            T data, RBTree<T> left, RBTree<T> right) {
        stats.set(0, stats.get(0) + 1);
        if (color == "BLACK") {
            stats.set(1, stats.get(1) + 1);
        }
        else {
            stats.set(2, stats.get(2) + 1);
        }
        left.accept(this);
        right.accept(this);
        return stats;
    }

    
    
}