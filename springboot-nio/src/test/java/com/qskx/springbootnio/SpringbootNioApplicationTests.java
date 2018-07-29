package com.qskx.springbootnio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *缓冲区(buffer):在java NIO中负责数据的存取，缓冲区就是数组，用于存储不同类型的缓冲区
 *缓冲区类型：
 *ByteBuffer
 *CharBuffer
 *IntBuffer
 *ShortBuffer
 *LongBuffer
 *DoubleBuffer
 *
 * 核心方法:put()和get()
 *
 * 核心属性：
 * capacity:容量，表示缓冲区中最大数据的存储容量
 * limit:界限，表示缓冲区中可以操作数据的大小（limit后数据不能进行读写）
 * position：位置，表示缓冲区中正在操作数据的位置
 *
 * mark():标记
 * reset():恢复到mark的位置
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootNioApplicationTests {

	String str="helloworld";

	@Test
	public void contextLoads() {
		//1.分配一个指定大小的缓冲区
		ByteBuffer bb=ByteBuffer.allocate(1024);
		System.out.println(bb.capacity());
		System.out.println(bb.limit());
		System.out.println(bb.position());

		//2.存入数据
		bb.put(str.getBytes());
		System.out.println(bb.capacity());
		System.out.println(bb.limit());
		System.out.println(bb.position());//位置变为10

		//3.切换读取数据模式
		bb.flip();
		System.out.println(bb.capacity());//缓冲区大小仍然为1024
		System.out.println(bb.limit());//可读取数量为10个的字节
		System.out.println(bb.position());//位置切换到0了，可以从0开始读取

		//4.读取数据
		byte[] by=new byte[bb.limit()];
		bb.get(by);//获取到缓冲区可读取的所有数据（也就是10）,存放在by数组中
		//System.out.println(by);
		System.out.println(new String(by,0,by.length));
		System.out.println(bb.capacity());
		System.out.println(bb.limit());
		System.out.println(bb.position());
		try {
			bb.get();//再读的话就越界了
		} catch (Exception e) {
			e.printStackTrace();
		}

		//5.rewind() ：可重复读数据
		bb.rewind();
		System.out.println(bb.capacity());
		System.out.println(bb.limit());
		System.out.println(bb.position());//位置变为0了，说明又可以读了

		//6.clear()：清空缓冲区，但是缓冲区的数据依然存在，但是处于“被遗忘状态”
		bb.clear();
		System.out.println(bb.capacity());
		System.out.println(bb.limit());//指针全部回到最原始状态，不知道有多少数据
		System.out.println(bb.position());
		System.out.println((char)bb.get());
	}

	@Test
	public void run2(){
		ByteBuffer bb=ByteBuffer.allocate(1024);
		bb.put(str.getBytes());
		bb.flip();
		byte[] by=new byte[bb.limit()];
		bb.get(by,0,2);
		System.out.println(new String(by,0,2));
		System.out.println(bb.position());//到第二个字节了

		//标记
		bb.mark();

		bb.get(by,2,3);
		System.out.println(new String(by,2,3));
		System.out.println(bb.position());//到第二个字节了

		//重置
		bb.reset();
		System.out.println(bb.position());//位置又回到标记处
	}

	@Test
	public void run3(){
		//非直接缓冲区:通过allocate（）方法分配缓冲区，将缓冲区建立在JVM的内存中
		ByteBuffer bb=ByteBuffer.allocate(1024);
		//直接缓冲区:通过allocateDirect（）方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率
		ByteBuffer bb2=ByteBuffer.allocateDirect(1024);
	}

	@Test
	public void run4() throws IOException{
		//1.分散读取
		RandomAccessFile raf1=new RandomAccessFile("1.txt", "rw");
		//获取通道
		FileChannel channel1 = raf1.getChannel();
		//分配两个指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		//构建缓冲区数组
		ByteBuffer[] bufArr={buf1,buf2};
		//通道读取
		channel1.read(bufArr);
		//切换缓冲区为写模式
		for (ByteBuffer byteBuffer : bufArr) {
			byteBuffer.flip();
		}
		System.out.println(new String(bufArr[0].array(), 0, bufArr[0].limit()));
		System.out.println("--------------------------");
		System.out.println(new String(bufArr[1].array(), 0, bufArr[1].limit()));

		//2.聚集写入
		//聚集写入到2.txt中
		RandomAccessFile raf2=new RandomAccessFile("2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		//将缓冲区数组写入通道中
		channel2.write(bufArr);
	}


	//3.通道之间的数据传输，直接缓冲区
	@Test
	public void run03() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("e:/01.avi"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("e:/02.avi"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
//      inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
	}

	//2.利用直接缓冲区完成文件的复制
	@Test
	public void run02() throws IOException {
		long start=System.currentTimeMillis();
		//获取通道
		//读模式
		FileChannel inChannel = FileChannel.open(Paths.get("e:/01.avi"), StandardOpenOption.READ);
		//读写模式
		FileChannel outChannel = FileChannel.open(Paths.get("e:/02.avi"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

		//内存映射文件
		MappedByteBuffer inBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outBuf = outChannel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());

		//对缓冲区的数据进行读写操作
		byte[] by=new byte[inBuf.limit()];
		inBuf.get(by);
		outBuf.put(by);
		inChannel.close();
		outChannel.close();
		long end=System.currentTimeMillis();
		System.out.println("耗费时间："+(end-start));
	}

	//1.利用通道完成文件的复制（非直接缓冲区）
	@Test
	public void run() throws IOException{
		long start=System.currentTimeMillis();
		FileInputStream fis=null;
		FileOutputStream fos=null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis=new FileInputStream("e:/01.avi");
			fos=new FileOutputStream("e:/02.avi");
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			//分配指定大小的缓冲区
			ByteBuffer bb = ByteBuffer.allocate(1024);
			//将通道中的数据存入缓冲区,这个时候的缓冲区是写模式
			while(inChannel.read(bb)!=-1){
				//将缓冲区切换为读模式
				bb.flip();
				outChannel.write(bb);
				bb.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fis.close();
			fos.close();
			inChannel.close();
			outChannel.close();
		}
		long end=System.currentTimeMillis();
		System.out.println("耗费时间："+(end-start));
	}

}
