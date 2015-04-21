
package net.sf.profiler.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class InnerSocketThread extends Thread {

	/**
	 * server
	 */
	private ServerSocket socket;

	public void run() {
		try {
			socket = new ServerSocket(50000);
			System.out.println("open agent service on port : 50000");
			while (true) {
				Socket child = socket.accept();

				child.setSoTimeout(5000);

				String command = read(child.getInputStream());

				if ("start".equals(command)) {
					TimerCount.startStatus=true;
				} else if ("status".equals(command)) {
					write(child.getOutputStream());
				} else {
					TimerCount.startStatus=false;
				}
				child.close(); //short connection
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * get request information from client socket
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String read(InputStream in) throws IOException {
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
	 * output status to Client side
	 * @param os
	 * @throws IOException
	 */
	private void write(OutputStream os) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(os);
		if (TimerCount.startStatus) {
			out.write("running".getBytes());
		} else {
			out.write("stop".getBytes());
		}
		out.write('\r');
		out.flush();
	}
	
	
}
