package network.components;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Frame implements Runnable
{
	final static long serialVersionUID = 4;
	int numClients = 10;
	protected ServerSocket ss = null;
	protected Socket[] s = new Socket[numClients];
	boolean listening = true;
	DataOutputStream[] dos = new DataOutputStream[numClients]; //for sending unit locations
	DataInputStream[] dis = new DataInputStream[numClients];
	
	protected SubServer[] subs = new SubServer[numClients];
	protected TextArea ta;
	
	public Server()
	{
		super("Server");
		
		setupServerFrame();
		setupServerSocket(4444);

		Thread runner = new Thread(this);
		runner.start();
	}
	public void run()
	{
		while(listening)
		{
			addSocket();
		}
	}
	protected void addSocket()
	{
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] == null)
			{
				try
	    		{
	    			s[i] = ss.accept();
	    			ta.append("Accept Succeded \n");
	    			subs[i] = new SubServer(this, i);
	    			break;
	    		}
	    		catch(IOException e)
	    		{
	    			System.err.println("Accept Failed");
	                System.exit(1);
	    		}
			}
		}
	}
	public Socket getClient(int client)
	{
		return s[client];
	}
	private void setupServerSocket(int port)
	{
		try
    	{
    		ss = new ServerSocket(port);
    	}
    	catch(IOException e)
    	{
    		System.err.println("Could not listen on port: "+port);
            System.exit(-1);
    	}
    	ta.append("server listening on port: "+port+" \n");
    	//System.out.println("server listening on port: "+port);
	}
	private void setupServerFrame()
	{
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		ta  = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add(ta);
		setSize(300, 200);
		setResizable(false);
		setVisible(true);
	}
	public static void main(String [] args)
	{
		new Server();
	}
}
