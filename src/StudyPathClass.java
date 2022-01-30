import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudyPathClass {
	public static void main(String[] args) {
		Path path = Paths.get("C:/test/cc");
		// System.out.println(Files.isDirectory(path));

		if (Files.notExists(path)) { // �ش� ��ΰ� �������� �ʴٸ�
			System.out.println("�������� �������� ����");
			try {
				Files.createDirectories(path); // ��λ� �������� �ʴ� ��� ���丮 ����
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		path = Paths.get("C:/test/hello.txt");
		if (Files.notExists(path)) { // �ش� ��ΰ� �������� �ʴٸ�
			System.out.println("�������� �������� ����");
			try {
				Files.createFile(path); // ��λ� �������� �ʴ� ��� ���� ����
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
