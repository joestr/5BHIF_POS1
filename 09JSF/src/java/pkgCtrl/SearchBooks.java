/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgCtrl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pkgData.Book;
import pkgData.Database;

/**
 *
 * @author Joel
 */
@ManagedBean
@RequestScoped
public class SearchBooks {
    
    private String searchParameter;
    private ArrayList<Book> books;
    
    public void searchBooks(String s) {
        try {
            this.books = (ArrayList<Book>)
                Database.getInstance()
                    .getBookWrapper().select("WHERE author LIKE '" + s + "'");
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }
    
    
}
