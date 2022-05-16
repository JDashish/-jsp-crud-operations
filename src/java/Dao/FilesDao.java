/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Files;
import Model.User;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell1
 */
public class FilesDao {
    private static final String UPLOAD_PATH_FILES = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user/files/";
    public static void uploadFiles(Files files){
        Connection con = DBConnect.getConnection();
        String sql = "insert into file (user_id,file_link,created_at) values(?,?,?)";
        PreparedStatement ps;
        try{
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, files.getUser_id());
            ps.setString(2, files.getFile_link());
            ps.setString(3, files.getCreated_at());
            ps.executeUpdate();
            con.close();
        }catch(SQLException e){
            
        }
    }
    public static List<Files> getUploadFiles(String id){
        Connection con = DBConnect.getConnection();
        String sql = "select * from file where user_id='"+id+"'";
        PreparedStatement ps;
        List<Files> files = new ArrayList<>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int fileID = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String file_link = rs.getString("file_link");
                String created_at = rs.getString("created_at");
                files.add(new Files(fileID,user_id, file_link, created_at));

            }
        } catch (Exception e) {

        }
        return files;
    }
    public static Files getUploadFilesById(String id){
        Connection con = DBConnect.getConnection();
        String sql = "select * from file where id='"+id+"'";
        PreparedStatement ps;
        Files files = null ;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int fileID = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String file_link = rs.getString("file_link");
                String created_at = rs.getString("created_at");
                files = new Files(user_id, file_link, created_at);

            }
        } catch (Exception e) {

        }
        return files;
    }
    public static void delFile(String id) {
        Connection con = DBConnect.getConnection();
        String sql = "delete from file where id='" + id + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String deleteFiles(String id){
        Files files = getUploadFilesById(id);
//        return String.valueOf(files!=null);
        if(files!=null){
            File f = new File(UPLOAD_PATH_FILES+files.getFile_link());
            f.delete();
            delFile(id);
            return "Successfully file deleted ";
        }
        return id;
    }
}
