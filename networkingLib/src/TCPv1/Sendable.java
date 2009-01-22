package TCPv1;
import java.util.ArrayList;


public interface Sendable {
	//This must return an array list of strings which can be passed to the constructor of the object
	//to recreate the object
	public ArrayList<String> toSendStringList();
}
