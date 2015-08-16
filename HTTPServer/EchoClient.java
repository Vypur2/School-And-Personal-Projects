import java.io.*;
import java.net.*;
import java.lang.*;

class EchoClient
{
	public static void main(String[] args) throws Exception
	{
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String sentence;
		String modified = "";

		try 
		{
		    Socket echoSocket = new Socket(hostName, portNumber);
		    BufferedReader inUser = new BufferedReader(
		    	new InputStreamReader(System.in));
		    DataOutputStream outServer = new DataOutputStream(echoSocket.getOutputStream());
		    BufferedReader inServer = new BufferedReader(
		    	new InputStreamReader(echoSocket.getInputStream()));

		    //sentence = inUser.readLine();
		    /*
		    sentence = "POST /cgi-bin/test.cgi HTTP/1.0" + "\r\n" 
		    		   + "Content-Length: 65" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n"
		    		   + "From: chen.cong@cs.rutgers.edu" + "\r\n" 
		    		   + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/573.36"
		    		   + "\r\n" + "\r\n" + "name=GOOG&price=%24632.74&CEO=Larry+Page&email=larry%40google.com";
		    		   */
		    sentence = "GET /cgi-bin/store.cgi HTTP/1.0";
		    outServer.writeBytes(sentence + "\r\n");
		    String line;
		    while ((line = inServer.readLine()) != null)
		    {
				System.out.println(line);
			}
			outServer.flush();
			echoSocket.close();
			inUser.close();
			inServer.close();
		}
		finally
		{
	
		}

	}
}
