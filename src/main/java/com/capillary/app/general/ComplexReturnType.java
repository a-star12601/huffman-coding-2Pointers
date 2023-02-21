package com.capillary.app.general;

import java.util.Map;

/**
 * Custom Class for retrieving Map and Byte array.
 *
 * @param <T> the Generic Type
 */
public class ComplexReturnType<T> {
    /**
     * Function to return the map.
     *
     * @return the stored map
     */
    public Map<T, Integer> getMap() {
        return mp;
    }

    /**
     * Function to return the byte array.
     *
     * @return the stored byte array
     */
    public byte[] getByteArray() {
        return byteArray;
    }

    /**
     * Variable to store the map.
     */
    Map<T, Integer> mp;
    /**
     * Variable to store the Byte array.
     */
    byte[] byteArray;

    /**
     * Constructor for the class.
     *
     * @param mp        the map
     * @param byteArray the byte array
     */
    public ComplexReturnType(Map<T, Integer> mp, byte[] byteArray){
        this.mp = mp;
        this.byteArray = byteArray;
    }
}
