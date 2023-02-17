package com.capillary.app.general;

import java.util.Map;

public class ComplexReturnType<T> {
    public Map<T, Integer> getMap() {
        return mp;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    Map<T, Integer> mp;
    byte[] byteArray;

    public ComplexReturnType(Map<T, Integer> mp, byte[] byteArray){
        this.mp = mp;
        this.byteArray = byteArray;
    }
}
