import java.net.*;
import java.io.*;
import java.util.Arrays;

class FileSend
{
	public static void main(String[] args)
	{
		//keep trying until channel connects.
		try
		{
			ServerSocket serversocket=new ServerSocket(50000);
			//Created ServerSocket on port 50000. Acknowledge client.
			System.out.println("Running...");
			//Accept incoming Client request.
			Socket socket= serversocket.accept();
			System.out.println("Client connected.");

			//Read and Write on Socket.
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//Send File List
			String dirname="C:/Program Files/Java/jdk1.7.0_79/textfiles";
			File f1=new File(dirname);
			File fl[]=f1.listFiles();

			//Sort Alphabetically
			Arrays.sort(fl);

			//Counter for required files
			int c=0;

			for(int i=0;i<fl.length;i++)
			{
				if(fl[i].canRead() && (fl[i].toString()).endsWith(".txt"))
					c++;
			}

			pw.println(" "+c+" .txt files found. listed A-Z.");

			for(int i=0;i<fl.length;i++)
					if((fl[i].toString()).endsWith(".txt"))
						pw.println(" "+fl[i].getName()+" "+fl[i].length()+" Bytes");

			//Output String Stream delimiter
			pw.println("-");
			pw.flush();

			//Convert ASCII to Decimal value.
			String tem=br.readLine();
			int temp=Integer.parseInt(tem);
			temp-=48;
			System.out.println("Index: "+temp);

			//Check if file exists
			boolean flis=false;
			int index=0;

			if(temp>=0 && temp<=fl.length)
			{
				flis=true;
				index=temp;
			}
			else
				flis=false;
			if(flis)
			{
				try
				{
					//File Send Process, Independent
					File ff=new File(fl[index].getAbsolutePath());
					FileReader fr=new FileReader(ff);
					BufferedReader brf=new BufferedReader(fr);
					String s;

					while((s=brf.readLine())!=null)
						pw.println(s);
					//Force write buffer to Client
					pw.flush();

					if(brf.readLine()==null)
						System.out.println("File Read Successful. Closing Socket.");
				}
				catch(IOException ioe)
				{
					System.out.println("\n Error in FTP. Please try again.");
				}			
			}
			//Close streams and sockets.
			br.close();
			socket.close();
		}
		catch(Exception E)
		{
			System.out.println("\n Connection error, please try again. ");
		}
	}
}