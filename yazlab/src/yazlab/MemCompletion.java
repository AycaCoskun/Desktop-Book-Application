package yazlab;

import com.mysql.jdbc.StringUtils;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import java.awt.event.*;

public class MemCompletion extends JFrame{
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query;
    
    private Image image =null; 
    private JLabel[] picBox,title,author,year,publisher;
    private JLabel describe,searchTxt;
    private JTextField search;
    private JPanel panel;
    
    private JScrollPane jsp;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JComboBox[] cmb; // you just declared an array of petList
    private JComboBox pageNumbers;
    private static int counter;
    private JButton complete;
    private int x = 50;
    private int y = 100;
    private int row =1;
    private static int bookCounter =0;
    private int[] rowNumbers = new int[100];
    private int[] ratingValue = new int[100];
    private String[] isbn = new String[100];
    
    public MemCompletion() throws Exception{
        picBox = new JLabel[10];
        title = new JLabel[10];
        author = new JLabel[10];
        year = new JLabel[10];
        publisher = new JLabel[10];
        cmb = new JComboBox[10];
        addComps();
        addComboBoxes();
        onStart();
    }
    //Connection to DB
    private void DBConnect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys" , "root" , "1234");
            st = con.createStatement();
        }catch(Exception ex){
            System.out.println("Err:" + ex);
        }
    }
    //Counting books
    private int rowCount() throws Exception{
        int rowNum = 0;
        DBConnect();
        try{
            query = "SELECT ROW_NUMBER FROM YAZLAB.BOOKS ORDER BY ROW_NUMBER DESC LIMIT 1";
            rs = st.executeQuery(query);
            if(rs.next()) rowNum =  rs.getInt(1);
            
            con.close();
            return rowNum;
        }catch(Exception e){
        }
        return -1;
    }
    
    private void onStart() throws Exception{
        DBConnect();
        counter=1;
        try{    
            
            query = "SELECT ISBN,TITLE,AUTHOR,YEAR,PUBLISHER,URLM,ROW_NUMBER FROM YAZLAB.BOOKS WHERE ROW_NUMBER <"+row*10+1+" LIMIT 10";
            rs = st.executeQuery(query);
            
            while(rs.next()){
                
                URL url =new URL(rs.getString(6));
                image = ImageIO.read(url).getScaledInstance(150,225, Image.SCALE_DEFAULT);
                
                picBox[counter-1] =new JLabel(new ImageIcon(image));
                title[counter-1] = new JLabel("Title:"+rs.getString(2));
                author[counter-1] = new JLabel("Author:"+rs.getString(3));
                year[counter-1] = new JLabel("Year:"+String.valueOf(rs.getInt(4)));
                publisher[counter-1] = new JLabel("Publisher:"+rs.getString(5));
                cmb[counter-1].addItem("Choose Rating");
                for(int i =1;i<11;i++){
                    cmb[counter-1].addItem(i); // insert ratings to combobox
                }
                
                picBox[counter-1].setBounds(x,y,picBox[counter-1].getPreferredSize().width,picBox[counter-1].getPreferredSize().height);
                title[counter-1].setBounds(x+175,y+60,700,title[counter-1].getPreferredSize().height);
                author[counter-1].setBounds(x+175,y+80,400,author[counter-1].getPreferredSize().height);
                year[counter-1].setBounds(x+175,y+100,year[counter-1].getPreferredSize().width,year[counter-1].getPreferredSize().height);
                publisher[counter-1].setBounds(x+175,y+120,700,publisher[counter-1].getPreferredSize().height);
                cmb[counter-1].setBounds(x+175, y+150, cmb[counter-1].getPreferredSize().width, cmb[counter-1].getPreferredSize().height);
                
                panel.add(picBox[counter-1]);
                panel.add(title[counter-1]);
                panel.add(author[counter-1]);
                panel.add(year[counter-1]);
                panel.add(publisher[counter-1]);
                panel.add(cmb[counter-1]);

                counter++;
                y= y+275;    
            }
        con.close();
        //panel.setBackground(Color.yellow); SONUDAAA
        
        }catch(IOException e){
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(MemCompletion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addComboBoxes(){
        JComboBox combo1 = new JComboBox();
        JComboBox combo2 = new JComboBox();
        JComboBox combo3 = new JComboBox();
        JComboBox combo4 = new JComboBox();
        JComboBox combo5 = new JComboBox();
        JComboBox combo6 = new JComboBox();
        JComboBox combo7 = new JComboBox();
        JComboBox combo8 = new JComboBox();
        JComboBox combo9 = new JComboBox();
        JComboBox combo10 = new JComboBox();
        
        combo1.addActionListener(combo1Listener);
        combo2.addActionListener(combo2Listener);
        combo3.addActionListener(combo3Listener);
        combo4.addActionListener(combo4Listener);
        combo5.addActionListener(combo5Listener);
        combo6.addActionListener(combo6Listener);
        combo7.addActionListener(combo7Listener);
        combo8.addActionListener(combo8Listener);
        combo9.addActionListener(combo9Listener);
        combo10.addActionListener(combo10Listener);
        
        cmb[0] = combo1;
        cmb[1] = combo2;
        cmb[2] = combo3;
        cmb[3] = combo4;
        cmb[4] = combo5;
        cmb[5] = combo6;
        cmb[6] = combo7;
        cmb[7] = combo8;
        cmb[8] = combo9;
        cmb[9] = combo10;
    }
    
    private void addComps() throws Exception{
        setSize((int)(screenSize.getWidth()/1.5),(int)(screenSize.getHeight()/1.5)); //Form boyutunu belirler
        setTitle("Book Selection"); //Form başlığı
        setLocation(screenSize.width/2-this.getSize().width/2,screenSize.height/2-this.getSize().height/2); //Ekran ortasına ortaya çıkar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Tıklanınca kapanması için
        
        panel = new JPanel();
        jsp = new JScrollPane(panel , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Scroll panelin içine atılır
        jsp.getVerticalScrollBar().setUnitIncrement(16); // Scroll hızını arttırmak
        panel.setPreferredSize(new Dimension((int)(screenSize.getWidth()/1.5-18),2850));
        panel.setLayout(null);
        
        search = new JTextField();
        search.addActionListener(searchListener);
        searchTxt = new JLabel("Search Book:");
        
        describe = new JLabel("Please vote 10 books for complete your signup.");
        describe.setBounds((int)(screenSize.getWidth()/7),20,450,31);
        describe.setFont(new Font("Serif" , Font.PLAIN,18));
        describe.setForeground(Color.red);
        
        pageNumbers = new JComboBox();
        pageNumbers.addActionListener(pageListener);
        int rowNumber = (int)Math.ceil(rowCount()/10d);
        System.out.println("Row number of book:"+rowNumber);
        pageNumbers.addItem("Choose page");
        for(int i =1;i<rowNumber;i++){
             pageNumbers.addItem(i);
        }
        complete = new JButton("Complete - "+String.valueOf(bookCounter)+" / 10");
        complete.addActionListener(completeListener);
        complete.setEnabled(false);
        
        pageNumbers.setBounds((int)screenSize.getWidth()/2+150,2800,130,25);
        search.setBounds((int)screenSize.getWidth()/2, 65, 250, 27);
        searchTxt.setBounds((int)screenSize.getWidth()/2-100,65,searchTxt.getPreferredSize().width,27);
        complete.setBounds((int)screenSize.getWidth()/2-100, 2775, 200, 50);
        
        
        panel.add(pageNumbers);
        panel.add(describe);
        panel.add(search);
        panel.add(searchTxt);
        panel.add(complete);
        getContentPane().add(jsp);
    }
    
    private void pageSelect() throws SQLException, MalformedURLException, IOException{
        DBConnect();
        counter=1;
        int row = Integer.parseInt(pageNumbers.getSelectedItem().toString());
        
        query = "SELECT ISBN,TITLE,AUTHOR,YEAR,PUBLISHER,URLM,ROW_NUMBER FROM YAZLAB.BOOKS WHERE ROW_NUMBER <"+(row*10+1)+" AND ROW_NUMBER >"+((row-1)*10)+" LIMIT 10";
        System.out.println(query);
        rs = st.executeQuery(query);
        System.out.println(row);
        for(int i =0;i<10;i++){
            cmb[i].setSelectedIndex(0);
        }
        while(rs.next()){
            URL url = new URL(rs.getString(6));
            image = ImageIO.read(url).getScaledInstance(150,225, Image.SCALE_DEFAULT);
            
            picBox[counter-1].setIcon(new ImageIcon(image)); 
            
            title[counter-1].setText("Title:"+rs.getString(2));
            author[counter-1].setText("Author:"+rs.getString(3));
            year[counter-1].setText("Year:"+String.valueOf(rs.getInt(4)));
            publisher[counter-1].setText("Publisher:"+rs.getString(5));
            
            counter++;
        }
        con.close();
    }
    
    private void saveUserInfo() throws SQLException{
        Signup frame = new Signup();
        DBConnect();
        
        query = "INSERT INTO YAZLAB.USERS VALUES(?,?,?,?)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        
        preparedStmt.setInt(1, Integer.parseInt(frame.getNick()));
        preparedStmt.setString (2, frame.getLoc());
        preparedStmt.setInt(3, Integer.parseInt(frame.getAge()));
        preparedStmt.setString(4, frame.getPass());
        
        preparedStmt.execute();
        
        con.close();
        
        saveBooks();
    }
    
    private void saveBooks() throws SQLException{
        getISBN();
        Signup signup = new Signup();
        int nick = Integer.parseInt(signup.getNick());
        query = "INSERT INTO YAZLAB.RATINGS VALUES (?,?,?)";
        for(int i =0;i<bookCounter;i++){
            DBConnect();
            
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, nick);
            preparedStmt.setString(2, isbn[i]);
            preparedStmt.setInt(3, ratingValue[i]);
            
            preparedStmt.execute();
        
            con.close();
        }
    }
    
    private void getISBN() throws SQLException{
        for(int i=0;i<bookCounter;i++){
            DBConnect();
            query = "SELECT ISBN FROM YAZLAB.BOOKS WHERE ROW_NUMBER =" + rowNumbers[i];
            rs = st.executeQuery(query);
            
            if(rs.next()){
                isbn[i] = rs.getString(1);
            }
            con.close();
        }
    }
    
    ActionListener searchListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnect();
                try{
                    query = "SELECT TITLE,ROW_NUMBER FROM YAZLAB.BOOKS WHERE TITLE LIKE '%"+search.getText().toString()+"%'";
                    if(StringUtils.isEmptyOrWhitespaceOnly(search.getText())){
                        con.close();
                    }
                    else{
                    rs = st.executeQuery(query);
                    while(rs.next()){
                    System.out.println(rs.getString(1) +" "+rs.getString(2)); 
                    }
                    }
                search.setText("");
            
                con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MemCompletion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
        ActionListener completeListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveUserInfo();
                    Books frame = new Books();
                    frame.setVisible(true);
                    setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(MemCompletion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(MemCompletion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };   
    
    ActionListener combo1Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+1);
                        ratingValue[bookCounter-1] = (int) cmb[0].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo2Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+2);
                        ratingValue[bookCounter-1] = (int) cmb[1].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo3Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+3);
                        ratingValue[bookCounter-1] = (int) cmb[2].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo4Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+4);
                        ratingValue[bookCounter-1] = (int) cmb[3].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo5Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+5);
                        ratingValue[bookCounter-1] = (int) cmb[4].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo6Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+6);
                        ratingValue[bookCounter-1] = (int) cmb[5].getSelectedItem();
                        
                    }
                }
            }
        };
    
    ActionListener combo7Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+7);
                        ratingValue[bookCounter-1] = (int) cmb[6].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo8Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+8);
                        ratingValue[bookCounter-1] = (int) cmb[7].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo9Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+9);
                        ratingValue[bookCounter-1] = (int) cmb[8].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener combo10Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    String text = ((JComboBox) e.getSource()).getSelectedItem().toString();
                    //https://stackoverflow.com/questions/17624008/jbutton-array-actionlistener
                    if(text == "Choose Rating"){
                    }
                    else{
                        bookCounter++;
                        System.out.println("Book counter:"+bookCounter);
                        complete.setText("Complete - "+String.valueOf(bookCounter)+" / 10");
                        if(bookCounter==10){
                            complete.setEnabled(true);
                        }
                        rowNumbers[bookCounter-1] = (((row-1)*10)+10);
                        ratingValue[bookCounter-1] = (int) cmb[9].getSelectedItem();
                    }
                }
            }
        };
    
    ActionListener pageListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pageNumbers.getSelectedItem().toString() == "Choose page"){
                    System.out.println("Sayfa Yukleniyor.");
                }else{
                    row = Integer.parseInt(pageNumbers.getSelectedItem().toString());
                    try {
                        pageSelect();
                        panel.revalidate();
                        panel.repaint();
                    } catch (Exception ex) {
                        Logger.getLogger(MemCompletion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
}   