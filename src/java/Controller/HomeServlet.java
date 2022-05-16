/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.FilesDao;
import Dao.UserDaoImpl;
import Model.Files;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dell1
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1204, // 1 GB
        maxFileSize = 1024 * 1024 * 1204, // 1 GB
        maxRequestSize = 1024 * 1024 * 1204 // 1 GB
)
public class HomeServlet extends HttpServlet {

    private static final String UPLOAD_PATH = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user";
    private static final String UPLOAD_PATH_FILES = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user/files";
    UserDaoImpl udi = new UserDaoImpl();
    FilesDao fDao = new FilesDao();

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Cookie ck[] = request.getCookies();
        for (int i = 1; i < ck.length; i++) {
            if (ck[i].getName().equals("userLoginEmail") && ck[i].getValue() != null) {
                request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
//                out.print(ck[i].getValue());
            } else {
                response.sendRedirect("login");
            }
        }
        if (ck.length == 1) {
            response.sendRedirect("login");
        }

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
        processRequest(request, response);
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
        PrintWriter out = response.getWriter();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            String uid = request.getParameter("UserID");
            if (request.getParameter("type").equals("UploadUserFIles")) {
                try {
                    File uploadsPath = new File(UPLOAD_PATH_FILES);
                    if (!uploadsPath.exists()) {
                        //create upload folder if not exist.
                        uploadsPath.mkdir();
                    }
                    for (Part part : request.getParts()) {
                        String fileName = getFileName(part);
                        if (fileName != null) {
                            int i = (int) new Date().getTime();
                            String fn = uid + String.valueOf(i) + "_" + fileName.replace(" ", "_");
                            part.write(UPLOAD_PATH_FILES + File.separator + fn);
                            SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String created_at = dateformate.format(new Date());
                            Files files = new Files(Integer.valueOf(uid), fn, created_at);
                            fDao.uploadFiles(files);
                        }
                    }
                    out.println("File uploaded successfully! ");
                } catch (Exception e) {
                    out.println("Error while uploading files! " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                try {
                    File uploadsPath = new File(UPLOAD_PATH);
                    if (!uploadsPath.exists()) {
                        //create upload folder if not exist.
                        uploadsPath.mkdir();
                    }
                    for (Part part : request.getParts()) {
                        String fileName = getFileName(part);
                        if (fileName != null) {
                            int i = (int) new Date().getTime();
                            String fn = uid + String.valueOf(i) + "_" + fileName.replace(" ", "_");
                            part.write(UPLOAD_PATH + File.separator + fn);
                            udi.updateUserImage(uid, fn);
                        }
                    }
                    out.println("File uploaded successfully! ");
                } catch (Exception e) {
                    out.println("Error while uploading files! " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } else if (request.getParameter("type").equals("delete_user")) {
            out.print(delete_user(request.getParameter("id")));
        } else if (request.getParameter("type").equals("update_user")) {
            out.print(updateUser(request.getParameter("id"),
                    request.getParameter("fname"),
                    request.getParameter("lname"),
                    request.getParameter("email"),
                    request.getParameter("age")));
        }else if(request.getParameter("type").equals("file_delete")){
            out.print(FilesDao.deleteFiles(request.getParameter("id")));
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    private String delete_user(String id) {
        udi.delUser(id);
//        Cookie c = new Cookies
                
        return "User deleted successfully!";
    }

    private String updateUser(String id, String fname, String lname, String email, String age) {
        User u = new User(fname, lname, email, Integer.valueOf(age));
        udi.UpdateUser(u, id);
        return "User update successfully";
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    private void uploadUserImage(String parameter, HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            File uploadsPath = new File(UPLOAD_PATH);
            if (!uploadsPath.exists()) {
                //create upload folder if not exist.
                uploadsPath.mkdir();
            }
            out.print(request.getParts().size());
            for (Part part : request.getParts()) {
                String fileName = getFileName(part);
                if (fileName != null) {
                    int i = (int) new Date().getTime();
                    part.write(UPLOAD_PATH + File.separator + String.valueOf(i) + "_" + fileName);
                }
            }
            out.println("File uploaded successfully! ");
        } catch (Exception e) {
            out.println("Error while uploading files! " + e.getMessage());
            e.printStackTrace();
        }
    }

}
