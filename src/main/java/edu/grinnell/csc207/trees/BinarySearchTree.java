package edu.grinnell.csc207.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() { }

    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in the tree
     */
    public int size() {
        return sizeH(root);
    }

    private Node<T> insertH(T value, Node<T> root) {
        if (root == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(root.value) < 0) {
                root.left = insertH(value, root.left);
            } else {
                root.right = insertH(value, root.right);
            }
            return root;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertH(value, root);
    }

    ///// Part 1: Traversals

    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> elements = new ArrayList<T>();
        if (root == null) {
            return elements;
        }
        return toListInorderH(root, elements);
    }

    public List<T> toListInorderH(Node<T> root, List<T> elements) {
        if (root.left != null) {
            toListInorderH(root.left, elements);
        }
        elements.add(root.value);
        if (root.right != null) {
            toListInorderH(root.right, elements);
        }
        return elements;
    } 

    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> elements = new ArrayList<T>();
        if (root == null) {
            return elements;
        }
        return toListPreorderH(root, elements);
    }

    public List<T> toListPreorderH(Node<T> root, List<T> elements) {
        elements.add(root.value);
        if (root.left != null) {
            toListPreorderH(root.left, elements);
        }
        if (root.right != null) {
            toListPreorderH(root.right, elements);
        }
        return elements;
    } 

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> elements = new ArrayList<T>();
        if (root == null) {
            return elements;
        }
        return toListPostorderH(root, elements);
    }

    public List<T> toListPostorderH(Node<T> root, List<T> elements) {
        if (root.left != null) {
            toListPostorderH(root.left, elements);
        }
        if (root.right != null) {
            toListPostorderH(root.right, elements);
        }
        elements.add(root.value);
        return elements;
    } 

    ///// Part 2: Contains

    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsH(value, root);
    }

    private boolean containsH(T value, Node<T> root) {
        if (root == null) {
            return false;
        }
        int comparison = root.value.compareTo(value);
        if (comparison == 0) {
            return true;
        }
        if (comparison < 0) {
            return containsH(value, root.left);
        } else {
            return containsH(value, root.right);
        }
    }

    ///// Part 3: Pretty Printing

    /**
     * @return a string representation of the tree obtained via an pre-order traversal in the
     *         form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        List<T> elements = toListPreorder();
        StringBuffer buf = new StringBuffer("[");
        T object = elements.get(0);
        buf.append(object.toString());
        for (int i = 1; i < elements.size(); i++) {
            object = elements.get(i);
            buf.append(", ");
            buf.append(object.toString());
        }
        buf.append("]");
        return buf.toString();
    }

    ///// Part 4: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. The left subtree doesn't have any more children so we make the left subtree the new root
     * 2. The right subtree doesn't have any more children so we make the right subtree the new root
     * 3. Both subtrees have children so we can replace the root with the rightmost value in the left subtree (leftmost value in the right subtree)
     */

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (!contains(value)) {
            throw new IllegalArgumentException();
        }
        
        Node<T> del = findNode(value, root, root.left);
        if (del == null) {
            del = findNode(value, root, root.right);
        }
        if (del == null) {
            root = null;
        }
        if (sizeH(del.left) == 1) {
            del.left = null;
        } else if (sizeH(del.right) == 1) {
            del.right = null;
        }

        deleteH(del);
    }

    private void deleteH(Node<T> root) {
        if (sizeH(root.left) == 1) {
            root.value = root.left.value;
            root.left = null;
        } else if (sizeH(root.right) == 1) {
            root.value = root.right.value;
            root.right = null;
        } else {
            Node<T> repl = findRightmost(root.left);
            root.value = repl.value;
            deleteH(repl);
        }
    }

    private Node<T> findRightmost (Node<T> root) {
        if (root.right != null) {
            return findRightmost(root.right);
        } else {
            return root;
        }
    }
    private Node<T> findNode(T value, Node<T> parent, Node<T> cur) {
        if (cur == null) {
            return null;
        }
        int comparison = cur.value.compareTo(value);
        if (comparison == 0) {
            return parent;
        }
        if (comparison < 0) {
            return findNode(value, cur, cur.left);
        } else {
            return findNode(value, cur, cur.right);
        }
    }
}
