package com.capillary.app.interfaces.decompression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.HashMap;

public interface IDecodeTree {

    HashMap<?,Integer> initialiseMap(byte[] arr) throws IOException, ClassNotFoundException ;

    Node initialiseTree(HashMap<?,Integer> map);

    long getMapSize();
}
