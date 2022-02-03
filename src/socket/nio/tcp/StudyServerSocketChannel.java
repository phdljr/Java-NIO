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
			serverSocketChannel.configureBlocking(true); // ���ŷ ���
			serverSocketChannel.bind(new InetSocketAddress(9999)); // ��Ʈ ���ε�

			System.out.println("������ ��ٸ��� ��...");
			SocketChannel socketChannel = serverSocketChannel.accept(); // Ŭ���̾�Ʈ�� �����ϱ� ������ ���ŷ ����

			ByteBuffer byteBuffer = ByteBuffer.allocate(100); // ���� �Ҵ�
			int byteCount = socketChannel.read(byteBuffer); // ���� ä���� ���� �����͸� �޾� ����. ��ȯ���� ����Ʈ ��
			byteBuffer.flip(); // ���۸� �б� ���� position�� 0���� �̵�
			Charset charset = Charset.forName("UTF-8"); // ���ۿ� ��� �����͸� ���ڿ��� �ٲٱ� ���� ��ü ����
			String message = charset.decode(byteBuffer).toString(); // ���ۿ� ��� �����͸� ���ڿ��� �ٲ�
			System.out.println("���� : " + byteCount + " : " + message);

			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
