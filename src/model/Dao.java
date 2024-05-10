/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo Navarrete
 */
public class Dao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public boolean insertProduct(Product p) {
        String sql = "insert into product (name, price, image) values (?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setBytes(3, p.getImage());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }

    }

    public void getallProducts(JTable table) {
        String sql = "select * from product order by id desc";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getDouble(3);
                row[3] = rs.getBytes(4);
                model.addRow(row);
            }

        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public boolean update(Product product){
        String sql = "update product set name = ?, price = ? where id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
           
        }
        
    }

    
      public int getMaxRowAOrderTable(){
        int row = 0;
        try{
            st = con.createStatement();
            rs = st.executeQuery("select max(cid) from cart");
        while(rs.next())
        {
            row = rs.getInt(1);
        }
        } catch (Exception ex){
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row + 1;
    }
    
    public boolean isProductExist(int cid, int pid){
        try {
            ps = con.prepareStatement("select * from cart where cid = ? and pid = ?");
            ps.setInt(1, cid);
            ps.setInt(2, pid);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    
    public boolean insertCart(Cart cart){
        String sql = "insert into cart (cid, pid, pName, qty, price, total) valuse(?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cart.getId());
            ps.setInt(2, cart.getPid());
            ps.setString(3, cart.getpName());
            ps.setInt(4, cart.getQty());
            ps.setDouble(5, cart.getPrice());
            ps.setDouble(6, cart.getTotal());
            return ps.executeUpdate() > 0;
            
        } catch (Exception ex) {
            return false;
        }
        
    }
    
    public int getMaxRowAPaymentTable() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(pid) from payment");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row + 1;
    }
     public int getMaxRowACartTable() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(pid) from cart");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }
    
    public double subTotal(){
        double subTotal = 0.0;
        int cid = getMaxRowACartTable();
        
        try {
            st = con.createStatement();
             rs = st.executeQuery("select sum(total) as 'total' from cart where cid = '" + cid+"'");
             
             
             if(rs.next()){
                 subTotal = rs.getDouble(1);
             }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subTotal;
    }
        public void getProductsFromCart(JTable table) {
            int cid = getMaxRowACartTable();
        String sql = "select * from product cart where cid = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            rs = ps.executeQuery();
            

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getBytes(6);
                model.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}