/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.UserDao;
import Dao.UserDaoImpl;
import Helper.Hash;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell1
 */
public class SignupServlet extends HttpServlet {

    UserDaoImpl userDao = new UserDaoImpl();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie ck[]=request.getCookies(); 
        
        for(int i=1;i<ck.length;i++){
            
            if(ck[i].getName().equals("userLoginEmail") && ck[i].getValue()==null){
                processRequest(request, response);
                
                
            }else{
                
                response.sendRedirect("home");
            }
        }
        if(ck.length==1){
            processRequest(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String pass = new Hash(request.getParameter("password")).toMd5();
        SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created_at = dateformate.format(new Date());
        String url = "WEB-INF/jsp/signup.jsp";
        
        
        User user = new User(0, 2, fname, lname, email, pass, created_at);
        
        
        if(userDao.checkUser(email)){
            flash(request,response,url,"Opps! Email already exist.","error");
            
        }else{
            //process to signup steps.
            userDao.addUser(user);
            flash(request,response,"WEB-INF/jsp/login.jsp","Congratulation! signup successfully .","success");
            response.sendRedirect("login");
        }
        
//        PrintWriter out = response.getWriter();
//        out.println(flname.length);
//        out.println(email);
//        out.println(pass);
//        out.println(created_at);
        
//        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static void flash(HttpServletRequest request, HttpServletResponse response, 
            String url, String message, String type) {
        try {
            request.getSession().setAttribute("type", type);
            request.getSession().setAttribute("message", message);
            if(!type.equals("success")){
                request.getRequestDispatcher(url).forward(request, response);
            }
            
        } catch (ServletException | IOException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
