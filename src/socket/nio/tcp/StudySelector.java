package socket.nio.tcp;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class StudySelector {

	public static void main(String[] args) {
		try {
			Selector selector = Selector.open();

			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);

			SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						int keyCount = selector.select();
						Set<SelectionKey> selectionKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterator = selectionKeys.iterator();
						while (iterator.hasNext()) {
							if (keyCount == 0) {continue;}
							if (iterator.next().isAcceptable()) {
								/* 연결 수락 작업 처리 */
							} else if (iterator.next().isReadable()) {
								/* 읽기 작업 처리 */
							} else if (iterator.next().isWritable()) {
								/* 쓰기 작업 처리 */
							}
							iterator.remove();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void accept() {

	}
}
