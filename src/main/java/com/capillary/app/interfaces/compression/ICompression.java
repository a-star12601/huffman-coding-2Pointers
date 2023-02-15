package com.capillary.app.interfaces.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The interface for a root.general Encoder.
 */
public interface ICompression {
    /**
     * Method to Encode text.
     *
     * @param arr  the arr
     * @param hash the hash
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    List<Byte> getCompressedBytes(byte[] arr, HashMap<?,String> hash);

    /**
     * Serialise and store map into compressed file.
     *
     * @param map the map
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    byte[] getHeader(HashMap<?,Integer> map) throws IOException;

    byte[] getSerializedMap(HashMap<?,Integer> map) throws IOException;
}
