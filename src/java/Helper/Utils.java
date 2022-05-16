/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dell1
 */
public class Utils {
    
    public static final String UPLOAD_PATH = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user";
    
    public static String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getScheme() + "://";
    String serverName = request.getServerName();
    String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
    String contextPath = request.getContextPath();
    return scheme + serverName + serverPort + contextPath;
  }
}
