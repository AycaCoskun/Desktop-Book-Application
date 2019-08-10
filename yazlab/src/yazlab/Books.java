package yazlab;

import com.mysql.jdbc.StringUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class Books extends JFrame{
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query;
    
    private Image image =null; 
    private JLabel[] picBox,title,author,year,publisher;
    private JLabel describe,searchTxt;
    private JTextField search;
    private JPanel panel;
    
    private JButton vote, avg,read1,read2;
    
    private JScrollPane jsp;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JComboBox pageNumbers;
    private static int counter;
    
    private int x = 50;
    private int y = 100;
    private int row =1;
    
    private String[] isbn = new String[10];
    
    public Books() throws Exception{
        picBox = new JLabel[10];
        title = new JLabel[10];
        author = new JLabel[10];
        year = new JLabel[10];
        publisher = new JLabel[10];
        addComps();
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
                
                picBox[counter-1].setBounds(x,y,picBox[counter-1].getPreferredSize().width,picBox[counter-1].getPreferredSize().height);
                title[counter-1].setBounds(x+175,y+60,700,title[counter-1].getPreferredSize().height);
                author[counter-1].setBounds(x+175,y+80,400,author[counter-1].getPreferredSize().height);
                year[counter-1].setBounds(x+175,y+100,year[counter-1].getPreferredSize().width,year[counter-1].getPreferredSize().height);
                publisher[counter-1].setBounds(x+175,y+120,700,publisher[counter-1].getPreferredSize().height);
                
                panel.add(picBox[counter-1]);
                panel.add(title[counter-1]);
                panel.add(author[counter-1]);
                panel.add(year[counter-1]);
                panel.add(publisher[counter-1]);
            
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
    
    private void addComps() throws Exception{
        Membership frame = new Membership();
        setSize((int)(screenSize.getWidth()/1.5),(int)(screenSize.getHeight()/1.5)); //Form boyutunu belirler
        setTitle("Books"); //Form başlığı
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
        
        vote = new JButton();
        vote.setText("Top Rated Books");
        vote.addActionListener(voteListener);
        
        avg = new JButton();
        avg.setText("Best Books");
        avg.addActionListener(avgListener);
        
        read1 = new JButton();
        read1.setText("Read");
        read1.addActionListener(read1Listener);
        
        describe = new JLabel("Welcome " + frame.getNick() + ". You can read books");
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
        
        pageNumbers.setBounds((int)screenSize.getWidth()/2+150,2800,130,25);
        search.setBounds((int)screenSize.getWidth()/2, 65, 250, 27);
        searchTxt.setBounds((int)screenSize.getWidth()/2-100,65,searchTxt.getPreferredSize().width,27);
        vote.setBounds(1000,150,200,35);
        avg.setBounds(1000,200,200,35);
        read1.setBounds(225,250,100,35);
   
        panel.add(pageNumbers);
        panel.add(describe);
        panel.add(search);
        panel.add(searchTxt);
        panel.add(vote);
        panel.add(avg);
        panel.add(read1);
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
    
    private void getAvgISBN() throws SQLException{
        DBConnect();
        int i=0;
        
        query = "SELECT AVG(RATING) , ISBN " +
                "FROM YAZLAB.RATINGS " +
                "WHERE ISBN IN (SELECT " +
                "ISBN " +
                "FROM YAZLAB.RATINGS " +
                "GROUP BY ISBN " +
                "HAVING COUNT(USERID) > 500 ORDER BY COUNT(*) DESC) " +
                "group by ISBN " +
                "HAVING avg(RATING)>3 ORDER BY AVG(RATING) DESC LIMIT 10";
        rs = st.executeQuery(query);
        while(rs.next()){
            isbn[i]=rs.getString(2);
            i++;
        }
        con.close();
    }
    
    private void getAVG() throws SQLException, IOException, MalformedURLException{
        getAvgISBN();
        
        for(int i =0;i<10;i++){
            DBConnect();
            
            query = "SELECT "+
                    "ISBN,TITLE,AUTHOR,YEAR,PUBLISHER,URLM,ROW_NUMBER "+
                    "FROM YAZLAB.BOOKS WHERE "+
                    "ISBN = " + isbn[i];
            rs = st.executeQuery(query);
            if(rs.first()){
                URL url = null;
                try {
                    url = new URL(rs.getString(6));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                }
                image = ImageIO.read(url).getScaledInstance(150,225, Image.SCALE_DEFAULT);
            
                picBox[i].setIcon(new ImageIcon(image)); 
            
                title[i].setText("Title:"+rs.getString(2));
                author[i].setText("Author:"+rs.getString(3));
                year[i].setText("Year:"+String.valueOf(rs.getInt(4)));
                publisher[i].setText("Publisher:"+rs.getString(5));
                
                con.close();
            }
            
            describe.setText("Best Books");
        }
    }
    
    private void getVoteISBN() throws SQLException{
        DBConnect();
        int i=0;
        
        query = "SELECT " +
                "ISBN,COUNT(*) " +
                "FROM " +
                "YAZLAB.RATINGS " +
                "GROUP BY " +
                "ISBN " +
                "HAVING " +
                "COUNT(ISBN) > 1 ORDER BY COUNT(*) DESC LIMIT 10";
        
        rs = st.executeQuery(query);
        while(rs.next()){
            isbn[i]=rs.getString(1);
            i++;
        }
        con.close();
    }
    
    private void getVote() throws SQLException, MalformedURLException, IOException {
        getVoteISBN();
        
        for(int i =0;i<10;i++){
            DBConnect();
            
            query = "SELECT "+
                    "ISBN,TITLE,AUTHOR,YEAR,PUBLISHER,URLM,ROW_NUMBER "+
                    "FROM YAZLAB.BOOKS WHERE "+
                    "ISBN = " + isbn[i];
            rs = st.executeQuery(query);
            if(rs.first()){
                URL url = null;
                try {
                    url = new URL(rs.getString(6));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                }
                image = ImageIO.read(url).getScaledInstance(150,225, Image.SCALE_DEFAULT);
            
                picBox[i].setIcon(new ImageIcon(image)); 
            
                title[i].setText("Title:"+rs.getString(2));
                author[i].setText("Author:"+rs.getString(3));
                year[i].setText("Year:"+String.valueOf(rs.getInt(4)));
                publisher[i].setText("Publisher:"+rs.getString(5));
                
                con.close();
            }
            
            describe.setText("Top Rated Books");
        }
    }
    
    ActionListener pageListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                Membership frame = new Membership();
                describe.setText("Welcome " + frame.getNick() + ". You can read books");
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
    
    ActionListener voteListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getVote();
                } catch (SQLException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
    ActionListener read1Listener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ReadBook read = new ReadBook();
                    read.setVisible(true);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
    ActionListener avgListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getAVG();
                } catch (SQLException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
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
    
}
