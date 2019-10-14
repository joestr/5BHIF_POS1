/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.mongify.manipulator;

/**
 *
 * @author Joel
 */
public class EmbededClass {
    
    Class<?> root;
    Class<?> embeded;

    public EmbededClass(Class<?> root, Class<?> embeded) {
        this.root = root;
        this.embeded = embeded;
    }

    public EmbededClass() {
    }

    public Class<?> getRoot() {
        return root;
    }

    public void setRoot(Class<?> root) {
        this.root = root;
    }

    public Class<?> getEmbeded() {
        return embeded;
    }

    public void setEmbeded(Class<?> embeded) {
        this.embeded = embeded;
    }
    
    
}
