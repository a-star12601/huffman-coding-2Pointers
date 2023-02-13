package com.capillary.app.compression;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class TreeEncoderTest {

    @Test
    public void testStoreMap() throws IOException {
        TreeEncoder enc=new TreeEncoder();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',14);
        byte[] bytes=new byte[]{50,48,53,10,-84,-19,0,5,115,114,0,17,106,97,118,97,46,117,116,105,108,46,72,97,115,104,77,97,112,5,7,-38,-63,-61,22,96,-47,3,0,2,70,0,10,108,111,97,100,70,97,99,116,111,114,73,0,9,116,104,114,101,115,104,111,108,100,120,112,63,64,0,0,0,0,0,12,119,8,0,0,0,16,0,0,0,1,115,114,0,19,106,97,118,97,46,108,97,110,103,46,67,104,97,114,97,99,116,101,114,52,-117,71,-39,107,26,38,120,2,0,1,67,0,5,118,97,108,117,101,120,112,0,97,115,114,0,17,106,97,118,97,46,108,97,110,103,46,73,110,116,101,103,101,114,18,-30,-96,-92,-9,-127,-121,56,2,0,1,73,0,5,118,97,108,117,101,120,114,0,16,106,97,118,97,46,108,97,110,103,46,78,117,109,98,101,114,-122, -84,-107 ,29 ,11, -108 ,-32 ,-117 ,2 ,0 ,0 ,120, 112,0,0,0,14,120};
        byte[] actualBytes=enc.storeMap(map);
        assertArrayEquals(bytes,actualBytes);
    }
    @Test
    public void checkEncoding(){
        TreeEncoder enc=new TreeEncoder();
        HashMap<Character,String> map=new HashMap<>();
        map.put('a',"10");
        map.put('b',"0");
        map.put('c',"11");
        List<Byte> bytes=new ArrayList<>();
        byte[] tempInt=new byte[]{-102,115,70,-26,-76,-36,-25,45,105,-53,113,-70,-42,90,-64};
        for(byte i:tempInt)
            bytes.add(i);
        List<Byte> eval=enc.encodingLogic("abcbabcabcbabbcbcabcbacbabcbcabcabcabacbacbabcabacbcabbcbcaacbacbbacbacb".getBytes(),map);
        Assert.assertEquals(bytes,eval);
    }
}