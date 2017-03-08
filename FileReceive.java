import java.io.*;
import java.net.*;

class FileReceive
{
	public static void main(String[] args)
	{
		try
		{
			Socket socket=new Socket("127.0.0.1", 50000);

			//Read and Write on Socket.
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//Read from Console
			BufferedReader bu=new BufferedReader(new InputStreamReader(System.in));

			String s;

			while((br.read())!='-')
				System.out.println(br.readLine());

			System.out.println("Enter file index no. (beginning= 0): ");
			out.println(bu.read());
			//Force write buffer
			out.flush();

			//File Receive Process, Independent
			try
			{
				BufferedWriter pw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Received.txt")));
				while((s=br.readLine())!=null)
					pw.write(s);
				//Force write buffer to Server
				pw.close();

				if(br.readLine()==null)
					System.out.println("File write successful. Closing Socket.");
			}
			catch(IOException ioe)
			{
			}
		}
		catch(Exception E)
		{
			System.out.println("Server is down, please try again later.");
		}
	}
}