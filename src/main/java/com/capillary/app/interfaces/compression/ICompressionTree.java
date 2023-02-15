package com.capillary.app.interfaces.compression;

import com.capillary.app.general.Node;

import java.util.HashMap;

public interface ICompressionTree {

    HashMap<?,Integer> getFrequencyMap(byte[] arr) ;

    Node generateTree(HashMap<?,Integer> map);

    HashMap<?,String> getHashTable(Node tree);
}
