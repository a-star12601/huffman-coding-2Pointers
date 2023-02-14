package com.capillary.app.interfaces.compression;

import com.capillary.app.general.Node;

import java.util.HashMap;

public interface IEncodeTree {

    HashMap<?,Integer> initialiseMap(byte[] arr) ;

    Node initialiseTree(HashMap<?,Integer> map);

    HashMap<?,String> generateTreeMap(Node tree);
}
