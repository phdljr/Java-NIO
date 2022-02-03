package threadpool;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StudyThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();

		Future<Socket> future = es.submit(() -> {
			ServerSocket serverSocket = new ServerSocket(9999);
			System.out.println("Ŭ���̾�Ʈ ������ ��ٸ��� ��...");
			Socket socket = serverSocket.accept();
			return socket;
		});

		Socket socket = future.get();
		System.out.println(socket.getInetAddress());
		es.shutdown();
	}
}
