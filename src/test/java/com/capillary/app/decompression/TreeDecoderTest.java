package com.capillary.app.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.huffman.decompression.TreeDecoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeDecoderTest {

    @Test
    public void getCount() {
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',2);
        TreeDecoder dec=new TreeDecoder();
        assertEquals(dec.getCount(map),2);
    }


    @Test
    public void decodingLogic() {
        TreeDecoder dec=new TreeDecoder();
        List<Byte> bytes=new ArrayList<>();
        byte[] decompressed="abcbabcabcbabbcbcabcbacbabcbcabcabcabacbacbabcabacbcabbcbcaacbacbbacbacb".getBytes();
        byte[] tempInt=new byte[]{-102,115,70,-26,-76,-36,-25,45,105,-53,113,-70,-42,90,-64};
        Node tree=new Node(new Node('b',29),new Node(new Node('a',21),new Node('c',22),1),2);
        byte[] eval=dec.decodingLogic(tempInt,tree,0,72);
        Assert.assertArrayEquals(decompressed,eval);
    }
}