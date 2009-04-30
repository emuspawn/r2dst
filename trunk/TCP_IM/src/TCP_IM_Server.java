import java.io.IOException;
import java.net.Socket;
import TCP.*;

public class TCP_IM_Server extends TCP_Server_Callbacks {
	TCP_Server serv;
	
	public TCP_IM_Server() throws IOException
	{
		serv = new TCP_Server(864, this);
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

	//----- The following implement abstract methods in TCP_Server_Callbacks ------
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
				e.printStackTrace();
			}
		}
	}
	
	public void ConnectException(Exception e) {
		e.printStackTrace();
	}

	public void DataReceived(int clientIndex, byte[] data) {
		for (int i = 0; i < serv.getClientList().size(); i++)
		{
			if (i == clientIndex)
				continue;
			
			try {
				serv.getClientList().get(i).getOutputStream().write(data);
				serv.getClientList().get(i).getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void ReceiveException(int clientIndex, Exception e) {
		e.printStackTrace();
	}
}
