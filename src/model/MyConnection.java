/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo Campa, Jake Sandoval, Matthew Morrow, Carlos Zavala, Carlos V, and Daniel Felix
 */
public class MyConnection {
    
    public static final String username = "root"; //this is the username from mysql
    public static final String password = "polash";  //created sql password
    public static final String url = "jdbc:mysql://localhost:3306/cafe2";
    public static Connection con = null;
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ""+ex, "", JOptionPane.WARNING_MESSAGE);
        }
        
        return con;
    }
}
