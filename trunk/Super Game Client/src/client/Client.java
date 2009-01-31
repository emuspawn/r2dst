package client;

import graphics.Camera;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import connection.*;
import java.io.*;
import java.net.*;
import java.util.*;
import world.*;

public class Client extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	DrawCanvas drawer;
	Connection conn;
	ClientKeyInterpreter cki;
	KeyMap km;
	double fps;
	double afps; //average fps
	
	public Client()
	{
		super("The Super Game Network Client");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		try
		{
			File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"keyMapV1.km");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			km = new KeyMap(dis);
		}
		catch(IOException e){
			System.out.println("Unable to read files");
			e.printStackTrace();
			return;
		}
		String userName = "test";
		String serverName = "gutman.dyndns.org";
		
		System.out.println("Using a network connection for data transfer");
		try {
			conn = new NetworkConnection(InetAddress.getByName(serverName), 1164, userName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		cki = new ClientKeyInterpreter(conn);
		addKeyListener(cki);
		drawer = new DrawCanvas(this, conn);
		
		setSize(500, 500);
		add(drawer);
		setVisible(true);
		
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println("thread started");
		double start;
		double end;
		double total = 0;
		double count = 0;
		
		int oldWidth = -1, oldHeight = -1;

		for(;;)
		{
			count++;
			start = System.currentTimeMillis();
			if (drawer.getWidth() != oldWidth || drawer.getHeight() != oldHeight)
			{
				conn.sendScreenDimensions(drawer.getWidth(), drawer.getHeight());
				oldWidth = drawer.getWidth();
				oldHeight = drawer.getHeight();
			}
			
			drawer.repaint();
			//cki.relayUserActions();
			try
			{
				Thread.sleep(40);
			}
			catch(InterruptedException e){}
			end = System.currentTimeMillis();
			total += (int)(1/((end-start)/1000));
			afps = total/count;
			fps = (int)(1/((end-start)/1000));
			if(count % 70 == 0)
			{
				count = 0;
				total = 0;
			}
		}
	}
	public static void main(String[] args)
	{
		new Client();
	}
	public double getFPS()
	{
		return fps;
	}
	public double getAverageFPS()
	{
		return afps;
	}
}
class DrawCanvas extends JPanel
{
	private static final long serialVersionUID = 1L;
	Client sc;
	Connection conn;
	
	public DrawCanvas(Client sc, Connection conn)
	{
		this.sc = sc;
		this.conn = conn;
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		/*Image i = createVolatileImage(getWidth(), getHeight());
		Graphics2D g2 = (Graphics2D)g;
		paintOffScreen((Graphics2D)i.getGraphics());
		g2.drawImage(i, 0, 0, null);*/
		paintOffScreen((Graphics2D)g);
	}
	private void paintOffScreen(Graphics2D g)
	{
		//System.out.println("Entered paint");
		Camera cam = conn.getCamera();
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), getHeight());
		ArrayList<Element> e = conn.getVisibleElements();
		
		//System.out.println("display dimensions = ("+dc.getCamera().getWidth()+", "+dc.getCamera().getWidth()+")");
		//System.out.println("e.size() = "+e.size());
		
		for(int i = e.size()-1; i >= 0; i--)
		{
			e.get(i).drawElementLG(g, cam);
		}
		
		//g.setColor(Color.blue);
		//g.fillOval(getWidth()/2-15, getHeight()/2-15, 30, 30);
		//g.fillOval(getWidth()/2, getHeight()/2, 30, 30);
		
		g.setColor(Color.black);
		g.drawString("fps: "+sc.getFPS(), 3, 16);
		g.drawString("average fps: "+sc.getAverageFPS()+"", 3, 32);
		//System.out.println("Done with paint");
	}
}
