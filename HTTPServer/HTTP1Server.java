import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.TimeZone;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.*;


public class HTTP1Server
{
	public static void main(String[] args) throws Exception
	{
		int portNumber = Integer.parseInt(args[0]);
		threadingServer server = new threadingServer(portNumber);
		new Thread(server).start();

		try {
		    Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		System.out.println("Stopping Server");
		server.stop();

	}

}

class threadingServer implements Runnable 
{
	private int portNum  = 9000;
    private ServerSocket serverSocket = null;
    private Thread curThread = null;
    int timeout = 3000;
    int  poolSize = 5;
    int  maxPoolSize = 50;
    long keepAliveTime = 5000;

    ExecutorService threadPoolExecutor =
            new ThreadPoolExecutor(
                    poolSize,
                    maxPoolSize,
                    keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>()
                    );

    private boolean isOpen = true;

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
        try 
        {
            serverSocket.setSoTimeout(timeout);
        }
        catch (SocketException se)
        {
            //timeout
            System.out.println("TIMEOUT!");
            return;
        }
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
                try 
                {
                    if (clientSocket != null)
                    {
                        DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());
                        outServer.writeBytes("HTTP/1.0 304 Service Unavailable");
                        outServer.writeBytes("Server: PartialHTTP1Server/1.0" + "\r\n");
                        outServer.writeBytes(new Date().toString());
                        outServer.flush();
                        outServer.close();
                        return;
                    }
                } 
                catch (IOException e2)
                {
                    e2.printStackTrace();
                }
    			//throw new RuntimeException("Error accepting Client Connection");
    		}
    		//new Thread(new threadWorker(clientSocket, "asdf")).start();
            try 
            {
                threadPoolExecutor.execute(new threadWorker(clientSocket, "asdf",portNum));
            }
            catch (Exception e2)
            {
                try 
                {
                    DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());
                    outServer.writeBytes("HTTP/1.0 304 Service Unavailable");
                    outServer.writeBytes("Server: PartialHTTP1Server/1.0" + "\r\n");
                    outServer.writeBytes(new Date().toString());
                    outServer.flush();
                } 
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    	}
    	System.out.println("Server Stopped");
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

    private String allow = null;
    private String expires = null;
    private String contentEncoding = null;
    private String contentLength = null;
    private String contentType = null;
    private String lastModified = null;
    private Date curDate = null;
    private int portNum = 0;

    private String versionNum = null;

	public threadWorker(Socket clientSocket,String responseText,int portNum)
	{
		this.clientSocket = clientSocket;
		this.responseText = responseText;
        this.portNum = portNum;
	}

	public void run()	
    {
		try
		{
			BufferedReader inServer = new BufferedReader(
		    	new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());

            long time = System.currentTimeMillis();
            String line2;
            String command = "";
            try 
            {
                Thread.sleep(500);                 //sleeps to give client enough time to write to the socket
            } 
            catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
            while (inServer.ready())
            {
                command += "\n" + inServer.readLine();
            }
            StringTokenizer tokenizer = null;
            if (command != null)
            {
                tokenizer = new StringTokenizer(command," \n");
            }
            //PARSE TEXT HERE//
            
            String filepath = "";
            int responseID = 0;
            if (tokenizer.hasMoreTokens())
            {
                responseID = parseQuery(tokenizer.nextToken());
            }
        
            if (responseID == 1 || responseID == 5) //services GET or HEAD
            {
                this.responseText = "HTTP/1.0 200 OK" + "\r\n";
                String holder;

                if (tokenizer.hasMoreTokens())
                {
            	   filepath = tokenizer.nextToken(); //gets filepath
                   filepath = filepath.substring(1,filepath.length());
                }
                else
                {
                    //bad request
                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }
                if (tokenizer.hasMoreTokens())
                {
                    versionNum = tokenizer.nextToken();
                    if (!versionNum.equals("HTTP/1.0"))
                    {
                        if (versionNum.length() < 8)
                        {
                            responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();

                            return;
                        }
                        else
                        {
                            if (versionNum.charAt(5) == '1')
                            {
                                if (versionNum.charAt(6) == '.')
                                {
                                    if (versionNum.charAt(7) != '0')
                                    {
                                        responseText = "HTTP/1.0 505 HTTP Version Not Supported" + "\r\n";
                                        curDate = new Date();
                                        writeToServer(outServer,responseText,curDate);
                                        outServer.flush();
                                        outServer.close();
                                        inServer.close();
                                        return;
                                    }
                                }
                                else
                                {
                                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                                    curDate = new Date();
                                    writeToServer(outServer,responseText,curDate);
                                    outServer.flush();
                                    outServer.close();
                                    inServer.close();

                                    return;
                                }
                            }
                            else if (versionNum.charAt(5) != '0')
                            {
                                responseText = "HTTP/1.0 505 Version Not Supported" + "\r\n";
                                curDate = new Date();
                                writeToServer(outServer,responseText,curDate);
                                outServer.flush();
                                outServer.close();
                                inServer.close();

                                return;
                            }
                        }
                    }
                }
                else
                {
                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }
                int day = 0;
                int hour = 0;
                if (tokenizer.hasMoreTokens())
                {
                    holder = tokenizer.nextToken();
                    if (holder.equals("If-Modified-Since:"))
                    {
                         System.out.println("found modified since clause");
                        holder = tokenizer.nextToken();
                        if(holder.length() > 7)
                        {
                            char m1 = holder.charAt(5);
                            char m2 = holder.charAt(6);
                            holder = "" + m2;
                            if (m1!='0')
                            {
                                holder += m1;
                            }
                            day = Integer.parseInt(holder);
                            m1 = holder.charAt(17);
                            m2 = holder.charAt(18);
                            holder = "" + m2;
                            if (m1!='0')
                            {
                                holder += m1;
                            }
                            hour = Integer.parseInt(holder);
                        } 
                    }
                }

                //check filepath for errors.
            	try 
            	{
                    File file = new File(filepath);
                    if (file.exists())
                    {
                        if (!file.canRead())
                        {
                            //403 forbidden
                            responseText = "HTTP/1.0 403 Forbidden" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();
                            return;
                        }
                    }
                    else
                    {
                        //not found
                        responseText = "HTTP/1.0 404 Not Found" + "\r\n";
                        curDate = new Date();
                        writeToServer(outServer,responseText,curDate);
                        outServer.flush();
                        outServer.close();
                        inServer.close();
                        return;
                    }

                    contentEncoding = "identity"; 
                    SimpleDateFormat fd = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
                    fd.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date d = new Date(file.lastModified());
                    String formattedDay = fd.format(d);
                    char m1 = formattedDay.charAt(5);
                    char m2 = formattedDay.charAt(6);
                    String hldr = "" + m2;
                    if (m1!='0')
                    {
                        hldr += m1;
                    }
                    int day2 = Integer.parseInt(hldr);
                    if (day != 0)
                    {
                        if (day > day2)
                        {
                            //304 not modified
                            responseText = "HTTP/1.0 304 Not Modified" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();
                            return;
                        }
                        else
                        {
                            m1 = formattedDay.charAt(17);
                            m2 = formattedDay.charAt(18);
                            hldr = "" + m2;
                            if (m1!='0')
                            {
                                hldr += m1;
                            }
                            int hour2 = Integer.parseInt(hldr);
                            if (hour > hour2)
                            {
                                responseText = "HTTP/1.0 304 Not Modified" + "\r\n";
                                curDate = new Date();
                                writeToServer(outServer,responseText,curDate);
                                outServer.flush();
                                outServer.close();
                                inServer.close();
                                return;
                            }

                        }
                    }

                    
                    //get length of file
                    int fileLength = (int)file.length();
                    Path path = Paths.get(file.getAbsolutePath());
                    byte[] fileData = Files.readAllBytes(path);

                    long theFuture = System.currentTimeMillis() + (86400 * 1 * 1000);
                    Date tomorrow = new Date(theFuture);
                    String tomorrowFormatted = fd.format(tomorrow); 

                    allow = "Allow: HEAD, POST, GET" + "\r\n";
                    contentEncoding = "Content-Encoding: identity"  + "\r\n";
                    contentType = "Content-Type: " + getContentType(filepath) + "\r\n";
                    contentLength = "Content-Length: " + fileLength + "\r\n";
                    lastModified = "Last-Modified: " + formattedDay + "\r\n";
                    System.out.println(lastModified);
                    expires = "Expires: " + tomorrowFormatted + "\r\n";

                    outServer.flush();
                    if (responseID == 3)
                    {
                        writeToServer(outServer,
                                      responseText,
                                      allow,
                                      contentEncoding,
                                      contentType,
                                      contentLength,
                                      lastModified,
                                      expires,
                                      fileData);
                    }
                    else
                    {
                        writeToServer(outServer,
                                      responseText,
                                      allow,
                                      contentEncoding,
                                      contentType,
                                      contentLength,
                                      lastModified,
                                      expires);
                    }

                     outServer.flush();

            	}
            	catch (FileNotFoundException f)
            	{
                    responseText = "HTTP/1.0 404 Not Found" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
            	}
                outServer.close();
                inServer.close();

            }
            else if (responseID == 10) //services HEAD
            {
                this.responseText = "HTTP/1.0 200 OK";
                String hldr2 = tokenizer.nextToken();
                filepath = tokenizer.nextToken();
                versionNum = tokenizer.nextToken();
                //tokenize filepath
                //check http version
                //check filepath for errors.
                try 
                {
                    FileReader fileIn = new FileReader(filepath);
                    BufferedReader buffIn = new BufferedReader(fileIn);
                    //contentType = ;
                    //contentLength = ;
                    contentEncoding = "identity"; 
                    //lastModified = ;

                }
                catch (FileNotFoundException f)
                {
                    responseText = "HTTP/1.0 404 Not Found" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.close();
                    inServer.close();
                    return;
                }
                //writeToServer();
            }
            else if (responseID == 2) //service POST
            {
                this.responseText = "HTTP/1.0 200 OK" + "\r\n";
                String postRequest;
                String from = null;
                String userAgent = null;
                String holder;
                String payload2 = null;
                String decodedPayload = null;
                String scriptName = null;
                String serverName = null;

                if (tokenizer.hasMoreTokens())
                {
                   filepath = tokenizer.nextToken(); //gets filepath
                   filepath = filepath.substring(1,filepath.length());
                }
                else
                {
                    //bad request
                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }
                if (tokenizer.hasMoreTokens())
                {
                    versionNum = tokenizer.nextToken();
                    if (!versionNum.equals("HTTP/1.0"))
                    {
                        if (versionNum.length() < 8)
                        {
                            responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();
                            return;
                        }
                        else
                        {
                            if (versionNum.charAt(5) == '1')
                            {
                                if (versionNum.charAt(6) == '.')
                                {
                                    if (versionNum.charAt(7) != '0')
                                    {
                                        responseText = "HTTP/1.0 505 HTTP Version Not Supported" + "\r\n";
                                        curDate = new Date();
                                        writeToServer(outServer,responseText,curDate);
                                        outServer.flush();
                                        outServer.close();
                                        inServer.close();
                                        return;
                                    }
                                }
                                else
                                {
                                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                                    curDate = new Date();
                                    writeToServer(outServer,responseText,curDate);
                                    outServer.flush();
                                    outServer.close();
                                    inServer.close();
                                    return;
                                }
                            }
                            else if (versionNum.charAt(5) != '0')
                            {
                                responseText = "HTTP/1.0 505 Version Not Supported" + "\r\n";
                                curDate = new Date();
                                writeToServer(outServer,responseText,curDate);
                                outServer.flush();
                                outServer.close();
                                inServer.close();
                                return;
                            }
                        }
                    }
                }
                else
                {
                    responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }

                //parse headers //////////////////////////////////////////////////////////////////////////////////////////////////
                if (tokenizer.hasMoreTokens())
                {
                    holder = tokenizer.nextToken();
                    if (holder.equals("Content-Type:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("From:") || holder.equals("User-Agent:"))
                        {
                            contentType = null;
                        }
                        else
                        {
                            contentType = "Content-Type: " + holder;
                        }
                    }
                    else if (holder.equals("Content-Length:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Type:") || holder.equals("From:") || holder.equals("User-Agent:"))
                        {
                           contentLength = null;
                        }
                        else
                        {
                            contentLength = "Content-Length: " + holder;
                        }
                    }
                    else if (holder.equals("From:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("Content-Type:") || holder.equals("User-Agent:"))
                        {
                            from = null;
                        }
                        else
                        {
                            from = "From: " + holder;
                        }
                    }
                    else if (holder.equals("User-Agent:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("Content-Type:") || holder.equals("From:"))
                        {
                            userAgent = null;
                        }
                        else
                        {
                            userAgent = "User-Agent: " + holder;
                        }
                    }
                }
                else
                {
                    //missing 2 headers
                }
                if (tokenizer.hasMoreTokens())
                {
                    holder = tokenizer.nextToken();
                    if (holder.equals("Content-Type:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("From:") || holder.equals("User-Agent:"))
                        {
                            contentType = null;
                        }
                        else
                        {
                            contentType = "Content-Type: " + holder;
                        }
                    }
                    else if (holder.equals("Content-Length:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Type:") || holder.equals("From:") || holder.equals("User-Agent:"))
                        {
                            contentLength = null;
                        }
                        else
                        {
                            contentLength = "Content-Length: " + holder;
                        }
                    }
                    else if (holder.equals("From:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("Content-Type:") || holder.equals("User-Agent:"))
                        {
                            from = null;
                        }
                        else
                        {
                            from = "From: " + holder;
                        }
                    }
                    else if (holder.equals("User-Agent:"))
                    {
                        holder = tokenizer.nextToken();
                        if (holder.equals("Content-Length:") || holder.equals("Content-Type:") || holder.equals("From:"))
                        {
                            //asdf
                            userAgent = null;
                        }
                        else
                        {
                            userAgent = "User-Agent: " + holder;
                        }
                    }
                }
                else
                {
                    //missing a header
                }
                //optional checks for from and useragent
                while (tokenizer.hasMoreTokens())
                {
                    holder = tokenizer.nextToken();
                    if (holder.contains("="))
                    {
                        //THATS THE PAYLOAD DUMMY
                        payload2 = holder;
                    } 
                    else if (holder.equals("From:"))
                    {
                        from = tokenizer.nextToken();
                    }
                    else if (holder.equals("User-Agent:"))
                    {
                        userAgent = "";
                    }
                    else
                    {
                        userAgent += " " + holder;
                    }
                }

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                int requestLength = 0;
                if (contentLength != null)
                {
                    int i = contentLength.length();
                    try 
                    {
                        requestLength = Integer.parseInt(contentLength.substring(16,i));
                    }
                    catch (NumberFormatException e)
                    {
                        //411 length required
                        responseText = "HTTP/1.0 411 Length Required" + "\r\n";
                        curDate = new Date();
                        writeToServer(outServer,responseText,curDate);
                        outServer.flush();
                        outServer.close();
                        inServer.close();
                        return;
                    }
                }
                else
                {
                    //411 length required
                    responseText = "HTTP/1.0 411 Length Required" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }
                if (contentType != null)
                {
                    if (!contentType.equals("Content-Type: application/x-www-form-urlencoded"))
                    {
                        //bad request
                        responseText = "HTTP/1.0 500 Internal Server Error" + "\r\n";
                        curDate = new Date();
                        writeToServer(outServer,responseText,curDate);
                        outServer.flush();
                        outServer.close();
                        inServer.close();
                        return;
                    }
                }
                else
                {
                    responseText = "HTTP/1.0 500 Internal Server Error" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }

                //url decode payload2
                StringTokenizer payloadTokenizer;
                if (payload2 != null)
                {
                    payload2 = payload2.substring(0,requestLength);
                    decodedPayload = URLDecoder.decode(payload2, "UTF-8");
                    payloadTokenizer = new StringTokenizer(decodedPayload,"&");
                }
            
                if (!filepath.contains(".cgi"))
                {
                    responseText = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    return;
                }

                //check filepath for errors.
                try 
                {
                    File file = new File(filepath);
                    if (file.exists())
                    {
                        if (!file.canRead())
                        {
                            //403 forbidden
                            responseText = "HTTP/1.0 403 Forbidden" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();
                            return;
                        }
                        if (!file.canExecute())
                        {
                            //403 forbidden
                            responseText = "HTTP/1.0 403 Forbidden" + "\r\n";
                            curDate = new Date();
                            writeToServer(outServer,responseText,curDate);
                            outServer.flush();
                            outServer.close();
                            inServer.close();
                            return;
                        }
                    }
                    else
                    {
                        //not found
                        responseText = "HTTP/1.0 404 Not Found" + "\r\n";
                        curDate = new Date();
                        writeToServer(outServer,responseText,curDate);
                        outServer.flush();
                        outServer.close();
                        inServer.close();
                        return;
                    }

                    contentEncoding = "identity"; 
                    SimpleDateFormat fd = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
                    fd.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date d = new Date(file.lastModified());
                    String formattedDay = fd.format(d);

                    
                    //get length of file
                    int fileLength = (int)file.length();

                    //
                    //CGI STUFFS HERE
                    //
                    int numEnvVariables = 4;
                    if (from != null)
                    {
                        numEnvVariables++;
                    }
                    if (userAgent != null)
                    {
                        numEnvVariables++;
                    } 
                    String[] envi = new String[numEnvVariables];
                    if (decodedPayload != null)
                    {
                        envi[0] = "CONTENT_LENGTH=" + decodedPayload.length();
                    }
                    else
                    {
                        envi[0] = "CONTENT_LENGTH=" + requestLength;
                    }
                    envi[1] = "SCRIPT_NAME=" + filepath;
                    envi[2] = "SERVER_NAME=" + "HTTP1Server";
                    envi[3] = "SERVER_PORT=" + portNum;
                    if (numEnvVariables > 4)
                    {
                        int i = 4;
                        envi[4] = "HTTP_FROM=" + from;
                        i++;
                        if (i <= numEnvVariables)
                        {
                            envi[i] = "HTTP_USER_AGENT=" + userAgent;
                        }
                    }
                    Runtime rt = Runtime.getRuntime();
                    //Process proc = rt.exec(filepath,envi);
                    String fullComand = envi[0] + filepath;
                    Process proc = rt.exec(filepath,envi);
                    InputStream procin = null; //FROM PROCESS
                    InputStream stderr = null; //FROM PROCESS
                    OutputStream stdout = null; //TO PROCESS
                    stdout = proc.getOutputStream();
                    if (decodedPayload != null)
                    {
                        byte[] b1 = decodedPayload.getBytes();
                        stdout.write(b1);
                        stdout.flush();
                    }
                    else
                    {
                        byte[] b1 = "".getBytes();
                        stdout.write(b1);
                        stdout.flush();
                    }
                    //OutputStreamWriter writer = new OutputStreamWriter(stdout);
                    //writer.flush();
                    //writer.close();
                    procin = proc.getInputStream();
                    stderr = proc.getErrorStream();
                    Reader stderrr = new InputStreamReader(stderr);
                    Reader stdinr = new InputStreamReader(procin);
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    String line = "";
                    int c;

                    while ((c = stdinr.read()) != -1)
                    {
                        //line += (char)c;
                        buffer.write(c);
                    }
                    buffer.flush();

                   // byte[] b2 = line.getBytes();
                    byte[] b2 = buffer.toByteArray();
                    for (int i = 0;i<b2.length;i++)
                    {
                        System.out.print((char)b2[i]);
                    }
                    if (!line.equals(""))
                    {
                        long theFuture = System.currentTimeMillis() + (86400 * 1 * 1000);
                        Date tomorrow = new Date(theFuture);
                        String tomorrowFormatted = fd.format(tomorrow);

                        allow = "Allow: HEAD, POST, GET" + "\r\n";
                        contentEncoding = "Content-Encoding: identity"  + "\r\n";
                        contentType = "Content-Type: " + "text/html" + "\r\n";
                        contentLength = "Content-Length: " + b2.length + "\r\n";
                        System.out.println(b2.length);
                        lastModified = "Last-Modified: " + formattedDay + "\r\n";
                        expires = "Expires: " + tomorrowFormatted + "\r\n";

                        writeToServer(outServer,
                                      responseText,
                                      allow,
                                      contentEncoding,
                                      contentType,
                                      contentLength,
                                      lastModified,
                                      expires,
                                      b2);
                    }
                    else if (b2.length != 0)
                    {
                        long theFuture = System.currentTimeMillis() + (86400 * 1 * 1000);
                        Date tomorrow = new Date(theFuture);
                        String tomorrowFormatted = fd.format(tomorrow);

                        allow = "Allow: HEAD, POST, GET" + "\r\n";
                        contentEncoding = "Content-Encoding: identity"  + "\r\n";
                        contentType = "Content-Type: " + "text/html" + "\r\n";
                        contentLength = "Content-Length: " + b2.length + "\r\n";
                        System.out.println(b2.length);
                        lastModified = "Last-Modified: " + formattedDay + "\r\n";
                        expires = "Expires: " + tomorrowFormatted + "\r\n";

                        writeToServer(outServer,
                                      responseText,
                                      allow,
                                      contentEncoding,
                                      contentType,
                                      contentLength,
                                      lastModified,
                                      expires,
                                      b2);
                    }
                    else
                    {
                        System.out.println("ASSDJBGDSUFJGBASDFUJSDBF WTF IS GOING ON");
                        responseText = "HTTP/1.0 204 No Content" + "\r\n";
                        curDate = new Date();
                        writeToServer(outServer,responseText,curDate);
                        outServer.flush();
                        outServer.close();
                        inServer.close();
                        return;
                    }


                    //Process process = new ProcessBuilder(
                    //filepath,"param1","param2").start();
                    /*
                    ProcessBuilder cgiProcess = new ProcessBuilder(filepath,"Args1","args2");
                    Map<String, String> envi = cgiProcess.environment();
                    envi.put("CONTENT_LENGTH","40");
                    Process process = cgiProcess.start();
                    System.out.println("process started");
                    InputStream procin = null; //FROM PROCESS
                    InputStream stderr = null; //FROM PROCESS
                    OutputStream stdout = null; //TO PROCESS
                    */
                    /*
                    //stdout = process.getOutputStream();
                    //procin = process.getInputStream();
                    //Reader stdinr = new InputStreamReader(procin);
                    
                    String line = "";
                    int c;
                    while ((c = stdinr.read()) != -1)
                    {
                        System.out.println((char)c);
                    }
                    
                    outServer.writeBytes(line);
                    System.out.println(line);
                    */

                }
                catch (FileNotFoundException f)
                {
                    responseText = "HTTP/1.0 404 Not Found" + "\r\n";
                    curDate = new Date();
                    writeToServer(outServer,responseText,curDate);
                    outServer.flush();
                    outServer.close();
                    inServer.close();
                    System.out.println("Request Processed: 404 Not Found");
                    return;
                }
    
                outServer.close();
                inServer.close();
                return;
            }
            else if (responseID == 3) //bad request
            {
                responseText = "HTTP/1.0 400 Bad Request" + "\r\n";
                curDate = new Date();
                writeToServer(outServer,responseText,curDate);
                outServer.close();
                inServer.close();
                System.out.println("Request processed: " + time);
                return;
            }
            else if (responseID == 8) //not implemented
            {
                responseText = "HTTP/1.0 501 Not Implemented" + "\r\n";
                curDate = new Date();
                writeToServer(outServer,responseText,curDate);
                outServer.close();
                inServer.close();
                return;
            }

		} 
		catch (IOException e)
		{
            //report exception somewhere.
            e.printStackTrace();
        }

	}

	public int parseQuery(String request)
	{
		int response;
		if (request.length() < 3)
		{
			return response = 3;
		}
		else
		{
			StringTokenizer tokenizer = new StringTokenizer(request, " \n");
			String token = tokenizer.nextToken();
			int i = token.length();
			int j = 0;
			while (j < i)
			{
				if (!Character.isUpperCase(token.charAt(j)))
				{
					return response = 3;
				}
				j++;
			}
			if (!token.equals("GET"))
			{
                if (token.equals("POST"))
                {
                    return response = 2;
                }
                else if (token.equals("HEAD"))
                {
                    return response = 5;
                }
                else if (token.equals("PUT") 
                    || token.equals("DELETE") 
                    || token.equals("TRACE") 
                    || token.equals("CONNECT") 
                    || token.equals("OPTIONS")
                    || token.equals("LINK")
                    || token.equals("UNLINK"))
                {
                    return response = 8;
                }
                else
                {
                    return response = 3;
                }
			}
			if (request.substring(0,3).equals("GET"))
			{
				return response = 1;
			}
			else
			{
				return response = 3;
			}
		}
	}
    public void writeToServer(DataOutputStream outServer,
                              String responseText,
                              String allow, 
                              String contentEncoding, 
                              String contentType,
                              String contentLength,
                              String lastModified,
                              String expires,
                              byte[] payload)
    {

        try 
        {

            outServer.writeBytes(responseText + allow + contentEncoding + lastModified + contentType + contentLength + expires + "\r\n");
            outServer.write(payload);
            outServer.flush();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToServer(DataOutputStream outServer,
                              String responseText,
                              String allow, 
                              String contentEncoding, 
                              String contentType,
                              String contentLength,
                              String lastModified,
                              String expires)
    {

        try 
        {

            outServer.writeBytes(responseText + allow + contentEncoding + lastModified + contentType + contentLength + expires + "\r\n");      
            outServer.flush();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void writeToServer(DataOutputStream outServer,
                              String responseText,
                              Date current)
    {
        try 
        {
            outServer.writeBytes(responseText);
            outServer.writeBytes("Server: PartialHTTP1Server/1.0" + "\r\n");
            outServer.writeBytes(current.toString());
            outServer.flush();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getContentType(String fileRequested)
    {
        if (fileRequested.endsWith(".htm") ||
            fileRequested.endsWith(".html"))
        {
            return "text/html";
        }
        else if (fileRequested.endsWith(".gif"))
        {
            return "image/gif";
        }
        else if (fileRequested.endsWith(".pdf"))
        {
            return "application/pdf";
        }
        else if (fileRequested.endsWith(".jpg") ||
                 fileRequested.endsWith(".jpeg"))
        {
            return "image/jpeg";
        }
        else if (fileRequested.endsWith(".png"))
        {
            return "image/png";
        }
        else if (fileRequested.endsWith(".class") ||
                 fileRequested.endsWith(".jar"))
        {
            return "application/octet-stream";
        }
        else
        {
            int i = 0;
            while (i < fileRequested.length())
            {
                if (fileRequested.charAt(i)=='.')
                {
                    return "text/plain";
                }
                i++;
            }
            return "application/octet-stream";
        }
    }
}
