package etc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import log.LogPrint;
import log.LogTypeEnum;

public class ChatServerThread extends Thread{

	Socket socket;
	ArrayList<ChatUser> userList;
	ChatUser chatUser;
	
	public ChatServerThread(Socket socket,ArrayList userList) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.userList = userList;
	}
	
	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetSocketAddress irsa = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteIp = irsa.getHostString();
			int remotePort = irsa.getPort();
			
			LogPrint.logPrint(LogTypeEnum.SERVER, "connected from client [" + remoteIp + ":" + remotePort + "]" );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);	//들어오는데로 flushing

			String request;
			
			//1. Client Data를 읽는다. Data에 따라 처리를한다.
			while(true) {						//client data를 읽는다. null이면 종료
				//1-1 데이터를 읽는다
				request = br.readLine();
				
				if(request == null) {
					LogPrint.logPrint(LogTypeEnum.SERVER, "클라이언트로 연결 끊김");
					doQuit();
					break;
				}
				
				//1-2 토큰을 통해 식별자를 구별한다
				String[] tokens = request.split(":");
				
				switch (tokens[0]) {
				case "join":
					doJoin(tokens[1], pw);
					break;
				case "message":
					if(tokens.length == 1) {
						doMessage(" ");
						break;
					}
						
					doMessage(tokens[1]);
					continue;
				case "quit":
					doQuit();
				}
				LogPrint.logPrint(LogTypeEnum.SERVER, "received :" + request );
								
			}


		} catch (Exception e) {
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

	}//end run
	
	private void doJoin(String nickName, Writer writer) {
		//ChatUser를 생성
		chatUser = new ChatUser(writer, nickName);
	
		//UserList에 User를 등록
		synchronized (userList) {
			userList.add(chatUser);
		}
		String message = nickName + "님이 입장 하였습니다.";
		broadcast(message);
	}
	
	private void doMessage(String message) {			//client에게 message를 보낸다
		broadcast(message);
	}
	
	private void doQuit() {				
		String nickName = chatUser.getNickName();
		removeUser();										//list자원 해제하고 socket을 닫아준다
		
		String message = nickName + "님이 퇴장 하였습니다.";
		broadcast(message);									//broadcast한다.
	}
	
	private void removeUser() {			//list자원 해제한다.
		synchronized (userList) {
			userList.remove(chatUser);
		}
	}
	
	private void broadcast(String message) {			
		synchronized (userList) {
			
			for(ChatUser user : userList) {
				PrintWriter pw = (PrintWriter)user.getWriter();
				pw.println(chatUser.getNickName() + ":"+ message);
				pw.flush();
			}
		}
	}
}
