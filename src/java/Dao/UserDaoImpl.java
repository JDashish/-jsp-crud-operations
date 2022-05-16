/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Helper.Hash;
import Helper.Utils;
import Model.User;
import Model.UserDetails;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dell1
 */
public class UserDaoImpl implements UserDao {
private static final String UPLOAD_PATH = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user";
    @Override
    public boolean checkUser(String email) {
        Connection con = DBConnect.getConnection();

        String sql = "select * from user where email='" + email + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        Connection con = DBConnect.getConnection();
        String sql = "insert into user (`first_name`,`last_name`,`email`,`password`,`age`,`role`,`created_at`) values(?,?,?,?,?,?,?)";

        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getAge());
            ps.setInt(6, user.getRole());
            ps.setString(7, user.getCreated_at());
            ps.executeUpdate();
            con.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public boolean LoginUser(String email, String password) {
        Connection con = DBConnect.getConnection();

        String sql = "select * from user where email='" + email + "' and password='" + new Hash(password).toMd5() + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public User getUser(int uid) {
        Connection con = DBConnect.getConnection();
        String sql = "select * from user where id='" + uid + "'";
        PreparedStatement ps;
        User user = null;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                int role = rs.getInt("role");
                String created_at = rs.getString("created_at");
                user = new User(id, age, role, first_name, last_name, email, password, created_at);

            }
        } catch (Exception e) {

        }
        return user;
    }
    
    

    public List<User> getUserList() {
        Connection con = DBConnect.getConnection();
        String sql = "select * from user";
        PreparedStatement ps;
        List<User> uList = new ArrayList<>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                int role = rs.getInt("role");
                String created_at = rs.getString("created_at");
                uList.add(new User(id, age, role, first_name, last_name, email, password, created_at));

            }
        } catch (Exception e) {

        }
        return uList;
    }

    @Override
    public void delUser(String id) {
        Connection con = DBConnect.getConnection();
        String sql = "delete from user where id='" + id + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateUser(User user, String id) {
        Connection con = DBConnect.getConnection();
        String sql = "update user set first_name=? , last_name=? , email=? , age=? where id='" + id + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getAge());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {

        }
    }

    public boolean checkUserDetails(String uid) {
        Connection con = DBConnect.getConnection();

        String sql = "select * from user_details where user_id='" + uid + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                
                String img = UPLOAD_PATH + File.separator + rs.getString("profile_image");
                System.err.println(img);
                File file = new File(img);
                file.delete();
                con.close();
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public void updateUserImage(String uid, String image) {
        Connection con = DBConnect.getConnection();
        if (!this.checkUserDetails(uid)) {

            String sql = "insert into user_details (`user_id`,`profile_image`,`created_at`) values(?,?,?)";
            SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String created_at = dateformate.format(new Date());
            PreparedStatement ps;
            try {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, uid);
                ps.setString(2, image);
                ps.setString(3, created_at);
                ps.executeUpdate();
                con.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else {
            String sql = "update user_details set profile_image=? where user_id='" + uid + "'";
            System.out.println("sqlsqlsqls "+sql);
            PreparedStatement ps;
            try {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, image);
                ps.executeUpdate();
                con.close();
            } catch (SQLException e) {

            }
        }
    }

    public UserDetails getUserDetails(int uid) {
        Connection con = DBConnect.getConnection();
        String sql = "select * from user_details where user_id='" + uid + "'";
        PreparedStatement ps;
        UserDetails userDetails = null;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("profile_image");
                String created_at = rs.getString("created_at");
                userDetails = new UserDetails(first_name, created_at);

            }
        } catch (Exception e) {

        }
        return userDetails;
    }
    
}
