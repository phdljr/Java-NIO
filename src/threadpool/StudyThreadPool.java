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
			System.out.println("클라이언트 접속을 기다리는 중...");
			Socket socket = serverSocket.accept();
			return socket;
		});

		Socket socket = future.get();
		System.out.println(socket.getInetAddress());
		es.shutdown();
	}
}
