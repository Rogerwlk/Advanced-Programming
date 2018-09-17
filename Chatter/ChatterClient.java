//ChatterClient.java
//Last edited: 3/12/2017
//Author: Roger Wang

package rw794;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatterClient
{
	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private Scanner console = null;

	public ChatterClient(String serverName, int serverPort) throws IOException, UnknownHostException
	{
		System.out.println("Establishing connection. Please wait ...");
		// Connect to the server
		socket = new Socket(serverName, serverPort);
		// read received data from server
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// read console input
		console = new Scanner(System.in);
		// send data to server
		out = new PrintWriter(socket.getOutputStream(), true);
		String msg;
		System.out.println("Welcome to the group chat!");
		// thread for listening to server
		new Thread(new Listener()).start();

		// reading and sending inputs from console
		while (true)
		{
			// reading from console
			msg = console.nextLine();
			// send data to server
			out.println(msg);
			if (msg.equals("/quit"))
			{
				in.close();
				console.close();
				out.close();
				socket.close();
				System.exit(0);
			}
		}
	}

	public class Listener implements Runnable
	{
		@Override
		public void run()
		{
			String msg;
			while (true)
			{
				try
				{
					// receive data from server
					msg = in.readLine();
					// connection interrupted
					if (msg == null)
					{
						System.out.println("Server shutdown. The program will exit.");
						in.close();
						out.close();
						console.close();
						socket.close();
						System.exit(0);
					}
					System.out.println(msg);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[])
	{
		if (args.length < 2)
			System.out.println("Format: java ChatterClient host port");
		else
			try
			{
				new ChatterClient(args[0], Integer.parseInt(args[1]));
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (UnknownHostException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
