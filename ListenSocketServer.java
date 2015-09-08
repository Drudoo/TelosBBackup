import java.io.*;
import java.net.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;
import net.tinyos.message.*;

public class ListenSocketServer {
    public static void main(String args[]) throws IOException {
        String source = null;
        PacketSource reader;
        if (args.length == 2 && args[0].equals("-comm")) {
          source = args[1];
        }
	else if (args.length > 0) {
	    System.err.println("usage: java net.tinyos.tools.Listen [-comm PACKETSOURCE]");
	    System.err.println("       (default packet source from MOTECOM environment variable)");
	    System.exit(2);
	}
        if (source == null) {	
  	  reader = BuildSource.makePacketSource();
        }
        else {
  	  reader = BuildSource.makePacketSource(source);
        }
	if (reader == null) {
	    System.err.println("Invalid packet source (check your MOTECOM environment variable)");
	    System.exit(2);
	}

	try {
	  reader.open(PrintStreamMessenger.err);
	  ServerSocket welcomeSocket = new ServerSocket(6789); 
		//create server socket that listens on 6789
  		System.out.println("Server Created");

	  while(true) { 
  
	    try {
         	Socket connectionSocket = welcomeSocket.accept();
	  	System.out.println("Client connected");
	    	DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
	    	
	    for (;;) {
		byte[] packet = reader.readPacket();
		Dump.printPacket(System.out, packet);
		for (int i=0; i<packet.length; i++) {
		    outToClient.writeByte(packet[i]);
		}
		System.out.println();
		System.out.flush();
	    }
	    }
	    catch(IOException e) {
	    }

	  }
	}
	catch (IOException e) {
	    System.err.println("Error on " + reader.getName() + ": " + e);
	}
    }
}

