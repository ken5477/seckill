package org.seckill.util.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ken Pan on 2017/4/28.
 */
public class TcpServiceServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String ip;
    private static String port;
    private static InetSocketAddress socketAddress = null; // 服务监听个地址
    private static ServerSocket rServer = null; // 连接对象
    /* 接入服务器服务类 */

    private static ExecutorService exec = Executors.newFixedThreadPool(10);

    static {
        Properties ps = new Properties();
        try {
            ps.load(TcpServiceServlet.class.getClassLoader().getResourceAsStream("tcp.properties"));
            ip = ps.getProperty("ip");
            port = ps.getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TcpServiceServlet() {
        super();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 初始化连接
     *
     * @throws SocketException
     */
    @Override
    public void init(final ServletConfig config) {

        try {
            socketAddress = new InetSocketAddress(ip, Integer.parseInt(port));
            rServer = new ServerSocket();
            rServer.bind(socketAddress);
            //LOG.info("ip"+ip);
            //LOG.info("port"+Integer.parseInt(port));
            logger.info("tcp服务端已经启动");
            final ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
            exec.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println("run");
                    while (!Thread.interrupted()) {
                        try {
                            System.out.println("开始接收请求！");
                            Socket request = rServer.accept();
                            System.out.println(request);
                            exec.execute(new ServerThread(request, ctx, config));
                        } catch (IOException e) {
                            logger.error("server socket accept fail", e);
                        }
                        // 接收客户机连接请求


                    }
                }
            });

        } catch (Exception e) {
            logger.error("服务端启动失败");
            if (rServer != null && !rServer.isClosed())
                try {
                    rServer.close();
                } catch (IOException e1) {
                    logger.error("server socket close fail", e);
                }

            exec.shutdownNow();
            e.printStackTrace();
        }
    }
}

class ServerThread implements Runnable {
    private Logger LOG = LoggerFactory.getLogger(ServerThread.class);
    Socket clientRequest;// 用户连接的通信套接字
    BufferedReader input;// 输入流
    PrintWriter output;// 输出流
    private ApplicationContext ctx;
    private ServletConfig config;
    String clientIp = "";
    int clientPort = 0;

    public ServerThread(Socket s, ApplicationContext ctx, ServletConfig config) {
        this.clientRequest = s;
        this.ctx = ctx;
        // 接收receiveServer传来的套接字
        System.out.println("接收receiveServer传来的套接字");
        InputStreamReader reader;
        OutputStreamWriter writer;
        try { // 初始化输入、输出流
            String clientIp = clientRequest.getInetAddress().getHostAddress();
            int clientPort = clientRequest.getPort();
            LOG.info("New connection accepted from : " + clientIp + ":" + clientPort);

            reader = new InputStreamReader(clientRequest.getInputStream());
            writer = new OutputStreamWriter(clientRequest.getOutputStream());
            input = new BufferedReader(reader);
            output = new PrintWriter(writer, true);
        } catch (IOException e) {
            LOG.error("ServerThread constructer ioexception", e);
        }

    }

    public void run() {
        try {
            LOG.info("#####reading from " + clientRequest.getInetAddress().getHostAddress() + " #####");
            String responseStr = null;
            StringBuffer buffer = new StringBuffer();
            while ((responseStr = input.readLine()) != null) {
                buffer.append(responseStr);
            }
            //if(buffer[0] != "0x00" || buffer[1] != "{")
            LOG.info("#####end listening from port " + clientRequest.getLocalPort() + " #####");
            LOG.info(clientRequest.getInetAddress().getHostAddress() + " client data:" + buffer.toString());
            //.substring(buffer.toString().indexOf("{") + 1)
            if (buffer.toString() == null || buffer.toString().isEmpty()) {
                return;
            }
            LOG.info(clientIp + " clientJson data:" + buffer.toString());
            //处理，可用json
        } catch (Exception e) {
            LOG.error("accept socket exception", e);
            output.write("{\"result\":0}");
        } finally {
            if (clientRequest != null && !clientRequest.isClosed()) {
                try {
                    clientRequest.close();
                } catch (IOException e) {
                    LOG.error("accept socket close error", e);
                }
            }
        }
    }
}

