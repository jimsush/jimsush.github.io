package net.sf.profiler.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TProfilerClient
 * 
 */
public class TProfilerClient {

	/**
	 * 
	 * @param server
	 *            ip
	 */
	public static void start(String server) {
		doSend("start", server);
	}

	/**
	 * @param server
	 *            ip
	 */
	public static void stop(String server) {
		doSend("stop", server);
	}

	/**
	 * @param server
	 * @return
	 */
	public static String status(String server) {
		return getStatus("status", server);
	}

	/**
	 * @param command
	 * @param server
	 */
	private static void doSend(String command, String server) {
		Socket socket = null;
		try {
			socket = new Socket(server, 50000);
			OutputStream os = socket.getOutputStream();
			BufferedOutputStream out = new BufferedOutputStream(os);
			out.write(command.getBytes());
			out.write('\r');
			out.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param command
	 * @param server
	 * @return 0
	 */
	private static String getStatus(String command, String server) {
		Socket socket = null;
		try {
			socket = new Socket(server, 50000);
			OutputStream os = socket.getOutputStream();
			BufferedOutputStream out = new BufferedOutputStream(os);
			out.write(command.getBytes());
			out.write('\r');
			out.flush();
			return read(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String read(InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		StringBuffer sb = new StringBuffer();
		while (true) {
			char c = (char) bin.read();
			if (c == '\r') {
				break;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: <server ip> <command[start/stop/status]>");
			return;
		}
		if (args[1].toLowerCase().equals("start")) {
			start(args[0]);
		} else if (args[1].toLowerCase().equals("stop")) {
			stop(args[0]);
		} else {
			System.out.println(status(args[0]));
		}
	}
}
