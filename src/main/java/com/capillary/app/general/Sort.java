package com.capillary.app.general;

import java.util.Comparator;

/**
 * Comparator to sort the tree nodes.
 */
public class Sort implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y){
        if(x.freq != y.freq)
            return x.freq - y.freq;
        return (x.value).compareTo(y.value);
    }
}