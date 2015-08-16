My HTTP Server Project for my Internet Technologies class.

Final Project: A Basic HTTP Server that can function through a browser. along with 2 python scripts that dynamically generate webpages
	       based on cookies. all stages are listed below and can be tested by modifying the "echoclient.java" file or via a browser
               for stages 3 and 4.

The first stage, SimpleHTTPServer.java is just a simple HTTP server to service "GET" requests and respond with the proper messages 
based on permissions/access rights and error checking for improper requests.

The second stage, PartialHTTPServer.java is the next itteration of the project, it services GET POST and HEAD requests. assigns and 
recognizes the "From" "content-length" "content-type" and "User-Agent" headers.

the third stage, HTTP1Server.java was the third stage, this version properly handles GET and POST requests in browsers.
it also has the functionality to run .CGI scripts through requests. a query with "POST cgi-bin/test.cgi HTTP/1.0"
would run the requested CGI script and output the programs's output to the browser. 

the final stage, HTTP1ServerASP.java, was the final iteration, services all requests from previous stages and
responds to "store.cgi" and "service.cgi", which dynamically generate web-pages using python and sending their output
through the web browser. the output of the cgi scripts is HTML formatted.

Running:
Must be run on a UNIX system for the python cgi scripts to function. windows doesn't like it. 
Unzip the zip file and compile and run the .java file. open a browser via localhost.

to test normal server functions, simply type the url of what you are looking for.
to run store or service.ci request http://localhost:3456/cgi-bin/store.cgi in your browser. 
