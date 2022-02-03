import java.nio.ByteBuffer;

public class StudyBuffer {
	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate(10);
		
		bb.put((byte)1);
		bb.put((byte)2);
		bb.put((byte)3);
		bb.put(3, (byte)100);
		
		bb.flip();
		System.out.println(bb.get());
		System.out.println(bb.get());
		System.out.println(bb.get());
		//System.out.println(bb.get()); //오류 발생. 왜?
		
	}
}
