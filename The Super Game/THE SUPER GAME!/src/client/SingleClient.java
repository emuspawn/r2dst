package client;

import graphics.Camera;

import javax.swing.*;

import world.*;
import java.awt.*;
import connection.*;

import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;

public class SingleClient extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	DrawCanvas drawer;
	Connection dc;
	World w;
	ClientKeyInterpreter cki;
	KeyMap km;
	double fps;
	double afps; //average fps
	
	public SingleClient()
	{
		super("SG V1");
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
		catch(IOException e){}
		w = new World();
		String userName = "test";
		System.out.println("Using a direct connection for data transfer");
		dc = new DirectConnection(w, userName);
		cki = new ClientKeyInterpreter(dc);
		addKeyListener(cki);
		drawer = new DrawCanvas(this, dc);
		
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

			if (oldWidth != drawer.getWidth() || oldHeight != drawer.getHeight())
			{
				dc.sendScreenDimensions(drawer.getWidth(), drawer.getHeight());
				
				oldWidth = drawer.getWidth();
				oldHeight = drawer.getHeight();
			}
			
			drawer.repaint();
			
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
		new SingleClient();
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
	SingleClient sc;
	Connection dc;
	
	public DrawCanvas(SingleClient sc, Connection dc2)
	{
		this.sc = sc;
		this.dc = dc2;
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
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), getHeight());
		ArrayList<Element> e = dc.getVisibleElements();
		
		//System.out.println("display dimensions = ("+dc.getCamera().getWidth()+", "+dc.getCamera().getWidth()+")");
		//System.out.println("e.size() = "+e.size());
		
		for(int i = e.size()-1; i >= 0; i--)
		{
			e.get(i).drawElementLG(g);
		}
		g.setColor(Color.black);
		g.drawString("fps: "+sc.getFPS(), 3, 16);
		g.drawString("average fps: "+sc.getAverageFPS()+"", 3, 32);
	}
}
