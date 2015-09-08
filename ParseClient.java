import java.io.*; 
import java.nio.*;
import java.net.*; 
class ParseClient{ 

    public static void main(String argv[]) throws Exception 
    { 
        String sentence; 
        String modifiedSentence; 
        
        Socket clientSocket = new Socket("127.0.0.1", 6789);
//Create client socket that connects to server on port 6789

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
	
	DataInputStream dataInput = new DataInputStream(clientSocket.getInputStream());

	int counter = 0;

	while(true){
			
			int b = dataInput.readUnsignedByte();

			if (counter == 12) {
				System.out.println();
				counter=0;

			} else {
				//if (counter==11) {
					System.out.print(b + " ");
				//}
			}
			counter++;
		//Interpret the bytes that are read to display mote id, sequence number and temperature as they are being read from the mote
	}
    }
}


