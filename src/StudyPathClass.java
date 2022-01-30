import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudyPathClass {
	public static void main(String[] args) {
		Path path = Paths.get("C:/test/cc");
		// System.out.println(Files.isDirectory(path));

		if (Files.notExists(path)) { // 해당 경로가 존재하지 않다면
			System.out.println("존재하지 않음으로 생성");
			try {
				Files.createDirectories(path); // 경로상에 존재하지 않는 모든 디렉토리 생성
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		path = Paths.get("C:/test/hello.txt");
		if (Files.notExists(path)) { // 해당 경로가 존재하지 않다면
			System.out.println("존재하지 않음으로 생성");
			try {
				Files.createFile(path); // 경로상에 존재하지 않는 모든 파일 생성
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		path = Paths.get("C:/test");
		DirectoryStream<Path> directoryStream;
		try {
			directoryStream = Files.newDirectoryStream(path);
			for (Path p : directoryStream) {
				System.out.println(p.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
