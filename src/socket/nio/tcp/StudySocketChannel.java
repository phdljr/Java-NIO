package socket.nio.tcp;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class StudySocketChannel {

	public static void main(String[] args) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			
			System.out.println("서버와 연결 시도중...");
			socketChannel.connect(new InetSocketAddress("localhost", 9999));
			System.out.println("서버와 연결 성공");
			
			Charset charSet = Charset.forName("UTF-8"); // 문자열 데이터를 보내기 위한 객체
			ByteBuffer byteBuffer = charSet.encode("안녕하세요!!"); // 문자열 데이터를 버퍼에 저장
			socketChannel.write(byteBuffer); // 소켓 채널을 통해 데이터 전송
			System.out.println("데이터 보냄");
			
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
