package com.capillary.app.interfaces.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    List<Byte> getCompressedBytes(byte[] arr, Map<?,String> hash);

    /**
     * Serialise and store map into compressed file.
     *
     * @param map the map
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    byte[] getHeader(Map<?,Integer> map) throws IOException;

    byte[] getSerializedMap(Map<?,Integer> map) throws IOException;
}
