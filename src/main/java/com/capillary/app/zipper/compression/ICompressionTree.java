package com.capillary.app.zipper.compression;

import com.capillary.app.general.Node;

import java.util.Map;

/**
 * The interface for creating Compression tree.
 *
 * @param <T> the Generic type
 */
public interface ICompressionTree<T> {

    /**
     * Function to generate frequency map.
     *
     * @param arr the input byte array
     * @return the frequency map
     */
    Map<T,Integer> getFrequencyMap(byte[] arr) ;

    /**
     * Function to generate Huffman tree from frequency map.
     *
     * @param map the frequency map
     * @return the Final tree node
     */
    Node generateTree(Map<T,Integer> map);

    /**
     * Function to generate hashtable from huffman tree.
     *
     * @param tree the tree
     * @return the hashtable
     */
    Map<T,String> getHashTable(Node tree);
}
