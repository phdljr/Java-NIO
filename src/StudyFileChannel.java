import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StudyFileChannel {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("D:/test/hello.txt");
		Files.createDirectories(path.getParent());
		
		FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		
		String data = "Å×½ºÆ®!";
		Charset cs = Charset.defaultCharset();
		ByteBuffer bb = cs.encode(data);
		
		int byteCount = fc.write(bb);
		System.out.println("hello.txt : " + byteCount + " bytes written");
		
		fc.close();
	}
}
