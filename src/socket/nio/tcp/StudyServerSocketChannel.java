package socket.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class StudyServerSocketChannel {

	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true); // 블로킹 방식
			serverSocketChannel.bind(new InetSocketAddress(9999)); // 포트 바인딩

			System.out.println("연결을 기다리는 중...");
			SocketChannel socketChannel = serverSocketChannel.accept(); // 클라이언트가 접속하기 전까지 블로킹 상태

			ByteBuffer byteBuffer = ByteBuffer.allocate(100); // 버퍼 할당
			int byteCount = socketChannel.read(byteBuffer); // 소켓 채널을 통해 데이터를 받아 들임. 반환값은 바이트 수
			byteBuffer.flip(); // 버퍼를 읽기 위해 position을 0으로 이동
			Charset charset = Charset.forName("UTF-8"); // 버퍼에 담긴 데이터를 문자열로 바꾸기 위한 객체 생성
			String message = charset.decode(byteBuffer).toString(); // 버퍼에 담긴 데이터를 문자열로 바꿈
			System.out.println("응답 : " + byteCount + " : " + message);

			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
