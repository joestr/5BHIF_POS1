/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pkgData.Database;
import pkgData.User;
import xyz.joestr.dbwrapper.DatabaseAnnotationWrapper;
import xyz.joestr.dbwrapper.DatabaseWrapper;

/**
 *
 * @author Joel
 */
public class Login extends HttpServlet {

    private int counter = 0;
    
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
        
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        
        String r = request.getParameter("reset");
        
        if(r != null) {
            u = null;
            p = null;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String result =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>KH Hamburg Altona - Anmelden</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>KH Hamburg Altona - Anmelden</h1>\n" +
                "        <form action=\"\" method=\"GET\">\n" +
                "            <label for=\"login-form_login\">Benutzername:</label><input id=\"login-form_login\" type=\"text\" name=\"username\" value=\"$$$userval\"/><br />\n" +
                "            <label for=\"login-form_password\">Password:</label><input id=\"login-form_password\" type=\"password\" name=\"password\" value=\"$$$passval\"/><br />\n" +
                "            <input type=\"submit\" value=\"submit\"/>\n" +
                "        </form>\n" +
                "        <form action=\"\" method=\"GET\">\n" +
                "            <input type=\"submit\" name=\"reset\" value=\"reset\"/>\n" +
                "        </form>\n" +
                "        <br />\n" +
                "        $$$bottomline $$$hitcounter<br />\n" +
                "        <p>\n" +
                "            <img width=\"32px\" height=\"32px\" src=\"$$$netscapegif\" alt=\"netscape\" /> Works best in Netscape&reg; Navigator\"\n" +
                "        </p>\n" +
                "    </body>\n" +
                "</html>";
            
            result = result.replace("$$$userval", (u == null ? "" : u));
            result = result.replace("$$$passval", (p == null ? "" : p));
            
            try {
                if(u == null || p == null) {
                    result = result.replace("$$$bottomline", "type in username and password"); 
                } else if(u.isEmpty() || p.isEmpty()) {
                    result = result.replace("$$$bottomline", "type in username and password"); 
                } else {
                    // !!!!!!!!!! UNSAFE OPERATION SQL INJECTION AHEAD !!!!!!!!!!
                    ArrayList<User> s =
                        (ArrayList<User>) Database.getInstance().getUserWrapper().select("username = '" + u + "' AND password = '" + p + "'");

                    if (s.isEmpty()) {
                        result = result.replace("$$$bottomline", "incorrect login details");
                    } else {
                        String url = response.encodeRedirectURL(request.getContextPath() + "/NewBook");
                        response.sendRedirect(url);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            result = result.replace("$$$hitcounter", "(hits: " + ++counter + ")");
            result = result.replace("$$$netscapegif", request.getContextPath().split("//")[0] + "/assets/netscape.gif");
            
            out.print(result);
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
        processRequest(request, response);
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

}
