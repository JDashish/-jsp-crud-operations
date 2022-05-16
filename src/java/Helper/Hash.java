/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Dell1
 */
public class Hash {
    private  String data;

    public Hash(String data) {
        this.data = data;
    }
    
    
    public String toMd5(){
        String GenerateMd5 = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(this.data.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            GenerateMd5 = sb.toString();
        }catch(NoSuchAlgorithmException e){
            
        }
        return GenerateMd5;
    }
}
