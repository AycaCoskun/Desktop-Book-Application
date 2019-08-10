package yazlab;

import com.mysql.jdbc.StringUtils;
import java.awt.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class Signup extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query;
    private static String nickk,agee,locc,pwd;
    
    public Signup() throws SQLException {   
        initComponents();
        setSize(568,288);
        Dimension dim= Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
        suggestID();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label5 = new java.awt.Label();
        nick = new java.awt.TextField();
        location = new java.awt.TextField();
        label6 = new java.awt.Label();
        age = new java.awt.TextField();
        back = new java.awt.Button();
        next = new java.awt.Button();
        pass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(851, 566));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        label1.setForeground(new java.awt.Color(153, 0, 204));
        label1.setText("SIGNUP");

        label2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label2.setForeground(new java.awt.Color(102, 193, 190));
        label2.setText("UserID:");

        label3.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label3.setForeground(new java.awt.Color(102, 193, 190));
        label3.setText("Password:");

        label5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label5.setForeground(new java.awt.Color(102, 193, 190));
        label5.setText("Location:");

        nick.setBackground(new java.awt.Color(253, 248, 248));
        nick.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        location.setBackground(new java.awt.Color(253, 248, 248));
        location.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        label6.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label6.setForeground(new java.awt.Color(102, 193, 190));
        label6.setText("Age:");

        age.setBackground(new java.awt.Color(253, 248, 248));
        age.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        back.setBackground(new java.awt.Color(102, 193, 190));
        back.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        back.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        back.setLabel("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        next.setBackground(new java.awt.Color(102, 193, 190));
        next.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        next.setLabel("Next");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(87, 87, 87)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nick, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(location, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(age, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        Membership frame = new Membership();
        frame.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backActionPerformed
    private boolean check(){
        if(StringUtils.isEmptyOrWhitespaceOnly(nick.getText())){
            JOptionPane.showMessageDialog(this, "Nickname cant empty.");
            nick.requestFocus();
            return false;
        }else if(StringUtils.isEmptyOrWhitespaceOnly(pass.getText())){
            JOptionPane.showMessageDialog(this, "Password cant empty.");
            pass.requestFocus();
            return false;
        }else if(StringUtils.isEmptyOrWhitespaceOnly(location.getText())){
            JOptionPane.showMessageDialog(this, "Location cant empty.");
            location.requestFocus();
            return false;
        }else if(StringUtils.isEmptyOrWhitespaceOnly(age.getText())){
            JOptionPane.showMessageDialog(this, "Age cant empty.");
            age.requestFocus();
            return false;
        }
        return true;
    }
    
    public  String getNick(){
        return nickk;
    }
    public String getPass(){
        return pwd;
    }
    public String getLoc(){
        return locc;
    }
    public String getAge(){
        return agee;
    }
    
    private boolean checkNick() throws SQLException{
        DBConnect();
        
        query = "SELECT USERID FROM YAZLAB.USERS WHERE USERID = '"+nick.getText()+"'";
        System.out.println(query);
        rs = st.executeQuery(query);
        
        if(rs.next()){
            con.close();
            JOptionPane.showMessageDialog(this, "Choose different id please");
            suggestID();
            nick.requestFocus();
            return false;
        }
        con.close();
        return true;
    }
    
    private void DBConnect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys" , "root" , "1234");
            st = con.createStatement();
        }catch(Exception ex){
            System.out.println("Err:" + ex);
        }
    }
    
    private void suggestID() throws SQLException{
        DBConnect();
        
        query = "SELECT USERID FROM YAZLAB.USERS ORDER BY USERID DESC LIMIT 1";
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(rs.first()){
            int x = rs.getInt(1)+1;
            nick.setText(String.valueOf(x));
            con.close();
            
        }
        con.close();
    }
    
    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
        MemCompletion frame;
        if(check()){
            try {
                if(checkNick()){
                    nickk = nick.getText();
                    agee = age.getText();
                    locc = location.getText();
                    pwd = pass.getText();
                    try {    
                        frame = new MemCompletion();
                        frame.setVisible(true);
                    } catch (Exception ex) {
                        Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.setVisible(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_nextActionPerformed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Signup().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextField age;
    private java.awt.Button back;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.TextField location;
    private java.awt.Button next;
    private java.awt.TextField nick;
    private javax.swing.JPasswordField pass;
    // End of variables declaration//GEN-END:variables
}
