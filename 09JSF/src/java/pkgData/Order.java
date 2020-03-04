/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import xyz.joestr.dbwrapper.annotations.WrappedField;
import xyz.joestr.dbwrapper.annotations.WrappedTable;

/**
 *
 * @author Joel
 */
@WrappedTable(name = "orders")
public class Order {
    
    @WrappedField(name = "username")
    String username;
    @WrappedField(name = "bookid")
    Integer bookId;

    public Order() {
    }

    public Order(String username, Integer bookId) {
        this.username = username;
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    
    
}
