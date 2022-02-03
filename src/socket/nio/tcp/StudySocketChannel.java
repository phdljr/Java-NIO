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
			
			System.out.println("������ ���� �õ���...");
			socketChannel.connect(new InetSocketAddress("localhost", 9999));
			System.out.println("������ ���� ����");
			
			Charset charSet = Charset.forName("UTF-8"); // ���ڿ� �����͸� ������ ���� ��ü
			ByteBuffer byteBuffer = charSet.encode("�ȳ��ϼ���!!"); // ���ڿ� �����͸� ���ۿ� ����
			socketChannel.write(byteBuffer); // ���� ä���� ���� ������ ����
			System.out.println("������ ����");
			
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
