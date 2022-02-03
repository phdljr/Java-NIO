import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudyAsynchronousFileChannelWrite {

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		for (int i = 1; i <= 10; i++) {
			Path path = Paths.get("C:/test/file" + i + ".txt");
			Files.createDirectories(path.getParent());

			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,
					EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE), executorService);

			// ���Ͽ� ������ �����͸� ByteBuffer�� ����
			Charset charset = Charset.defaultCharset();
			ByteBuffer byteBuffer = charset.encode("�ȳ��ϼ���");

			// ÷�� ��ü ����
			// ÷�� ��ü�� �ݹ� �޼ҵ�� ������ ��ü�̸�, �ݹ� �޼ҵ忡�� ����� �ܿ� �߰����� ������ ����� �� �� ����ϴ� ��ü�� ���Ѵ�.
			// �ʿ���ٸ� write �޼ҵ忡 null�� �����ص� �ȴ�.
			class Attachment {
				Path path; // ���� ��� ����
				AsynchronousFileChannel fileChannel; // �񵿱� ���� ä�� ����
			}
			Attachment attachment = new Attachment();
			attachment.path = path;
			attachment.fileChannel = fileChannel;

			// CompletionHandler ��ü ����
			// �񵿱� �۾��� ���������� �Ϸ�� ���� ���� �߻����� ���е� ��쿡 �ڵ� �ݹ�Ǵ� �� ���� �޼ҵ带 ������ �־�� �Ѵ�.
			// Integer�� ����� �۾��� ��� Ÿ������, �аų� �� ����Ʈ ���̴�.
			// Attachment�� ÷�� ��ü Ÿ������ �����ڰ� �ۼ��� �� ���Ƿ� ������ �����ϴ�.
			CompletionHandler<Integer, Attachment> completionHandler = new CompletionHandler<Integer, Attachment>() {

				// �۾��� ���������� �Ϸ�� ��� �ݹ�
				// result�� �۾� ����� ���Եȴ�. �аų� �� ����Ʈ�� ���̴�.
				// attachment�� read�� write ȣ�� �� ������ ÷�� ��ü�̴�.
				@Override
				public void completed(Integer result, Attachment attachment) {
					System.out.println(attachment.path.getFileName() + " : " + result + "bytes written : "
							+ Thread.currentThread().getName());
					try {
						//�۾��� �� �� �ڿ� ä���� �ݾ���� �Ѵ�.
						attachment.fileChannel.close();
					}
					catch(IOException e) {}
				}

				// ���� ������ �۾��� ���е� ��� �ݹ�
				// exc�� �۾� ó�� ���� �߻��� �����̴�.
				@Override
				public void failed(Throwable exc, Attachment attachment) {
					exc.printStackTrace();
					try {
						attachment.fileChannel.close();
					}
					catch(IOException e) {}
				}
			};
			
			fileChannel.write(byteBuffer, 0, attachment, completionHandler);
		}
		
		executorService.shutdown(); //������ Ǯ �ݱ�
	}

}
