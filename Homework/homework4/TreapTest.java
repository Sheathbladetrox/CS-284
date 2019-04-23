// Andrew Chuah
// I pledge my honor that I have abided by the Stevens Honor System.

package homework4;

import static org.junit.Assert.*;
import org.junit.Test;

public class TreapTest {
    Treap<Integer> TreeTest = new Treap<>();
    Treap<Integer> TreeSeedTest = new Treap<>(100);
    Treap<Integer> TreeTest2 = new Treap<>();

    @Test
    public void TreeTest() {
        System.out.println("This is the TreeTest treap test!\n");
        assertEquals(true, TreeTest.add(4));
        assertEquals(true, TreeTest.add(2));
        assertEquals(true, TreeTest.add(6));
        assertEquals(true, TreeTest.add(1));
        assertEquals(true, TreeTest.add(3));
        assertEquals(true, TreeTest.add(5));
        assertEquals(true, TreeTest.add(7));

        //Confirm that every node added is correctly placed within the tree
        System.out.println(TreeTest.toString());

        assertEquals((long)7, (long)TreeTest.find(7)); //Tests find() on key that is in the treap
        assertEquals(null, TreeTest.find(32)); //Tests find() on nonexistent key
        assertEquals(null, TreeTest.delete(32)); //Tests that delete returns null if the key cannot be deleted.
        assertEquals((long)7, (long)TreeTest.delete(7)); //Returns key if it successfully deletes the node

        //Check to see that the node with key 7 is deleted
        System.out.println(TreeTest.toString());

        assertEquals((long)1, (long)TreeTest.delete(1));

        //Check to see that the node with key 1 is deleted
        System.out.println(TreeTest.toString());
        System.out.println("\n");
    }

    @Test
    public void TreeSeedTest(){
        System.out.println("This is the TreeSeedTest treap test!\n");
        assertEquals(true, TreeSeedTest.add(1));
        assertEquals(true, TreeSeedTest.add(2));
        assertEquals(true, TreeSeedTest.add(3));
        assertEquals(true, TreeSeedTest.add(4));
        assertEquals(true, TreeSeedTest.add(5));
        assertEquals(true, TreeSeedTest.add(6));
        assertEquals(true, TreeSeedTest.add(7));

        //Confirm that every node added is correctly placed within the tree
        System.out.println(TreeSeedTest.toString());

        assertEquals((long)7, (long)TreeSeedTest.find(7)); //Tests find() on key that is in the treap
        assertEquals(null, TreeSeedTest.find(32)); //Tests find() on nonexistent key
        assertEquals(null, TreeSeedTest.delete(32)); //Tests that delete returns null if the key cannot be deleted.
        assertEquals((long)7, (long)TreeSeedTest.delete(7)); //Returns key if it successfully deletes the node

        //Check to see that the node with key 7 is deleted
        System.out.println(TreeSeedTest.toString());

        assertEquals((long)1, (long)TreeSeedTest.delete(1));

        //Check to see that the node with key 1 is deleted
        System.out.println(TreeSeedTest.toString());
        System.out.println("\n");
    }

    @Test
    public void TreeTest2(){
        /*
        This test will emulate the example in the PDF.
        Since the priorities are manually inputted as parameters, we can test the toString() method
        to see how exactly our treap fell into place after adding the nodes.
         */
        System.out.println("This is the TreeTest2 treap test!\n");
        assertEquals(true, TreeTest2.add(4, 81));
        assertEquals(true, TreeTest2.add(2, 69));
        assertEquals(true, TreeTest2.add(6, 30));
        assertEquals(true, TreeTest2.add(1, 16));
        assertEquals(true, TreeTest2.add(3, 88));
        assertEquals(true, TreeTest2.add(5, 17));
        assertEquals(true, TreeTest2.add(7, 74));

        System.out.println(TreeTest2.toString());

        //Tests if toString() method outputted correctly
        assertEquals("(key=1, priority=16)\n" +
                        "  null\n" +
                        "  (key=5, priority=17)\n" +
                        "    (key=2, priority=69)\n" +
                        "      null\n" +
                        "      (key=4, priority=81)\n" +
                        "        (key=3, priority=88)\n" +
                        "          null\n" +
                        "          null\n" +
                        "        null\n" +
                        "    (key=6, priority=30)\n" +
                        "      null\n" +
                        "      (key=7, priority=74)\n" +
                        "        null\n" +
                        "        null",
                TreeTest2.toString());

        assertEquals((long)7, (long)TreeTest2.find(7)); //Tests find() on key that is in the treap
        assertEquals(null, TreeTest2.find(32)); //Tests find() on nonexistent key
        assertEquals(null, TreeTest2.delete(32)); //Tests that delete returns null if the key cannot be deleted.
        assertEquals((long)7, (long)TreeTest2.delete(7)); //Returns key if it successfully deletes the node4

        System.out.println(TreeTest2.toString());

        //Tests if toString() method outputted correctly after deleting key 7
        assertEquals("(key=1, priority=16)\n" +
                        "  null\n" +
                        "  (key=5, priority=17)\n" +
                        "    (key=2, priority=69)\n" +
                        "      null\n" +
                        "      (key=4, priority=81)\n" +
                        "        (key=3, priority=88)\n" +
                        "          null\n" +
                        "          null\n" +
                        "        null\n" +
                        "    (key=6, priority=30)\n" +
                        "      null\n" +
                        "      null",
                TreeTest2.toString());

        //Tests if root deletion was successful and treap is reconfigured correctly
        assertEquals((long)1, (long)TreeTest2.delete(1));

        System.out.println(TreeTest2.toString());

        //Tests if toString() method outputted correctly after deleting key 1
        assertEquals("(key=5, priority=17)\n" +
                        "  (key=2, priority=69)\n" +
                        "    null\n" +
                        "    (key=4, priority=81)\n" +
                        "      (key=3, priority=88)\n" +
                        "        null\n" +
                        "        null\n" +
                        "      null\n" +
                        "  (key=6, priority=30)\n" +
                        "    null\n" +
                        "    null",
                TreeTest2.toString());

        System.out.println("\n");
    }
}
