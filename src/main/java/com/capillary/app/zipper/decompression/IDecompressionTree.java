package com.capillary.app.zipper.decompression;

import com.capillary.app.general.Node;

import java.util.Map;

/**
 * The interface for generating Decompression tree.
 *
 * @param <T> the Generic type
 */
public interface IDecompressionTree<T> {

    /**
     * Function to generate the Huffman tree.
     *
     * @param map the frequency map
     * @return the huffman tree
     */
    Node regenerateTree(Map<T,Integer> map);

}
