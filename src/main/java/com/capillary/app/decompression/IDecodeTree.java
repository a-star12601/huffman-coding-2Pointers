package com.capillary.app.decompression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface IDecodeTree {

    HashMap<?,Integer> initialiseMap(byte[] arr) throws IOException, ClassNotFoundException ;

    Node initialiseTree(HashMap<?,Integer> map);

    long getMapSize();
}
