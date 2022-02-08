package socket.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javafx.application.Platform;

public class StudySelectorServer {
	Selector selector;
	ServerSocketChannel serverSocketChannel;
	List<Client> connections = new Vector<Client>();

	// 데이터 통신용 클래스
	class Client {
		SocketChannel socketChannel;
		String sendData;

		Client(SocketChannel socketChannel) {
			this.socketChannel = socketChannel;
			try {
				socketChannel.configureBlocking(false);
				SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);

				selectionKey.attach(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		void receive(SelectionKey selectionKey) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(100);

			int byteCount;
			try {
				byteCount = socketChannel.read(byteBuffer);

				if (byteCount == -1) {
					throw new IOException();
				}

				String message = "요청 처리";
				// Platform.runLater(()->displayText(message));

				byteBuffer.flip();
				Charset charset = Charset.forName("UTF-8");
				String data = charset.decode(byteBuffer).toString();

				for (Client client : connections) {
					client.sendData = data;
					SelectionKey key = client.socketChannel.keyFor(selector);
					key.interestOps(SelectionKey.OP_WRITE);
				}
				selector.wakeup();
			} catch (IOException e) {
				connections.remove(this);
				String message = "통신 안됨";
				Platform.runLater(() -> {
				});
				try {
					socketChannel.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}

		void send(SelectionKey selectionKey) {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(sendData);
			try {
				socketChannel.write(byteBuffer);
				selectionKey.interestOps(SelectionKey.OP_READ);
				selector.wakeup();
			} catch (IOException e) {
				String message = "통신 안됨";
				Platform.runLater(() -> {
				});
				connections.remove(this);
				try {
					socketChannel.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	void startServer() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.bind(new InetSocketAddress(9999));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			if (serverSocketChannel.isOpen()) {
				stopServer();
				return;
			}
		}

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						int countKey = selector.select();
						if (countKey == 0) {
							continue;
						}

						Set<SelectionKey> selectionKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterator = selectionKeys.iterator();
						while (iterator.hasNext()) {
							SelectionKey selectionKey = iterator.next();
							if (selectionKey.isAcceptable()) {
								accept(selectionKey);
							} else if (selectionKey.isReadable()) {
								Client client = (Client) selectionKey.attachment();
								client.receive(selectionKey);
							} else if (selectionKey.isWritable()) {
								Client client = (Client) selectionKey.attachment();
								client.send(selectionKey);
							}
							iterator.remove();
						}
					} catch (IOException e) {
						if (serverSocketChannel.isOpen()) {
							stopServer();
							return;
						}
					}
				}
			};
		};
		thread.start();

		Platform.runLater(() -> {
			// displayText("[서버 시작]");
			// btnStartStop.setText("stop");
		});
	}

	void stopServer() {
		Iterator<Client> iterator = connections.iterator();
		try {
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socketChannel.close();

				iterator.remove();
			}
			if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
				serverSocketChannel.close();
			}
			if (selector != null && selector.isOpen()) {
				selector.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			// displayText("[서버 멈춤]");
			// btnStartStop.setText("start");
		});
	}

	// 연결 수락 코드
	void accept(SelectionKey selectionKey) {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
		try {
			SocketChannel socketChannel = serverSocketChannel.accept();
			String message = "[연결 수락: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName()
					+ "]";
			Platform.runLater(() -> {
				// displayText(message);
			});

			Client client = new Client(socketChannel);
			connections.add(client);

			Platform.runLater(() -> {
				// displayText("[연결 개수: " + connections.size() + "]");
			});
		} catch (IOException e) {
			if (serverSocketChannel.isOpen()) {
				stopServer();
			}
		}
	}

	public static void main(String[] args) {

	}

}
