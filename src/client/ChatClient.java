package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import server.ChatServer;

public class ChatClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		
		try {
			//1. 소켓생성
			socket = new Socket();

			//2. 서버연결
			InetSocketAddress isa = new InetSocketAddress(ChatServer.IP_ADRESS, ChatServer.PORT);
			socket.connect(isa);
			System.out.println("[TCPClient] connected");
			
			//3. IOStream 받아오기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);	//들어오는데로 flushing
			
			//4. Join 프로토콜
			Scanner sc = new Scanner(System.in);
			System.out.println("닉네임>>");
			String nickName = sc.nextLine();
			pw.println("join:" + nickName);
			pw.flush();
			
			//5. 쓰기	
			while(true) {
				System.out.print(">>");
				String input = sc.nextLine();
				
				if("quit".equals(input)) {
					break;
				}
				
				pw.println("message:" + input);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && !socket.isClosed())
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//end main

}
