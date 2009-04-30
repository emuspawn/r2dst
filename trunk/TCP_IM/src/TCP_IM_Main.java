import java.net.InetAddress;
import java.util.Scanner;




public class TCP_IM_Main {

	public static void main(String[] args) {

		String name = null, ip = null;
		
		if (args.length != 2) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Please type your name: ");
		if (scan.hasNextLine())
			name = scan.nextLine().trim();
		
		System.out.print("Please type the server's external IP address: ");
		if (scan.hasNext())
			ip = scan.nextLine().trim(); 
		}
		else
		{
			name = args[0];
			ip = args[1];
		}
		
		try {
			//Comment this next line out for a client-only setup
			//new TCP_IM_Server();
			
			new TCP_IM_Client(name, InetAddress.getByName(ip));
		} catch (Exception e) {
			System.out.println("Connection failed - "+e.getMessage());
		}
	}

}
