package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import log.LogPrint;
import log.LogTypeEnum;

public class ChatClientThread extends Thread{

	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	public ChatClientThread(Socket socket) {
		this.socket = socket;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);	//들어오는데로 flushing
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message;
		try {
			while(true) {
				message = br.readLine();

				if(message == null) {
					pw.println("<TCPClient> " + "서버로부터 연결 끊김");
					break;
				}
				System.out.println(message);
			}
		} catch (IOException e) {
			// TODO: handle exception
		} finally {
			try {
				if(socket != null && !socket.isClosed())
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
