import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class StudyFileCopy {
	
	public static void easyCopy() throws IOException {
		Path from = Paths.get("D:/test/hello.txt");
		Path to = Paths.get("D:/test/copy_hello.txt");
		
		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void main(String[] args) throws IOException {
		Path from = Paths.get("D:/test/hello.txt");
		Path to = Paths.get("D:/test/copy_hello.txt");
		
		FileChannel fc_from = FileChannel.open(from, StandardOpenOption.READ);
		FileChannel fc_to = FileChannel.open(to, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		
		ByteBuffer bb = ByteBuffer.allocate(100);
		int byteCount = 0;
		
		while(true) {
			byteCount = fc_from.read(bb);
			if(byteCount == -1) {
				break;
			}
			System.out.println("읽기:"+byteCount);
			bb.flip();
			
			byteCount = fc_to.write(bb);
			System.out.println("쓰기:"+byteCount);
			bb.clear();
		}
		
		fc_from.close();
		fc_to.close();
	}
}
