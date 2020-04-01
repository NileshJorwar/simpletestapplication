package com.practice.nilesh.tddexamples;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BSTTest {


    BST bst;
    Node root;

    @BeforeAll
    public void contextLoads()
    {
        root=mock(Node.class);
        bst=new BST(root);
    }

    @Test
    public void test_insert()
    {
        Node newNode = null;
        newNode= bst.insert(newNode,10);
        newNode= bst.insert(newNode,8);
        newNode= bst.insert(newNode,9);
        newNode= bst.insert(newNode,7);
        newNode= bst.insert(newNode,12);
        newNode= bst.insert(newNode,11);
        newNode= bst.insert(newNode,13);
//        verify(bst).insert(any(),anyInt());
        assertEquals(newNode.data,10);
        assertEquals(newNode.left.data,8);
        assertEquals(newNode.left.right.data,9);
    }

    @Test
    public void test_search()
    {
        Node newNode = null;
        newNode= bst.insert(newNode,10);
        newNode= bst.insert(newNode,8);
        newNode= bst.insert(newNode,9);
        newNode= bst.insert(newNode,7);
        newNode= bst.insert(newNode,12);
        newNode= bst.insert(newNode,11);
        newNode= bst.insert(newNode,13);

        String result=
                bst.search(newNode,8);
        assertEquals(result,"Data Found: 8");

        String result2=
                bst.search(newNode,34);
        assertEquals(result2,"Data Not Found");

    }

    @Test
    public void test_min()
    {
        Node newNode = null;
        newNode= bst.insert(newNode,10);
        newNode= bst.insert(newNode,8);
        newNode= bst.insert(newNode,9);
        newNode= bst.insert(newNode,7);
        newNode= bst.insert(newNode,12);
        newNode= bst.insert(newNode,11);
        newNode= bst.insert(newNode,13);

        int min= bst.minRecursive(newNode);
        assertEquals(min,7);


    }

    @Test
    public void test_minIterative()
    {
        Node newNode = null;
        newNode= bst.insert(newNode,10);
        newNode= bst.insert(newNode,8);
        newNode= bst.insert(newNode,9);
        newNode= bst.insert(newNode,7);
        newNode= bst.insert(newNode,12);
        newNode= bst.insert(newNode,11);
        newNode= bst.insert(newNode,13);

        int min= bst.minIterative(newNode);
        assertEquals(min,7);

    }

    @Test
    public void test_treeHeight()
    {
        Node newNode = null;
        newNode= bst.insert(newNode,10);
        newNode= bst.insert(newNode,8);
        newNode= bst.insert(newNode,9);
        newNode= bst.insert(newNode,7);
        newNode= bst.insert(newNode,12);
        newNode= bst.insert(newNode,11);
        newNode= bst.insert(newNode,13);
        newNode= bst.insert(newNode,4);
        int height= bst.getHeight(newNode);
        assertEquals(height,4);
    }

    @Test
    public void test_insertCharacters()
    {
        Node newNode = null;
        newNode= bst.insertCharacters(newNode,'M');
        newNode= bst.insertCharacters(newNode,'A');
        newNode= bst.insertCharacters(newNode,'N');
        newNode= bst.insertCharacters(newNode,'O');
        newNode= bst.insertCharacters(newNode,'C');
        newNode= bst.insertCharacters(newNode,'Q');
        newNode= bst.insertCharacters(newNode,'B');
        newNode= bst.insertCharacters(newNode,'K');

        verifyNoInteractions(root);

        assertEquals(newNode.data,'M');
        assertEquals(newNode.left.data,'A');
        assertEquals(newNode.right.data,'N');
        assertEquals(newNode.right.right.data,'O');
    }

    @Test
    public void levelOrderTraversal()
    {
        Node newNode = null;
        newNode= bst.insertCharacters(newNode,'M');
        newNode= bst.insertCharacters(newNode,'A');
        newNode= bst.insertCharacters(newNode,'N');
        newNode= bst.insertCharacters(newNode,'O');
        newNode= bst.insertCharacters(newNode,'C');
        newNode= bst.insertCharacters(newNode,'Q');
        newNode= bst.insertCharacters(newNode,'B');
        newNode= bst.insertCharacters(newNode,'K');
        bst.levelOrderTraversal(newNode);
    }

}