package com.capillary.app.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.Map;

public class SQLiteDB implements IDataBase{

    @Override
    public void delete(int id) {
        PreparedStatement pstmt = null;
        Connection conn=connect();
        String sql = "DELETE FROM FrequencyMap WHERE id = ?";

        if (conn != null){
            try {
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, id);

                pstmt.executeUpdate();

                System.out.println("Row deleted Successfully");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Map<String,Integer> readHashMap(String mdHash) {
        PreparedStatement pstmt =null;
        Connection conn=connect();
        Map<String,Integer> freqmap=null;
        String sql = "SELECT freqmap from FrequencyMap WHERE mdhash = ?";
        if (conn != null) {
            ResultSet rs = null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, mdHash);
                rs=pstmt.executeQuery();
                rs.next();

                byte[] buf = rs.getBytes(1);
                ObjectInputStream objectIn = null;
                if (buf != null)
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

                freqmap = (Map<String ,Integer>) objectIn.readObject();
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return freqmap;
    }

    @Override
    public void update(int id, String mdHash, byte[] freqmap) {
        PreparedStatement pstmt = null;
        Connection conn=connect();
        String sql = "UPDATE FrequencyMap SET "
                + "freqmap = ? "
                + "WHERE mdhash = ?";

        if (conn != null){
            try {
                pstmt = conn.prepareStatement(sql);

                pstmt.setBytes(1, freqmap);
                pstmt.setString(2, mdHash);

                pstmt.executeUpdate();
                System.out.println("Update Successful");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void read(){
        String sql = "SELECT * FROM FrequencyMap";
        Connection conn=connect();

        if (conn != null) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    System.out.println(rs.getString("mdhash") + "\t" +
                            rs.getObject("freqmap"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void insert(String mdHash, byte[] freqmap) {
        PreparedStatement pstmt = null;
        Connection conn=connect();
        String sql = "INSERT INTO FrequencyMap(mdhash,freqmap) VALUES(?,?)";

        if (conn != null){
            try{
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, mdHash);
                pstmt.setBytes(2, freqmap);
                pstmt.executeUpdate();

                System.out.println("Row inserted Successfully");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void createNewTable() {
        Connection conn=connect();
        String sql = "CREATE TABLE IF NOT EXISTS FrequencyMap (\n"
                + "	mdhash text PRIMARY KEY NOT NULL,\n"
                + "	freqmap blob\n"
                + ");";

        if(conn != null){
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:huffman.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite huffman DB has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }



//    public static void main(String[] args) {
//        IDataBase setup = new SQLiteDB();
//
//        setup.createNewTable();
////        setup.insert(conn, "Dhanish", 10);
////        setup.insert(conn, "Anuroop", 11);
////        setup.read(conn);
//
//        setup.update(2, "Anuuu", 12);
//        setup.delete(3);
//    }
}
