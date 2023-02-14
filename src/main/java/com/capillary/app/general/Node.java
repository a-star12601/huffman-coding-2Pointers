package com.capillary.app.general;

public class Node {
    /**
     * Character in the node.
     */
    public String Char;
    /**
     * frequency of the node.
     */
    public int Freq;
    /**
     * left link.
     */
    public Node Left;
    /**
     * right link.
     */
    public Node Right;
    /**
     * Current height of node.
     */
    public int Height;

    /**
     * Constructor for empty node.
     */
    public Node(){
        Char="\0";
        Freq=0;
        Left=null;
        Right=null;
        Height=0;
    }

    /**
     * Constructor for leaf node.
     *
     * @param c the character
     * @param f the frequency
     */
    public Node(String c,int f){
        Char=c+"";
        Freq=f;
        Left=null;
        Right=null;
//        Height=0;
    }

    /**
     * Constructor for an intermediate node.
     *
     * @param f     the frequency
     * @param left  the left link
     * @param right the right link
     * @param h     the height
     */
    public Node(Node left,Node right,int h){
        Freq= left.Freq+ right.Freq;
        Char= left.Char+ right.Char;
        Left=left;
        Right=right;
//        Height=h;
    }
}
