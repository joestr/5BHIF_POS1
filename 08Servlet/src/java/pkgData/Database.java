/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.joestr.dbwrapper.DatabaseAnnotationWrapper;
import xyz.joestr.dbwrapper.DatabaseConnectionHandler;

/**
 *
 * @author Joel
 */
public class Database {
    
    private static Database instance = null;
    
    private DatabaseConnectionHandler dCH = null;
    private DatabaseAnnotationWrapper<User> userWrapper = null;
    private DatabaseAnnotationWrapper<Book> bookWrapper = null;
    
    private Database() {
        Properties config = new Properties();
        try {
            config.load(this.getClass().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        dCH = new DatabaseConnectionHandler(config.getProperty("jdbcUri"));
        try {
            userWrapper = new DatabaseAnnotationWrapper(User.class, dCH);
            bookWrapper = new DatabaseAnnotationWrapper(Book.class, dCH);
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static Database getInstance() {
        
        if(instance == null) {
            instance = new Database();
        }
        
        return instance;
    }
    
    public DatabaseAnnotationWrapper<User> getUserWrapper() {
        return this.userWrapper;
    }
    
    public DatabaseAnnotationWrapper<Book> getBookWrapper() {
        return this.bookWrapper;
    }
}
