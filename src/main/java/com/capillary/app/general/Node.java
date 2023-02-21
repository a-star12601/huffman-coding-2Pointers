package com.capillary.app.general;

/**
 * Type Node to store tree contents.
 */
public class Node {
    /**
     * Value of Character/String.
     */
    public String value;
    /**
     * frequency of the character/string.
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
     * @param left  the left link
     * @param right the right link
     */
    public Node(Node left,Node right){
        freq = left.freq + right.freq;
        value = left.value + right.value;
        leftNode =left;
        rightNode =right;
    }
}
