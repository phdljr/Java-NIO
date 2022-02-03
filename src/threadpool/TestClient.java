package threadpool;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Á¢¼Ó");
		Socket socket = new Socket("localhost", 9999);
	}
}
