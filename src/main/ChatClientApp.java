package main;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import etc.ChatWindow;
import log.LogPrint;
import log.LogTypeEnum;

public class ChatClientApp {

	public static void main(String[] args) {		
		Socket socket = null;
		
		try {
			//1. 소켓생성
			socket = new Socket();

			//2. 서버연결
			InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), ChatServer.PORT);
			socket.connect(isa);
			LogPrint.logPrint(LogTypeEnum.CLIENT, "connected");
			
			//3. IOStream 받아오기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);	//들어오는데로 flushing
			
			//4. 쓰레드 생성	-> 읽기만 하는 쓰레드로 소켓 넘긴다.
			//ChatClientThread cTh = new ChatClientThread(socket);
			//cTh.start();
	
			
			//4. Join 프로토콜
			Scanner sc = new Scanner(System.in);
			System.out.println("닉네임>>");
			String nickName = sc.nextLine();
			pw.println("join:" + nickName);
			pw.flush();
			
			ChatWindow chatWindow =  new ChatWindow(nickName, socket);
			Thread th = new Thread(chatWindow);
			th.start();
			chatWindow.show();
			
			
			//5. 쓰기	
//			writeOnConsole(pw, socket);
//			while(true) {
//				System.out.print(">>");
//				String input = sc.nextLine();
//				
//				if(socket.isClosed())
//					break;
//				
//				if("quit".equals(input)) {
//					pw.println("quit:");
//					break;
//				}
//				
//				pw.println("message:" + input);
//			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void writeOnConsole(PrintWriter pw, Socket socket) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print(">>");
			String input = sc.nextLine();
			
			if(socket.isClosed())
				break;
			
			if("quit".equals(input)) {
				pw.println("quit:");
				break;
			}
			
			pw.println("message:" + input);
		}
		sc.close();
	}
	
	public static void writeOnGUI() {
		
	}

}
