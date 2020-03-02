/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import xyz.joestr.dbwrapper.DatabaseWrapable;
import xyz.joestr.dbwrapper.annotations.WrappedField;
import xyz.joestr.dbwrapper.annotations.WrappedTable;

/**
 *
 * @author Joel
 */
public class Book implements DatabaseWrapable {
    
    int id = 0;
    String author = null;
    String title = null;

    public Book() {
    }
    
    public Book(int id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String databaseTableName() {
        return "books";
    }

    @Override
    public Collection<String> databaseColumnNames() {
        List<String> result = new ArrayList<>();
        result.add("id");
        result.add("author");
        result.add("title");
        return result;
    }

    @Override
    public Collection<String> classFieldNames() {
        List<String> result = new ArrayList<>();
        result.add("id");
        result.add("author");
        result.add("title");
        return result;
    }
    
    
}
