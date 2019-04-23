// Andrew Chuah
// I pledge my honor that I have abided by the Stevens Honor System.

package homework4;

import java.util.Random;

/**
 * A class to represent a treap, that is, a BST with node placement
 * randomized by probabilistic heap-like priorities
 * @author CS284 team
 */
public class Treap<E extends Comparable<E>> extends BinarySearchTree<E> {
    protected static class Node<E> {
        public E data; // key for the search
        public int priority; // random heap priority
        public Node<E> left;
        public Node<E> right;

        /** Creates a new node with the given data and priority. The
         *  pointers to child nodes are null. Throw exceptions if data
         *  is null.
         */
        public Node(E data, int priority) {
            if(data == null)
                throw new IllegalArgumentException("Data cannot be null in a node.");

            this.data = data;
            this.priority = priority;
            this.left = null;
            this.right = null;
        }

        public Node<E> rotateRight() {
            Node<E> node1 = this.left;
            Node<E> node2 = node1.right;
            node1.right = this;
            this.left = node2;
            return node1;
        }

        public Node<E> rotateLeft() {
            Node<E> node1 = this.right;
            Node<E> node2 = node1.left;
            node1.left = this;
            this.right = node2;
            return node1;
        }

        @Override
        public String toString(){
            return "(key=" + data + ", priority=" + priority + ")";
        }
    }

    private Random priorityGenerator;
    private Node<E> root;

    /** Create an empty treap. Initialize {@code priorityGenerator}
     * using {@code new Random()}. See {@url
     * http://docs.oracle.com/javase/8/docs/api/java/util/Random.html}
     * for more information regarding Java's pseudo-random number
     * generator. 
     */
    public Treap() {
        priorityGenerator = new Random();
        root = null;
    }


    /** Create an empty treap and initializes {@code
     * priorityGenerator} using {@code new Random(seed)}
     */
    public Treap(long seed) {
        priorityGenerator = new Random(seed);
        root = null;
    }

    public boolean add(E key) {
        if(find(key) == key)
            addReturn = false;

        else{
            int priority = priorityGenerator.nextInt();
            root = add(root, key, priority);
        }
        return addReturn;
    }

    /**
     * The below public method add is for testing purposes, and is not part of the main methods that needs to
     * be implemented. This function will take parameters key and priority, instead of generating
     * a random number for the priority, and adds the respective node to the treap.
     * @param key
     * @param priority
     * @return true if the node was added, false if it didn't
     */
    public boolean add(E key, int priority){
        if(find(key) == key)
            addReturn = false;

        else{
            root = add(root, key, priority);
        }
        return addReturn;
    }

    private Node<E> add(Node<E> localroot, E key, int priority) {
        if(localroot == null){
            addReturn = true;
            return new Node<E>(key, priority);
        }

        int compResult = key.compareTo(localroot.data);
        if(compResult < 0){
            localroot.left = add(localroot.left, key, priority);

            if(localroot.left.priority < localroot.priority)
                localroot = localroot.rotateRight();
        }
        else if(compResult > 0){
            localroot.right = add(localroot.right, key, priority);

            if(localroot.right.priority < localroot.priority)
                localroot = localroot.rotateLeft();
        }
        else //When item = localroot.data
            addReturn = false;

        return localroot;
    }

    public E delete(E key) {
        root = delete(root, key);
        return deleteReturn;
    }

    //Helper function for the public method delete(E key)
    private Node<E> delete(Node<E> localroot, E key){
        if(localroot == null){
            deleteReturn = null;
            return null;
        }

        int compResult = key.compareTo(localroot.data);
        if(compResult < 0)
            localroot.left = delete(localroot.left, key);

        else if(compResult > 0)
            localroot.right = delete(localroot.right, key);

        else{
            deleteReturn = localroot.data;
            if(localroot.left == null && localroot.right == null){
                return null;
            }
            else if(localroot.right == null){
                localroot = localroot.rotateRight();
                localroot.right = delete(localroot.right, key);
            }
            else if(localroot.left == null){
                localroot = localroot.rotateLeft();
                localroot.left = delete(localroot.left, key);
            }
            else{
                if(localroot.left.priority < localroot.right.priority){
                    localroot = localroot.rotateRight();
                    localroot.left = delete(localroot.left, key);
                }
                else{
                    localroot = localroot.rotateLeft();
                    localroot.right = delete(localroot.right, key);
                }
            }
        }
        return localroot;
    }

    private E find(Node<E> root, E key) {
        if(root == null)
            return null;

        int compResult = key.compareTo(root.data);

        if(compResult == 0)
            return root.data;

        else if(compResult > 0)
            return find(root.right, key);

        else
            return find(root.left, key);
    }

    public E find(E key) {
        return find(root, key);
    }

    @Override
    public String toString() {
        return toString(root, 0);
    }

    // Helper method for the toString() method
    private String toString(Node<E> node, int height){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < height; i++){
            sb.append("  ");
        }

        if(node == null)
            sb.append("null");

        else{
            sb.append(node.toString());
            sb.append("\n");
            sb.append(toString(node.left, height + 1));
            sb.append("\n");
            sb.append(toString(node.right, height + 1));
        }
        return sb.toString();
    }
}
