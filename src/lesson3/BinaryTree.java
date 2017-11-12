package lesson3;

import org.jetbrains.annotations.NotNull;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;
    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        T key = (T) o;
        int oldSize = size;
        Node<T> parent = null;
        if (root.value == key) {
            if (root.left == null && root.right == null) root = null;
            else if (root.left != null && root.right == null) root = root.left;
            else if (root.left == null && root.right != null) root = root.right;
            else {
                if (root.right.left == null) {
                    root.right.left = root.left;
                    root = root.right;
                } else {
                    Node n = searchToRemove(root.right);
                    Node t = root;
                    root = n.left;
                    n.left = n.left.right;
                    root.left = t.left;
                    root.right = t.right;
                }
            }
            size--;
        } else remove(root, key, parent);
        return (size < oldSize);
    }

    private void remove(Node<T> t, T key, Node<T> parent) {
        if (t == null) return;
        if (t.value != key) parent = t;
        if (key.compareTo(t.value) < 0) remove(t.left, key, parent);
        if (key.compareTo(t.value) > 0) remove(t.right, key, parent);
        if (t.value == key) {
            size--;
            if (t.right == null && t.left == null) {         //нет потомков
                if (t == parent.right) parent.right = null;
                else parent.left = null;
            } else if (t.left == null && t.right != null) {  //только правый
                if (t == parent.right) parent.right = t.right;
                else parent.left = t.right;
            } else if (t.right == null && t.left != null) {//только левый
                if (t == parent.right) parent.right = t.left;
                else parent.left = t.left;
            } else {                                          //оба
                if (t.right.left == null) {                   // проверяем сразу, мб правый потомок-найменьший
                    t.right.left = t.left;
                    if (t == parent.right) parent.right = t.right;
                    else parent.left = t.right;
                } else {
                    Node n = searchToRemove(t.right);    //родитель элемента который будем переставлять/поднимать
                    if (t == parent.right) parent.right = n.left;
                    else parent.left = n.left;
                    n.left = n.left.right;
                    if (t.value.compareTo(parent.value) > 0) {
                        parent.right.right = t.right;
                        parent.right.left = t.left;
                    } else {
                        parent.left.right = t.right;
                        parent.left.left = t.left;
                    }
                }
            }
        }
    }

    private Node searchToRemove(Node t) {
        if (t.left.left == null) return t;
        else return searchToRemove(t.left);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private Node<T> myNext;
        private Stack<Node<T>> stack;
        Node<T> found;
        int sizeForItertor = size;

        private BinaryTreeIterator() {
            stack = new Stack<Node<T>>();
            myNext = min(root);
        }

        private Node<T> findNext() {
            if (sizeForItertor == 0) {
                return null;
            } else {
                found = myNext;
                if (sizeForItertor == 1) myNext = null;
                else if (found.right != null) {
                    myNext = min(found.right);
                } else myNext = back();
                sizeForItertor--;
                return found;
            }
        }

        private Node<T> min(Node<T> t) {
            while (t.left != null) {
                stack.push(t);
                t = t.left;
            }
            stack.push(t);
            return t;
        }

        private Node<T> back() {
            Node<T> last = stack.pop();
            if (stack.peek().left != null && stack.peek().left == last)
                return stack.peek();
            else return back();
        }


        @Override
        public boolean hasNext() {
            return myNext != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            BinaryTree.this.remove(found.value);
            if (found.right == null) return;
            else {
                Stack<Node<T>> partOfStack = new Stack<Node<T>>();
                Node a = stack.pop();
                while (found != stack.peek()) {
                    partOfStack.add(stack.pop());
                }
                stack.pop();
                if(stack.isEmpty() || found.left==null){
                    while (!partOfStack.isEmpty()) {
                        stack.push(partOfStack.pop());
                    }
                    stack.push(a);
                }else {
                    stack.push(a);
                    while (!partOfStack.isEmpty()) {
                        stack.push(partOfStack.pop());
                    }
                }
            }
        }
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }

    public void prn() {
        Queue<Node> q1 = new LinkedList<Node>();

        q1.add(root);
        prn(q1);
    }

    private void prn(Queue<Node> q1) {
        Queue<Node> q2 = new LinkedList<Node>();
        for (Node n : q1) {
            if (n != null) System.out.print(n.value + " ");
            else System.out.print("N  ");
        }
        System.out.println();
        while (!q1.isEmpty()) {
            Node node = q1.poll();
            if (node != null && node.left != null) q2.add(node.left);
            else q2.add(null);
            if (node != null && node.right != null) q2.add(node.right);
            else q2.add(null);
        }
        q1 = q2;
        int count = 0;
        for (Node n : q2) {
            if (n != null) {
                count++;
                break;
            }

        }
        if (count != 0) prn(q1);
        else System.out.println();

    }
}