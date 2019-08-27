package etc;

import java.io.Writer;

public class ChatUser {
	Writer writer;
	String nickName;
	
	public ChatUser(Writer writer, String nickName) {
		this.writer = writer;
		this.nickName = nickName;
	}
	
	public Writer getWriter() {
		return writer;
	}
	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
