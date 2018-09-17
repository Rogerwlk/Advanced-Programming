//ChatterServer.java
//Last edited: 3/12/2017
//Author: Roger Wang

package rw794;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatterServer
{
	private ServerSocket listener = null;
	private ArrayList<String> names = new ArrayList<String>();
	// store all the names for /dm commands
	private ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
	// store all the writers for output

	public ChatterServer(int port)
	{
		try
		{
			listener = new ServerSocket(port); // start server
			System.out.println("Server starts running.");
			System.out.println("Use Ctrl+C to exit.");
			while (true)
			{
				Socket socket = listener.accept();
				// listen to new client and create a thread for each client
				try
				{
					new Thread(new Client(socket)).start();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException
	{
		if (args.length < 1)
			throw new IllegalArgumentException("Format: java filename port");

		new ChatterServer(Integer.valueOf(args[0])); // run the server
	}

	public class Client implements Runnable
	{
		// store accepted socket for input and output
		private BufferedReader in = null;
		private PrintWriter out = null;

		public Client(Socket socket) throws IOException
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}

		@Override
		public void run()
		{
			try
			{
				String name = "Anonymous" + (names.size() + 1);
				// set default name to the client
				boolean flag = true;
				synchronized (names) // block other changes to names
				{
					names.add(name);
				}
				synchronized (writers)
				{
					for (PrintWriter writer : writers)
					{
						writer.println("Welcome " + name + " to the group chat.");
					} // informing others of a new member
					writers.add(out);
				}
				// welcome message
				out.println("Your current nickname: " + name);
				out.println("There are " + names.size() + " people in the group chat.");
				out.println("\nOptions:");
				out.println("msg				send message to all the people in the group chat");
				out.println("/nick name			change current nickname");
				out.println("/dm name msg		send message to specific people");
				out.println("/quit				quit program\n");

				while (flag)
				{
					String msg = in.readLine();
					if (msg == null) // in case of forced exit by client
						break;
					System.out.println("Server: read =" + msg); // debug purpose
					String[] input = msg.trim().split("\\s+");
					// split received message
					if (input[0].equals("/quit")) // client exit
					{
						synchronized (writers)
						{
							synchronized (names)
							{
								for (int i = 0; i < writers.size(); i++)
								{
									// Because of repetitive names, the name is
									// removed by comparing "out" with
									// "writers".
									if (out.equals(writers.get(i)))
									{
										names.remove(i);
										writers.remove(i);
										flag = false;
										// exit while loop
										System.out.println("Remove successful.");
										// debug purpose
										break;
									}
								}
							}
						}
						// inform other group members that someone quits
						for (PrintWriter writer : writers)
						{
							writer.println(name + " quits the group chat.");
						}
					}
					else if (input[0].equals("/nick")) // change nickname
					{
						if (input.length <= 1) // incorrect format
						{
							out.println("Format: /nick name");
						}
						else
						{
							for (int i = 0; i < writers.size(); i++)
							{
								if (out.equals(writers.get(i)))
								{
									synchronized (names)
									{
										names.set(i, input[1]); // change names
									}
									for (PrintWriter writer : writers)
									{
										writer.println(name + " changes nickname to " + input[1]);
									}
									name = input[1];// change local variable
									break;
								}
							}
						}
					}
					else if (input[0].equals("/dm"))// direct message
					{
						if (input.length <= 2) // incorrect format
						{
							out.println("Format: /dm name msg");
						}
						else
						{
							// cut out /dm name
							msg = msg.substring(msg.indexOf(input[1]) + input[1].length() + 1);
							for (int i = 0; i < writers.size(); i++)
							{
								if (input[1].equals(names.get(i)))
								// find user to direct message
								{
									writers.get(i).println(name + ": " + msg);
								}
							}
						}
					}
					else // send message to all other members
					{
						for (PrintWriter writer : writers)
						{
							if (!writer.equals(out))
							{
								writer.println(name + ": " + msg);
							}
						}
					}

				} // end of while
				in.close();
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}