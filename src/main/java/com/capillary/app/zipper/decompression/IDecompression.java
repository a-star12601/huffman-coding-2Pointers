package com.capillary.app.zipper.decompression;

import com.capillary.app.general.Node;

import java.util.Map;

/**
 * The interface for performing Decompression.
 *
 * @param <T> the Generic type
 */
public interface IDecompression<T> {

    /**
     * Function to return the decompressed bytes.
     *
     * @param arr   the compressed bytes array
     * @param tree  the Huffman tree
     * @param count the characters in the file
     * @return the byte [ ]
     */
    byte[] getDecompressedBytes(byte[] arr, Node tree, long count);

    /**
     * Function to return the character count.
     *
     * @param map the map
     * @return the character count
     */
    int getCharCount(Map<T,Integer> map);

}
