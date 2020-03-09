/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import xyz.joestr.dbwrapper.annotations.WrappedTable;

/**
 *
 * @author Joel
 */
@ManagedBean
@WrappedTable(name = "books")
public class Book {
    
    @WrappedTable(name = "id")
    Integer id = 0;
    @WrappedTable(name = "author")
    String author = null;
    @WrappedTable(name = "title")
    String title = null;
    @WrappedTable(name = "price")
    Integer price = 0;

    public Book() {
    }
    
    public Book(Integer id, String author, String title, Integer price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
