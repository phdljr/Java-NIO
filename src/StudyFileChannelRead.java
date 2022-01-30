import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StudyFileChannelRead {
	public static void main(String[] args) throws IOException {
		//�б⸦ �ɼ����� �� ���� ä��
		Path path = Paths.get("D:/test/hello.txt");
		FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer bb = ByteBuffer.allocate(100);
		
		Charset cs = Charset.defaultCharset();
		String data = "";
		int byteCount = 0;
		
		while(true) {
			//read�� �ϸ鼭 ������ position�� ���� ����ŭ �����ϰ� ��
			byteCount = fc.read(bb); // ByteBuffer�� capacity��ŭ �о� ��
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
