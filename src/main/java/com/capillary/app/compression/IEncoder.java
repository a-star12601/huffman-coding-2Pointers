package com.capillary.app.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface for a root.general Encoder.
 */
public interface IEncoder {
    /**
     * Method to Encode text.
     *
     * @param arr  the arr
     * @param hash the hash
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    List<Byte> encodingLogic(byte[] arr, HashMap<?,String> hash);

    /**
     * Serialise and store map into compressed file.
     *
     * @param map the map
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    byte[] storeMap(HashMap<?,Integer> map) throws IOException;
    public byte[] getMapBytes(HashMap<?,Integer> map) throws IOException;
}
