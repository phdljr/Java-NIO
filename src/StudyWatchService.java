import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudyWatchService extends Application {
	class WatchServiceThread extends Thread {
		@Override
		public void run() {
			WatchService watchService;
			try {
				watchService = FileSystems.getDefault().newWatchService();
				Path directory = Paths.get("C:/test");
				directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

				while (true) {
					WatchKey watchKey = watchService.take(); //블로킹 상태가 됨
					List<WatchEvent<?>> list = watchKey.pollEvents();

					for (WatchEvent<?> watchEvent : list) {
						Kind<?> kind = watchEvent.kind();
						Path path = (Path) watchEvent.context();

						if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
							Platform.runLater(() -> {
								textArea.appendText("파일 생성됨 -> " + path.getFileName() + "\n");
							});
						} else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
							Platform.runLater(() -> {
								textArea.appendText("파일 수정됨 -> " + path.getFileName() + "\n");
							});
						} else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
							Platform.runLater(() -> {
								textArea.appendText("파일 삭제됨 -> " + path.getFileName() + "\n");
							});
						} else if (kind == StandardWatchEventKinds.OVERFLOW) {
							// 운영체제가 알아서 함
						}
					}

					boolean valid = watchKey.reset();
					if (!valid) {
						break;
					}
				}
				watchService.close();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private TextArea textArea;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(500, 300);

		textArea = new TextArea();
		textArea.setEditable(false);
		root.setCenter(textArea);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("WatchService 예제");
		primaryStage.setOnCloseRequest(e->{
			Platform.exit();
		});
		primaryStage.show();

		WatchServiceThread wsth = new WatchServiceThread();
		wsth.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
