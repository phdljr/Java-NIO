import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StudyFileChannelWrite {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("D:/test/hello.txt");
		Files.createDirectories(path.getParent()); //디렉터리가 없다면 생성
		
		//생성과 쓰기를 옵션으로 둔 파일 채널
		FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		
		String data = "테스트!";
		Charset cs = Charset.defaultCharset(); //문자열을 ByteBuffer으로 변환시켜주기 위한 객체
		ByteBuffer bb = cs.encode(data);
		
		int byteCount = fc.write(bb);
		System.out.println("hello.txt : " + byteCount + " bytes written");
		
		fc.close();
	}
}
