package com.practice.nilesh.tddexamples;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Integer.max;

class Node{
    Node left, right;
    int data;
    Node(int data)
    {
        this.data=data;
        left=right=null;
    }

}
public class BST {
    Node node;
    public BST(Node node){
        this.node=node;
    }

    /*public static void main(String[] args) {
        BST bst= new BST();
        Node root=null;
        root= bst.insert(root,10);
        root= bst.insert(root,8);
        root= bst.insert(root,9);
        root= bst.insert(root,7);
        root= bst.insert(root,12);
        root= bst.insert(root,11);
        System.out.println("--------------");
        bst.printInorder(root);
        System.out.println("--------------");
        bst.printPreorder(root);
        System.out.println("--------------");
        bst.printPostorder(root);
    }*/

    public void printPreorder(Node root)
    {
        if(root!=null)
        {
            System.out.println(root.data);
            printPreorder(root.left);
            printPreorder(root.right);
        }
    }
    public void printInorder(Node root)
    {
        if(root!=null)
        {
            printInorder(root.left);
            System.out.println(root.data);
            printInorder(root.right);
        }
    }
    public void printPostorder(Node root)
    {
        if(root!=null)
        {
            printPostorder(root.left);
            printPostorder(root.right);
            System.out.println(root.data);
        }
    }

    public Node insert(Node root, int data)
    {
        if(root==null)
        {
            Node node= new Node(data);
            return node;
        }
        else if (data<=root.data)
        {
            root.left=insert(root.left,data);
        }
        else
        {
            root.right=insert(root.right,data);
        }
        return root;
    }

    public Node insertCharacters(Node root, char data)
    {
        if(root==null)
        {
            Node node= new Node(data);
            return node;
        }
        else if (data<=root.data)
        {
            root.left=insert(root.left,data);
        }
        else
        {
            root.right=insert(root.right,data);
        }
        return root;
    }

    public void levelOrderTraversal(Node root)
    {
        if(root==null)
            return;
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            Node current=
                    queue.peek();
            queue.poll();
            System.out.println(current.data);
            if(current.left!=null)
                queue.add(current.left);
            if(current.right!=null)
                    queue.add(current.right);
        }
    }

    public String search(Node root, int data)
    {
        if(root==null)
        {
            return "Data Not Found";
        }
        else if(data == root.data)
            return "Data Found: "+data;
        else if (data <= root.data)
            return search(root.left,data);
        else
            return search(root.right,data);
    }

    public int minRecursive(Node root)
    {
        if(root==null)
            return 0;
        else if(root.left==null)
            return root.data;
        else
            return minRecursive(root.left);
    }

    public int minIterative(Node root)
    {
        Node current=root;
        if(current==null)
            return 0;
        while(current.left!=null)
        {
            current=current.left;
        }
        return current.data;
    }

    public int getHeight(Node root)
    {
        if(root==null)
            return 0;
        int leftHeight=getHeight(root.left);
        int righHeight=getHeight(root.right);
        return max(leftHeight, righHeight)+1;
    }
}
