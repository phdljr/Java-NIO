import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class StudyServerSocketChannel {

	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true); // ���ŷ ���
			serverSocketChannel.bind(new InetSocketAddress(9999)); // ��Ʈ ���ε�

			SocketChannel socketChannel = serverSocketChannel.accept(); // Ŭ���̾�Ʈ�� �����ϱ� ������ ���ŷ ����
			System.out.println(socketChannel.getLocalAddress());
			
			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
