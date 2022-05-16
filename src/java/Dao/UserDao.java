/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.User;

/**
 *
 * @author Dell1
 */
public interface UserDao {
    public boolean checkUser(String email);
    public void addUser(User user);
    public boolean LoginUser(String email , String password);
    public void delUser(String id);
    public void UpdateUser(User user , String id);
    public void updateUserImage(String uid , String image);
}
