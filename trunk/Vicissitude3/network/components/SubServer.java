package network.components;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.io.*;
import network.protocol.*;

public class SubServer extends Frame implements Runnable
{
	protected int client;
	protected DataOutputStream dos; //for sending unit locations
	protected DataInputStream dis;
	Server server;
	boolean justConnected = true;
	
	protected OutputProtocol op = new OutputProtocol();
	protected InputProtocol ip = new InputProtocol();
	protected TextArea ta;
	
	public SubServer(Server server, int client)
	{
		super("Sub Server");
		this.client = client;
		setLocation(300*client, 200);
		setupSubServerFrame();
		this.server = server;
		try
		{
			dis = new DataInputStream(server.getClient(client).getInputStream());
			dos = new DataOutputStream(server.getClient(client).getOutputStream());
		}
		catch(IOException e){}
		
		Thread runner = new Thread(this);
		runner.start();
	}
	public void run()
	{
		for(;;)
		{
			newConnectionFunctions();
			sendData();
			receiveData();
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e){}
		}
	}
	protected void sendData(){}
	protected void receiveData(){}
	public void newConnectionFunctions()
	{
		if(justConnected)
		{
			try
			{
				int a = dis.readInt();
				ta.append("data received, connected \n");
				if(a == 1)
				{
					dos.writeInt(client);
					ta.append("client number sent, "+client+" \n");
				}
				justConnected = false;
			}
			catch(IOException e){}
		}
	}
	private void setupSubServerFrame()
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
}
