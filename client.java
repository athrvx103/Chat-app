import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;


class client implements ActionListener{

	JTextField send;
	
	static JFrame f=new JFrame();
	static JLabel bg;
	static Box vertical=Box.createVerticalBox();
	static DataOutputStream dout;

	String out;
	
	public static void main(String[] args){
		client cl=new client();

		try{
			Socket s=new Socket("127.0.0.1",6001);
			DataInputStream din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
		
			while(true){

				bg.setLayout(new BorderLayout());	
			
				String msg=din.readUTF();
				JPanel panel=formatLabel(msg);

				JPanel left=new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				left.setOpaque(false);
				vertical.add(left);

				vertical.add(Box.createVerticalStrut(15));
				bg.add(vertical,BorderLayout.PAGE_START);
		
				f.validate(); 
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	

	
	client(){
		
		f.getContentPane().setBackground(Color.GRAY);
		f.setLayout(null);

		f.setSize(450,700);
		f.setLayout(null);

		ImageIcon im=new ImageIcon(ClassLoader.getSystemResource("chatpaper.png"));
		bg=new JLabel(im);
		bg.setLocation(200,200);
		bg.setBounds(-10,65,420,530);
		bg.setForeground(Color.WHITE);
		f.add(bg);
	
		JPanel pl=new JPanel();
		pl.setBackground(new Color(128, 128, 128));
		pl.setLocation(0,0);
		pl.setLayout(null);
		pl.setBounds(0,0,420,65);
		f.add(pl);

		ImageIcon back=new ImageIcon(ClassLoader.getSystemResource("back - Copy.png"));
		JLabel arrow = new JLabel(back);
		arrow.setLocation(0,0);
		arrow.setBounds(15,20,25,20);
		pl.add(arrow);

		ImageIcon call=new ImageIcon(ClassLoader.getSystemResource("phone.png"));
		JLabel phone = new JLabel(call);
		phone.setLocation(0,0);
		phone.setBounds(280,20,40,44);
		pl.add(phone);

		ImageIcon p1=new ImageIcon(ClassLoader.getSystemResource("video.png"));
		JLabel i1 = new JLabel(p1);
		i1.setLocation(0,0);
		i1.setBounds(350,20,40,44);
		pl.add(i1);


		ImageIcon p2=new ImageIcon(ClassLoader.getSystemResource("bunty.jpg"));
		JLabel i2 = new JLabel(p2);
		i2.setLocation(0,0);
		i2.setBounds(60,10,50,50);
		pl.add(i2);

		i2.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae){
				new Profile();
			}
		});

		arrow.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae){
				System.exit(0);
			}
		});

		JLabel name=new JLabel("anonymous");
		name.setBounds(130,5,100,30);
		name.setFont(new Font("Times New Roman",Font.BOLD,25));
		name.setForeground(Color.WHITE);
		pl.add(name);

		JLabel status=new JLabel("Active Now");
		status.setBounds(130,40,150,10);
		status.setFont(new Font("SAN SERIF",Font.BOLD,15));
		status.setForeground(Color.WHITE);
		pl.add(status);

		JPanel pl2=new JPanel();
		pl2.setBackground(new Color(128, 128, 128));
		pl2.setLocation(0,0);
		pl2.setLayout(null);
		pl2.setBounds(0,600,320,100);
		f.add(pl2);

		send=new JTextField();
		send.setBounds(50,655,310,40);
		send.setLocation(10,10);
		pl2.add(send);

		JButton sendbut=new JButton("Send");
		sendbut.setBounds(330,610,70,40);
		sendbut.setBackground(Color.WHITE);
		sendbut.setForeground(new Color(128,128,128));
		sendbut.setFont(new Font("SAN SERIF",Font.PLAIN,10));
		sendbut.addActionListener(this);
		f.add(sendbut);
	
		f.setVisible(true);
		f.setSize(420,700);
		f.setLocation(800,50);
	}
	public void actionPerformed(ActionEvent ae){
		try{
			out=send.getText();

			JPanel p2=formatLabel(out);

			bg.setLayout(new BorderLayout());

			JPanel right=new JPanel(new BorderLayout());
			right.setBounds(out.length(),0,out.length(),20);
			right.add(p2,BorderLayout.LINE_END);
			right.setOpaque(false);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));

			bg.add(vertical,BorderLayout.PAGE_START);

			dout.writeUTF(out);	
	
			send.setText("");
		
			f.repaint();
			f.invalidate();
			f.validate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static JPanel formatLabel(String out){
		
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setOpaque(true);

		JLabel output=new JLabel("<html><p styles=\"width:150px\">"+out+"</p></html>");
		output.setFont(new Font("Times New Roman",Font.PLAIN,20));
		output.setBackground(Color.WHITE);
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(10,10,10,30));	
		
		panel.add(output);

		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

		JLabel time=new JLabel();
		time.setText(sdf.format(cal.getTime()));
		time.setForeground(Color.WHITE);

		panel.add(time);
		panel.setOpaque(false);
		panel.setBackground(Color.BLACK);
	
		return panel; 
	}

}
class Profile extends JFrame{
	Profile(){
		getContentPane().setBackground(Color.GRAY);
		setLayout(null);

		ImageIcon prof=new ImageIcon(ClassLoader.getSystemResource("bunty.jpeg"));
		JLabel disp=new JLabel(prof);
		disp.setBounds(75,200,300,300);
		setLocation(0,0);
		add(disp);

		ImageIcon back=new ImageIcon(ClassLoader.getSystemResource("back - Copy.png"));
		JLabel arrow = new JLabel(back);
		arrow.setLocation(0,0);
		arrow.setBounds(15,20,25,20);
		add(arrow);

		arrow.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae){
				setVisible(false);
				new client();
			}
		});

		setVisible(true);
		setSize(420,700);
		setLocation(60,50);
	}

}