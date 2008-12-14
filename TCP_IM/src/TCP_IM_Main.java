import java.net.InetAddress;
import java.util.Scanner;




public class TCP_IM_Main {

	public static void main(String[] args) {
		String name = null;
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Please type your name: ");
		if (scan.hasNext())
			name = scan.next().trim();
		
		try {
			//Comment this next line out for a client-only setup
			new TCP_IM_Server();
			
			new TCP_IM_Client(name, InetAddress.getLocalHost());
		} catch (Exception e) {
			System.out.println("Connection failed - "+e.getMessage());
		}
	}

}
