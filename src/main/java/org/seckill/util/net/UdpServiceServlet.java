package org.seckill.util.net;

/**
 * Created by Ken Pan on 2017/5/2.
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @说明 UDP连接服务端，这里一个包就做一个线程处理
 * @version 1.0
 * @since
 */
public class UdpServiceServlet extends HttpServlet {
    private static String ip;
    private static String port;
    private static ExecutorService exec = Executors.newFixedThreadPool(10);
    static{
        Properties ps = new Properties();
        try
        {
            ps.load(UdpServiceServlet.class.getClassLoader().getResourceAsStream("tcp.properties"));
            ip = ps.getProperty("ip");
            port = ps.getProperty("port");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public UdpServiceServlet() {
        super();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    /**
     * 接收数据包，该方法会造成线程阻塞
     * @return
     * @throws Exception
     * @throws IOException
     */
    public DatagramPacket receive(DatagramPacket packet) throws Exception {
        try {
            datagramSocket.receive(packet);
            return packet;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 将响应包发送给请求端
     * @param packet
     * @throws IOException
     */
    public static void response(DatagramPacket packet) {
        try {
            datagramSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化连接
     * @throws SocketException
     */
    @Override
    public void init(final ServletConfig config){
        try {
            socketAddress = new InetSocketAddress(ip, Integer.parseInt(port));
            datagramSocket = new DatagramSocket(socketAddress);
//			datagramSocket.setSoTimeout(5 * 1000);
            System.out.println("服务端已经启动");
            new Thread(new Runnable() {

                public void run() {
                    // TODO Auto-generated method stub
                    while(true){
                        try {
                            byte[] buffer = new byte[1024]; // 缓冲区
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            receive(packet);

                            exec.execute(new ServiceImpl(packet,config));
                            //new Thread(new ServiceImpl(packet,config)).start();
                        } catch (Exception e) {
                        }

                    }
                }
            }).start();

        } catch (Exception e) {
            datagramSocket = null;
            System.err.println("服务端启动失败");
            e.printStackTrace();
        }
    }
    private static InetSocketAddress socketAddress = null; // 服务监听个地址
    private static DatagramSocket datagramSocket = null; // 连接对象
}
/**
 * @说明 打印收到的数据包，并且将数据原封返回，中间设置休眠表示执行耗时
 * @version 1.0
 * @since
 */
class ServiceImpl implements Runnable {
    private DatagramPacket packet;
    private ServletConfig config;
    public ServiceImpl(DatagramPacket packet,ServletConfig config){
        this.packet = packet;
        this.config = config;
    }
    public void run() {
        try {
            byte[] bt = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
            String clientString = new String(bt);
			System.out.println("client data:"+clientString);

            // 设置回复的数据，原数据返回，以便客户端知道是那个客户端发送的数据
            packet.setData("{\"result\":1}".getBytes());
            UdpServiceServlet.response(packet);
        } catch (Exception e) {
            e.printStackTrace();
            packet.setData("{\"result\":0}".getBytes());
            UdpServiceServlet.response(packet);
        }
    }
}
