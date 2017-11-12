package lesson3;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    private List<Integer> list = Arrays.asList(10, 5, 7, 3, 4, 1, 8, 15, 20, 14);
    private List<Integer> list1 = Arrays.asList(12, 91, 76, 6, 41, 70, 64, 14, 37, 98, 59, 40, 67, 95, 33);
    private List<Integer> list2 = Arrays.asList(2, 42, 79, 55, 51, 68, 90, 60, 54, 18, 65, 27, 81, 87, 64, 32, 25, 95, 66, 90);
    private List<Integer> test1 = new ArrayList<>();
    private List<Integer> test2 = new ArrayList<>();

    @Test
    public void add() {
        BinaryTree<Integer> tree1 = new BinaryTree<>();
        tree1.addAll(list1);
        Iterator<Integer> itt1 = tree1.iterator();
        itt1.forEachRemaining(test1::add);
        Collections.sort(list1);
        assertEquals(list1, test1);
        tree1.remove(12);
        assertTrue(tree1.checkInvariant());
        tree1.remove(91);
        assertTrue(tree1.checkInvariant());
        assertTrue(tree1.contains(37));

        BinaryTree<Integer> tree2 = new BinaryTree<>();
        tree2.addAll(list2);
        tree2.remove(79);
        assertTrue(tree2.checkInvariant());
        Iterator<Integer> itt2 = tree2.iterator();
        while (itt2.hasNext()) {
            System.out.println(itt2.next());
            itt2.remove();
        }

        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.addAll(list);
        Iterator<Integer> itt = tree.iterator();
        while (itt.hasNext()) {
            System.out.println(itt.next());
            itt.remove();
        }
    }
}

