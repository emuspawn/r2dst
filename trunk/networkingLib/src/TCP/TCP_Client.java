package TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_Client implements Runnable {
	private Socket sock;
	
	private BufferedInputStream in;
	private BufferedOutputStream out;
	
	private TCP_Client_Callbacks callbacks;
	
	public TCP_Client(InetAddress addr, int port, TCP_Client_Callbacks calls) throws IOException
	{
		sock = new Socket(addr, port);
		in = new BufferedInputStream(sock.getInputStream());
		out = new BufferedOutputStream(sock.getOutputStream());
		callbacks = calls;
		new Thread().start();
	}
	
	public Socket getSocket()
	{
		return sock;
	}
	
	//The returned list is read-only
	public BufferedInputStream getInStream()
	{
		return in;
	}
	
	//The returned list is read-only
	public BufferedOutputStream getOutStream()
	{
		return out;
	}
	
	public void run()
	{
		for (;;)
		{
			byte[] buffer;
			try {
				buffer = new byte[in.available()];
				in.read(buffer);
					
				callbacks.DataReceived(buffer);
			} catch (IOException e) {
				callbacks.ReceiveException(e);
				break;
			}
		}	
	}
}
