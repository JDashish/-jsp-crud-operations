/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Dell1
 */
public class Files {
    int id , user_id;
    String file_link ,created_at;

    public Files(int user_id, String file_link, String created_at) {
        this.user_id = user_id;
        this.file_link = file_link;
        this.created_at = created_at;
    }

    public Files(int id, int user_id, String file_link, String created_at) {
        this.id = id;
        this.user_id = user_id;
        this.file_link = file_link;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFile_link() {
        return file_link;
    }

    public void setFile_link(String file_link) {
        this.file_link = file_link;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    
}
