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
	
	public static Socket socket = null;
	public static PrintWriter pw = null;
	public static String nickName = null;
	
	public static void main(String[] args) {		
		
		
		try {
			//1. 소켓생성
			socket = new Socket();

			//2. 서버연결
			InetSocketAddress isa = new InetSocketAddress(ChatServer.SERVER_IP, ChatServer.PORT);
			socket.connect(isa);
			LogPrint.logPrint(LogTypeEnum.CLIENT, "connected");
			
			//3. IOStream 받아오기
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);	//들어오는데로 flushing
				
			//4. Join 요청
			requestJoin();
			
			//5. Window를 통한 사용자입력 받기 시작
			ChatWindow chatWindow =  new ChatWindow(nickName, socket);
			chatWindow.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	public static void requestJoin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("닉네임>>");
		nickName = sc.nextLine();
		pw.println("join:" + nickName);
		pw.flush();
	}
	
}
