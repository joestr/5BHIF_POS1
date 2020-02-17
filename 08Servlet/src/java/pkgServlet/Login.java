/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Krankenhaus Hamburg Altona - Anmelden</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Krankenhaus Hamburg Altona - Anmelden</h1>");
            out.println("<form action=\"\" method=\"GET\">");
            out.println("<label for=\"login_form_login\">Username:</label><input id=\"login_form_login\" type=\"text\" name=\"username\" value=\"" + (u == null ? "" : u)  + "\"/><br />");
            out.println("<label for=\"login_form_password\">Password:</label><input id=\"login_form_password\" type=\"password\" name=\"password\" value=\"" + (p == null ? "" : p)  + "\"/><br />");
            out.println("<input type=\"submit\" value=\"submit\"/>");
            out.println("</form>");
            out.println("<form action=\"\" method=\"GET\"><input type=\"submit\" name=\"reset\" value=\"reset\"/></form>");
            out.println("<br />");
            
            if(u == null || p == null) {
                out.print("type in username and password");
            } else if (u.equals(p)) {
                out.print("login details correct");
            } else {
                out.print("incorrect login details");
            }
            
            out.println("(hits: " + ++counter + ")");
            out.println("<p>");
            out.println("<img width=\"64px\" height=\"64px\" src=\"" + request.getContextPath().split("//")[0] + "/assets/netscape.gif" + "\" alt=\"netscape\" /> Works best in Netscape&reg; Navigator");
            out.println("<p>");
            out.println("</body>");
            out.println("</html>");
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
