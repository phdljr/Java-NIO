import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StudyFileChannelRead {
	public static void main(String[] args) throws IOException {
		//읽기를 옵션으로 둔 파일 채널
		Path path = Paths.get("D:/test/hello.txt");
		FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer bb = ByteBuffer.allocate(100);
		
		Charset cs = Charset.defaultCharset();
		String data = "";
		int byteCount = 0;
		
		while(true) {
			//read를 하면서 버퍼의 position은 읽은 수만큼 증가하게 됨
			byteCount = fc.read(bb); // ByteBuffer의 capacity만큼 읽어 옴
			if(byteCount == -1) {
				break;
			}
			bb.flip();
			data += cs.decode(bb).toString();
			bb.clear();
		}
		fc.close();
		
		System.out.println(data);
		
	}
}
