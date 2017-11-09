package lesson3;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    private List<Integer> list = Arrays.asList(10, 5, 7, 3, 1, 4,8, 15, 20, 14);
    @Test
    public void add() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.addAll(list);
        assertEquals(10, tree.size());
        assertTrue(tree.checkInvariant());

        assertTrue(tree.contains(5));
        assertTrue(tree.remove(5));
        assertFalse(tree.contains(5));
        assertEquals(9, tree.size());
        assertFalse(tree.remove(5));
        assertTrue(tree.checkInvariant());

        tree.remove(7);
        tree.remove(8);
        assertFalse(tree.remove(123));
        assertEquals(7, tree.size());
        assertTrue(tree.remove(10));
        assertTrue(tree.checkInvariant());
        assertEquals(6, tree.size());

        Iterator<Integer> itt = tree.iterator();
        itt.next();
        itt.next(); //достаём  3
        itt.remove(); //удаляю её
        assertFalse(tree.contains(3));
        itt.next();
        assertTrue(itt.hasNext());
        itt.next();
        itt.next();
        itt.next();// последний элемент
        assertFalse(itt.hasNext());
    }

}