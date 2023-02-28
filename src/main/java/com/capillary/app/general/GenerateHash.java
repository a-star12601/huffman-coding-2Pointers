package com.capillary.app.general;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateHash {
    public String getHash(byte[] file,String type){
        byte[] hash;
        try {
            hash= MessageDigest.getInstance(type).digest(file);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new BigInteger(1,hash).toString(16);
    }
}
