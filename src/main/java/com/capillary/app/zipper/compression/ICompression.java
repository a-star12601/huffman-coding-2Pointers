package com.capillary.app.zipper.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The interface for a root.general Encoder.
 */
public interface ICompression<T> {
    /**
     * Method to Encode text.
     *
     * @param arr  the arr
     * @param hash the hash
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    List<Byte> getCompressedBytes(byte[] arr, Map<T,String> hash);

    /**
     * Serialise and store map into compressed file.
     *
     * @param map the map
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    byte[] getHeader(Map<T,Integer> map) throws IOException;

}
