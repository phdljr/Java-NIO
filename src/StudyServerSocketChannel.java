import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class StudyServerSocketChannel {

	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true); // 블로킹 방식
			serverSocketChannel.bind(new InetSocketAddress(9999)); // 포트 바인딩

			SocketChannel socketChannel = serverSocketChannel.accept(); // 클라이언트가 접속하기 전까지 블로킹 상태
			System.out.println(socketChannel.getLocalAddress());
			
			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
