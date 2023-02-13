package com.capillary.app.compression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface IEncodeTree {

    HashMap<?,Integer> initialiseMap(byte[] arr) ;

    Node initialiseTree(HashMap<?,Integer> map);

    HashMap<?,String> generateTreeMap(Node tree);
}
