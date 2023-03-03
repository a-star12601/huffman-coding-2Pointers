package com.capillary.app.db;

import java.util.Map;

public interface IDataBase {

    void insert(String mdHash, byte[] freqmap);

    void createNewTable();

    void read();

    void update(int id, String mdHash, byte[] freqmap);

    void delete(String mdHash);

    Map<String,Integer> readHashMap(String mdHash);
}
