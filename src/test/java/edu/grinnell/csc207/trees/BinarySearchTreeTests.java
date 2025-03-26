package edu.grinnell.csc207.trees;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTests {

    private BinarySearchTree<Integer> makeSampleTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.insert(5);
        tree.insert(0);
        tree.insert(7);
        tree.insert(11);
        tree.insert(6);
        return tree;
    }

    private BinarySearchTree<Integer> makeNoLeftTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.insert(5);
        tree.insert(7);
        tree.insert(11);
        tree.insert(16);
        return tree;
    }

    @Test
    public void emptyTreeTest() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertEquals(0, tree.size());
    }

    @Test
    public void basicSizeInsertTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
    }

    @Test
    public void basicToListInorderTest() {
        // N.B., need to upcast the Integer[] array to avoid a ClassCastException that
        // arises from downcasting the result of toArray to Integer[].
        assertArrayEquals((Object[]) new Integer[] {0, 5, 6, 7, 11},
                          makeSampleTree().toListInorder().toArray());
    }

    @Test
    public void noLeftToListInorderTest() {
        assertArrayEquals((Object[]) new Integer[] {5, 7, 11, 16},
                          makeNoLeftTree().toListInorder().toArray());
    }

    @Test
    public void basicToListPreorderTest() {
        assertArrayEquals((Object[]) new Integer[] {5, 0, 7, 6, 11},
                          makeSampleTree().toListPreorder().toArray());
    }

    @Test
    public void noLeftToListPreorderTest() {
        assertArrayEquals((Object[]) new Integer[] {5, 7, 11, 16},
                          makeNoLeftTree().toListPreorder().toArray());
    }

    @Test
    public void basicToListPostorderTest() {
        assertArrayEquals((Object[]) new Integer[] {0, 6, 11, 7, 5},
                          makeSampleTree().toListPostorder().toArray());
    }

    @Test
    public void noLeftToListPostorderTest() {
        assertArrayEquals((Object[]) new Integer[] {16, 11, 7, 5},
                          makeNoLeftTree().toListPostorder().toArray());
    }

    @Test
    public void basicContainsTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(true, tree.contains(5));
        assertEquals(false, tree.contains(8));
    }

    @Test
    public void basicToStringPreorderTest() {
        assertEquals("[5, 0, 7, 6, 11]", makeSampleTree().toStringPreorder());
    }

    @Test
    public void basicDeleteTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
        assertTrue(tree.contains(6));
        tree.delete(6);
        assertEquals(4, tree.size());
        assertFalse(tree.contains(6));
    }
}
