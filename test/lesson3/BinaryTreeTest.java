package lesson3;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    private List<Integer> list = Arrays.asList(178, 336, 184, 640, 894, 921, 99, 825, 728, 108);
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
        tree.prn();
        tree.remove(825);
        tree.prn();
        Iterator<Integer> itt = tree.iterator();
        while (itt.hasNext()) {
            System.out.println(itt.next());
            itt.remove();
        }

    }

    @Test
    public void veryBigTest() {
        for (int i = 0; i < 100; i++) {
            assertTrue(forVeryBigTest());
        }
    }

    private boolean forVeryBigTest() {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
//Заполняем дерево 50 случайными числами от 0 до 1000
        while (binaryTree.size() != 50) {
            int next = random.nextInt(1000);
            if (binaryTree.add(next)) list.add(next);
        }
        //Удаляем из дерева случайное число
        int removeIt = list.get(random.nextInt(50));
        assertTrue(binaryTree.remove(removeIt));
        Iterator<Integer> iterator = binaryTree.iterator();
        int x = -1;
        try {
            while (iterator.hasNext()) {
                x = iterator.next();
            }
        } catch (Exception e) {
            System.out.println(list);
            System.out.println("Последний элемент до ошибки: " + x);
            System.out.println("Пытались удалить: " + removeIt);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

