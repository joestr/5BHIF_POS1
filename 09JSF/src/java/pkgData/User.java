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
@WrappedTable(name = "users")
public class User {
    
    @WrappedField(name = "username")
    String username;
    @WrappedField(name = "password")
    String password;
    @WrappedField(name = "information")
    String information;
    @WrappedField(name = "count_orders")
    Integer countOrders;

    public User() {
    }

    public User(String username, String password, String information, Integer countOrders) {
        this.username = username;
        this.password = password;
        this.information = information;
        this.countOrders = countOrders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(Integer countOrders) {
        this.countOrders = countOrders;
    }
    
    
    
}
