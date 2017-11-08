package lesson3;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void add() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(7);
        tree.add(10);
        assertEquals(3, tree.size());
        assertTrue(tree.contains(5));
        tree.add(3);
        tree.add(1);
        tree.add(3);
        tree.add(4);
        assertEquals(6, tree.size());
        assertFalse(tree.contains(8));
        tree.add(8);
        tree.add(15);
        tree.add(15);
        tree.add(20);
        tree.add(14);
        assertEquals(10, tree.size());
        assertTrue(tree.checkInvariant());
        assertTrue(tree.contains(5));
        assertTrue(tree.remove(5));
        assertFalse(tree.contains(5));
        assertEquals(9,tree.size());
        assertFalse(tree.remove(5));
        assertTrue(tree.checkInvariant());
        tree.remove(7);
        tree.remove(8);
        assertFalse(tree.remove(123));
        assertEquals(7,tree.size());
        assertTrue(tree.remove(10));
        assertTrue(tree.checkInvariant());
        tree.prn();
    }

}