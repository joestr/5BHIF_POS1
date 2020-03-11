/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgManagedBean;

import java.io.Serializable;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private String guess = "", solution = "", result = "", message = "";
    private int nextNumber = new Random().nextInt(10);
    private boolean playing = true;

    public void processKeystroke() {
        if (playing) {
            solution += String.valueOf(nextNumber);

            if (guess.endsWith(String.valueOf(nextNumber))) {
                result += "O";
                message = MessageSupplier.getRandomPositiveMessage();
            } else {
                result += "X";
                message = MessageSupplier.getRandomNegativeMessage();
                nextNumber = new Random().nextInt(10);
            }
        }
    }

    public void newGame() {
        playing = true;
        solution = "";
        result = "";
        message = "";
    }

    public void endGame() {
        playing = false;
        solution = "";
        result = "";
        guess = "";
        message = "";
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(int nextNumber) {
        this.nextNumber = nextNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
