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

			// 파일에 저장할 데이터를 ByteBuffer에 저장
			Charset charset = Charset.defaultCharset();
			ByteBuffer byteBuffer = charset.encode("안녕하세요");

			// 첨부 객체 생성
			// 첨부 객체는 콜백 메소드로 전달할 객체이며, 콜백 메소드에서 결과값 외에 추가적인 정보를 얻고자 할 때 사용하는 객체를 말한다.
			// 필요없다면 write 메소드에 null을 대입해도 된다.
			class Attachment {
				Path path; // 파일 경로 저장
				AsynchronousFileChannel fileChannel; // 비동기 파일 채널 저장
			}
			Attachment attachment = new Attachment();
			attachment.path = path;
			attachment.fileChannel = fileChannel;

			// CompletionHandler 객체 생성
			// 비동기 작업이 정상적으로 완료된 경우와 예외 발생으로 실패된 경우에 자동 콜백되는 두 가지 메소드를 가지고 있어야 한다.
			// Integer는 입출력 작업의 결과 타입으로, 읽거나 쓴 바이트 수이다.
			// Attachment는 첨부 객체 타입으로 개발자가 작성할 때 임의로 지정이 가능하다.
			CompletionHandler<Integer, Attachment> completionHandler = new CompletionHandler<Integer, Attachment>() {

				// 작업이 정상적으로 완료될 경우 콜백
				// result는 작업 결과가 대입된다. 읽거나 쓴 바이트의 수이다.
				// attachment는 read와 write 호출 시 제공된 첨부 객체이다.
				@Override
				public void completed(Integer result, Attachment attachment) {
					System.out.println(attachment.path.getFileName() + " : " + result + "bytes written : "
							+ Thread.currentThread().getName());
					try {
						//작업을 다 한 뒤에 채널을 닫아줘야 한다.
						attachment.fileChannel.close();
					}
					catch(IOException e) {}
				}

				// 예외 때문에 작업이 실패된 경우 콜백
				// exc는 작업 처리 도중 발생한 예외이다.
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
		
		executorService.shutdown(); //스레드 풀 닫기
	}

}
