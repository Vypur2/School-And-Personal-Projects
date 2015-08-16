import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.StringTokenizer;

public class SimpleHTTPServer
{
	public static void main(String[] args) throws Exception
	{
		int portNumber = Integer.parseInt(args[0]);                 //parse arguement for port
		threadingServer server = new threadingServer(portNumber);   //start a new server thread
		new Thread(server).start();                                 //

		try {
		    Thread.sleep(60 * 1000);                                //disconnects server if no request is sent within 60 seconds
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		System.out.println("Stopping Server");
		server.stop();

	}

}

class threadingServer implements Runnable
{
	private int portNum  = 9000;                             // placeholder initialization
    private ServerSocket serverSocket = null;                // initial socket
    private Thread curThread = null;  
                           // current working thread
    private boolean isOpen = true;                           // is server open?

    public threadingServer(int port)
    {
    	this.portNum = port;
    }

    public void run()
    {
    	synchronized(this)
    	{
    		this.curThread = Thread.currentThread();
    	}

    	openServerSocket();
    	while (isOpen())
    	{
    		Socket clientSocket = null;
    		try
    		{
    			clientSocket = this.serverSocket.accept();
    		}
    		catch (IOException e)
    		{
    			if (!isOpen())
    			{
    				System.out.println("Server Closed");
    				return;
    			}
    			throw new RuntimeException("Error accepting Client Connection");
    		}
    		new Thread(new threadWorker(clientSocket, "asdf")).start();       //spawn new worker thread for every request after accept
    	}
    }

    private synchronized boolean isOpen()
    {
    	return this.isOpen;
    }

    public synchronized void stop(){
        this.isOpen = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() 
    {
        try {
            this.serverSocket = new ServerSocket(this.portNum);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + portNum, e);
        }
    }

}

class threadWorker implements Runnable
{
	private Socket clientSocket;
	private String responseText;

	public threadWorker(Socket clientSocket,String responseText)
	{
		this.clientSocket = clientSocket;
		this.responseText = responseText;
	}

	public void run()
	{
		try
		{
			BufferedReader inServer = new BufferedReader(
		    	new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());
         
            String command = inServer.readLine();      //initial command

            //PARSE ALL TEXT HERE//
            this.responseText = parseQuery(command);        //checks format of string
            String filepath;
            if (responseText.substring(0,6).equals("200 OK"))
            {
            	//get the file here
                StringTokenizer st = new StringTokenizer(command);  //checks filepath for spaces and format errors
                int numtoks;
                filepath = st.nextToken();
                filepath = st.nextToken();
                if (filepath == null)
                {
                    this.responseText = "400 Bad Request";
                    outServer.writeBytes(this.responseText + '\n');
                    outServer.close();
                    inServer.close();
                    return;
                }
                numtoks = 2;
            	filepath = filepath.substring(1,filepath.length());

                while (st.hasMoreTokens())
                {
                    String line = "";
                    line = st.nextToken();
                    numtoks++;
                }

                if (numtoks > 2)
                {
                    this.responseText = "400 Bad Request";
                    outServer.writeBytes(this.responseText + '\n');
                    outServer.close();
                    inServer.close();
                    return;
                } 
                else if (filepath.length() < 2)
                {
                    this.responseText = "400 Bad Request";
                    outServer.writeBytes(this.responseText + '\n');
                    outServer.close();
                    inServer.close();
                    return;
                }
                else 
                {
                    int y = 0;
                    while (y < filepath.length())
                    {
                        if (filepath.charAt(y)=='.')
                        {
                            break;
                        }
                        y++;
                    }
                    if (y == filepath.length())
                    {
                        this.responseText = "400 Bad Request";
                        outServer.writeBytes(this.responseText + '\n');
                        outServer.close();
                        inServer.close();
                        return;
                    }
                }

            	try                                                    //if it made it here, it is confirmed a good request
            	{
            		FileReader fileIn = new FileReader(filepath);
            		BufferedReader buffIn = new BufferedReader(fileIn);
            		StringBuilder everything = new StringBuilder();
            		String line;
            		while( (line = buffIn.readLine()) != null) {
            		   everything.append(line);
            		}
            		this.responseText += ('\n');
            		this.responseText += ('\n');
            		this.responseText += (everything.toString());
            	}
            	catch (FileNotFoundException f)
            	{
            		this.responseText = "404 Not Found";
            	}
            }
            outServer.writeBytes(this.responseText + '\n');
            //outServer.flush();
            outServer.close();
            inServer.close();
		} 
		catch (IOException e)
		{
            this.responseText = "500 Internal Error";
        }
	}

	public String parseQuery(String request)
	{
		String response;
		if (request.length() < 5)
		{
			return response = "400 Bad Request";
		}
		else
		{
			StringTokenizer tokenizer = new StringTokenizer(request);
			String token = tokenizer.nextToken();
			int i = token.length();
			int j = 0;
			while (j < i)
			{
				if (!Character.isUpperCase(token.charAt(j)))
				{
					return response = "400 Bad Request";
				}
				j++;
			}
			if (request.charAt(token.length())==' ' && !token.equals("GET"))
			{
                if (request.charAt(token.length()+1)=='/')
                {
                    return response = "501 Not Implemented";
                }
                else
                {
                    return response = "400 Bad Request";
                }
			}
			if (request.substring(0,5).equals("GET /"))
			{
				return response = "200 OK";
			}
			else
			{
				return response = "400 Bad Request";
			}
		}
	}
}