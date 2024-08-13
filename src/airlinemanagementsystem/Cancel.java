
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener{
    JTextField tfpnr;
    JLabel tfname,cancelcellationno,lblfcode,lbldateoftravel;
    JButton fetchButton,flight;
    Choice source,destination;

    
    
    
    
    public Cancel(){
        
        getContentPane().setBackground(Color.white);
        setLayout(null);
        
        Random random = new Random();
       
        JLabel heading = new JLabel("CANCELLATION");
        heading.setBounds(180,20,250,35);
        heading.setFont(new Font("Tahoma",Font.PLAIN,32));
        add(heading);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/cancel.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(470,120,250,250);
        add(image);
        
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60,80,150,25);
        lblaadhar.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(lblaadhar);
        
        tfpnr = new JTextField();
        tfpnr.setBounds(200,80,150,25);
        add(tfpnr);
        
        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.white);
        fetchButton.addActionListener(this);
        fetchButton.setBounds(380,80,120,25);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60,130,150,25);
        lblname.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220,130,150,25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("Cancellation No");
        lblnationality.setBounds(60,180,150,25);
        lblnationality.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(lblnationality);
        
        cancelcellationno = new JLabel("" + random.nextInt(100000));
        cancelcellationno.setBounds(220,180,150,25);
        add(cancelcellationno);
        
        
        JLabel lbladdress = new JLabel("Flight Code");
        lbladdress.setBounds(60,230,150,25);
        lbladdress.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(lbladdress);
        
        lblfcode = new JLabel();
        lblfcode.setBounds(220,230,150,25);
        add(lblfcode);
        
        
  
        
        JLabel lblgender = new JLabel("Date");
        lblgender.setBounds(60,280,150,25);
        lblgender.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(lblgender);
        
        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220,280,150,25);
        add(lbldateoftravel);
   
      
        
        try{
            Conn c = new Conn();
            String query = "select * from flight";
            ResultSet rs = c.s.executeQuery(query);
            
            while(rs.next()){
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        flight = new JButton("Cancel");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(220,330,150,30);
        flight.addActionListener(this);
        add(flight);
        
        
        
        setSize(800,450);
        setLocation(350,150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        
        if(ae.getSource() == fetchButton){
            
            
            String pnr = tfpnr.getText();
            
            try{
                Conn conn = new Conn();
                String query = "select * from reservation where PNR = '"+pnr+"'";
                
                ResultSet rs = conn.s.executeQuery(query);
                
                if(rs.next()){
                    tfname.setText(rs.getString("name"));
                    lblfcode.setText(rs.getString("flightcode"));
                    lbldateoftravel.setText(rs.getString("ddate"));
                   
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please enter correct aadhar");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(ae.getSource() == flight){
            
            
            String name = tfname.getText();
            String pnr = tfpnr.getText();
            String cancelno = cancelcellationno.getText();
            String fcode = lblfcode.getText();
            String date = lbldateoftravel.getText();
            
            try{
                Conn conn = new Conn();
                String query = "insert into cancel values('"+pnr+"', '"+name+"','"+cancelno+"', '"+fcode+"', '"+date+"')";
                
                conn.s.executeUpdate(query);
                
                conn.s.executeUpdate("delete from reservation where PNR = '"+pnr+"'");
                
                JOptionPane.showMessageDialog(null,"Ticket Cancelled");
                setVisible(false);
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
       
    }
    public static void main(String[] args){
        
        new Cancel();
    }
}
