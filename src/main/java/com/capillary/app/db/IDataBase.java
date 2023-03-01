package com.capillary.app.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public interface IDataBase {

    void insert(String mdHash, byte[] freqmap);

    void createNewTable();


    void read();

    void update(int id, String mdHash, byte[] freqmap);

    void delete(int id);

    Map<String,Integer> readHashMap(String mdHash);
}
