package TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public abstract class TCP_Server_Callbacks {
	private int callbackVer = -1;
	
	//Data has been received on the client socket
	public abstract void DataReceived(int clientIndex, byte[] data);
	
	//A client has connected to the server socket
	public abstract void ClientConnected(Socket client, BufferedInputStream in, BufferedOutputStream out);
	
	//The client socket has caused an exception to be thrown
	//when attempting to receive. This handler is responsible
	//for removing the client and associated streams from
	//each list.
	//!!! THE CLIENT SOCKET IS DEAD WHEN THIS IS CALLED !!!
	public abstract void ReceiveException(int clientIndex, Exception e);
	
	//The server socket has caused an exception to be thrown
	//when attempting to accept connections
	//!!! THE SERVER SOCKET IS DEAD WHEN THIS IS CALLED !!!
	public abstract void ConnectException(Exception e);
	
	public void setCallbackVersion(int callVer) {
		callbackVer = callVer;
	}
	
	public int getCallbackVersion() {
		return callbackVer;
	}
}
