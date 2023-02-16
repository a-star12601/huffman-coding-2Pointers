package com.capillary.app.general;

public class Node {
    /**
     * Character in the node.
     */
    public String value;
    /**
     * frequency of the node.
     */
    public int freq;
    /**
     * left link.
     */
    public Node leftNode;
    /**
     * right link.
     */
    public Node rightNode;
    /**
     * Constructor for empty node.
     */
    public Node(){
        value ="\0";
        freq =0;
        leftNode =null;
        rightNode =null;
    }

    /**
     * Constructor for leaf node.
     *
     * @param c the character
     * @param f the frequency
     */
    public Node(String c,int f){
        value =c;
        freq =f;
        leftNode =null;
        rightNode =null;
    }

    /**
     * Constructor for an intermediate node.
     *
     * @param f     the frequency
     * @param left  the left link
     * @param right the right link
     * @param h     the height
     */
    public Node(Node left,Node right){
        freq = left.freq + right.freq;
        value = left.value + right.value;
        leftNode =left;
        rightNode =right;
    }
}
