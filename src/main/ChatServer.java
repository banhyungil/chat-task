package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import etc.ChatServerThread;
import etc.ChatUser;
import log.LogPrint;
import log.LogTypeEnum;

public class ChatServer {
	
	public static final int PORT = 8888;
	
	public static void main(String[] args) throws Throwable{
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		ArrayList<ChatUser> list = new ArrayList<ChatUser>();
		
		try {
			serverSocket = new ServerSocket();
			
			InetAddress ia = InetAddress.getLocalHost();				//ip주소를 뽑아온다
			
			InetSocketAddress isa = new InetSocketAddress(ia,PORT);
			serverSocket.bind(isa);										//server 자신의 ip와 특정 port를 바인드
																		//다중 ip를 가진 server의 경우 특정 ip만 할당가능
			LogPrint.logPrint(LogTypeEnum.SERVER, ia.getHostAddress() + ":" + PORT + " 연결시작");
			
			while(true) {		
				Socket socket = serverSocket.accept();						//요청을 기다린다. Deamon 상태로 진입
				
				ChatServerThread sTh = new ChatServerThread(socket, list);
				sTh.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		}
		
		
				
	}

}
