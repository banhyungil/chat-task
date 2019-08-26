package log;

public class LogPrint {
	
	public static void logPrint(LogTypeEnum lte, String message) {
		String type = null;
		String resultMessage;
		
		switch(lte) {
		case SERVER:
			type = "[TCPServer] ";
			break;
		case CLIENT:
			type = "[TCPClient] ";
			break;
		}
		
		resultMessage = type + message;
		System.out.println(resultMessage);
	}
}
