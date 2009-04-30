import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import TCP.TCP_Server;
import TCP.TCP_Server_Callbacks;


public class TCP_IM_Server extends TCP_Server_Callbacks {
	TCP_Server serv;
	
	public TCP_IM_Server() throws IOException
	{
		serv = new TCP_Server(864, this);
	}

	public void ClientConnected(int clientIndex, Socket client) {
		String Message = "User connected from "+client.getInetAddress().toString().substring(1)+" on port "+client.getLocalPort();
		
		for (int i = 0; i < serv.getClientList().size(); i++)
		{
			if (i == clientIndex)
				continue;
			
			try {
				serv.getClientList().get(i).getOutputStream().write(MessageToByteArray(Message));
				serv.getClientList().get(i).getOutputStream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static byte[] MessageToByteArray(String message)
	{
		byte[] sendBuff = new byte[message.length()];
		
		for (int i = 0; i < sendBuff.length; i++)
		{
			sendBuff[i] = (byte)(((int)message.charAt(i)) & 0xff);
		}
		
		return sendBuff;
	}
	
	public void ConnectException(Exception e) {
		e.printStackTrace();
	}

	public void DataReceived(int clientIndex, byte[] data) {
		for (int i = 0; i < serv.getClientList().size(); i++)
		{
			//System.out.println("Reflecting data to client "+i);
			
			if (i == clientIndex)
				continue;
			
			try {
				serv.getClientList().get(i).getOutputStream().write(data);
				serv.getClientList().get(i).getOutputStream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ReceiveException(int clientIndex, Exception e) {
		e.printStackTrace();
	}
}
