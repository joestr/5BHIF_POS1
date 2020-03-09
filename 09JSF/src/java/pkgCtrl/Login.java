/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgCtrl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pkgData.Database;
import pkgData.User;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String pwd;
	private String msg;
	private String user;
    private boolean validLogin;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

    public boolean isValidLogin() {
        return validLogin;
    }

    public void setValidLogin(boolean validLogin) {
        this.validLogin = validLogin;
    }
    
    

	//validate login
	public String validateUsernamePassword() {
        
        validLogin = false;
        
        try {
            validLogin = Database.getInstance().getUserWrapper().select().stream().anyMatch((u) -> {
                return u.getUsername().equals(user)
                    && u.getPassword().equals(pwd);
            });
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		if (validLogin) {
            HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", user);
			return "admin?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Incorrect Username and Passowrd",
                    null
                )
            );
			return "login?faces-redirect=true";
		}
	}

	//logout event, invalidate session
	public String logout() {
        HttpSession session = SessionUtils.getSession();
		session.invalidate();
        FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Logout successful",
                null
            )
        );
		return "login?faces-redirect=true";
	}
    
    public ArrayList<String> getUsernames() {
        try {
            // Look at this glorious command chain!
            return (ArrayList<String>) Database.getInstance()
                .getUserWrapper()
                .select()
                .parallelStream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}