/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pkgData.Book;
import pkgData.Database;

/**
 *
 * @author Joel
 */
public class NewBook extends HttpServlet {

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
        
        String id = request.getParameter("id");
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String result =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>KH Hamburg Altona: Bibliothek - Neues Buch anlegen</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <form action=\"\" method=\"GET\">\n" +
                "            <label for=\"newbook-form_id\">ID</label><input id=\"newbook-form_id\" type=\"number\" name=\"id\" />\n" +
                "            <label for=\"newbook-form_author\">ID</label><input id=\"newbook-form_author\" type=\"text\" name=\"author\" />\n" +
                "            <label for=\"newbook-form_title\">ID</label><input id=\"newbook-form_title\" type=\"text\" name=\"title\" />\n" +
                "            <input type=\"submit\" />\n" +
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
                "</html>"
            ;
            
            if(id != null && author != null && title != null) {
                try {
                    Database.getInstance().getBookWrapper().insert(
                        new Book(Integer.parseInt(id), author, title)
                        );
                    
                } catch (Exception ex) {
                    
                }
            }
            
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
