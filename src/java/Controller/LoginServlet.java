/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.UserDaoImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dell1
 */
public class LoginServlet extends HttpServlet {

    UserDaoImpl userDao = new UserDaoImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        Cookie ck[] = request.getCookies();
        if (ck != null) {
            for (int i = 1; i < ck.length; i++) {

                if (ck[i].getName().equals("userLoginEmail") && ck[i].getValue() == null) {
                    processRequest(request, response);

                } else {

                    response.sendRedirect("home");
                }
            }
        }
        if(ck==null){
            processRequest(request, response);
        }

        if (ck.length == 1) {
            processRequest(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        if (userDao.LoginUser(email, pass)) {
            //use is present

            Cookie loginCookie = new Cookie("userLoginEmail", email);
            loginCookie.setMaxAge(1 * 60);
            response.addCookie(loginCookie);

            flash(request, response, "home.jsp", "Login successfully", "success");
            response.sendRedirect("home");
        } else {
            flash(request, response, "WEB-INF/jsp/login.jsp", "User not found", "error");
//            user not present
        }
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
            if (!type.equals("success")) {
                request.getRequestDispatcher(url).forward(request, response);
            }

        } catch (ServletException | IOException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
