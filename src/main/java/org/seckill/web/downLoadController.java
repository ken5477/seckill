package org.seckill.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @说明 断点续传
 * http协议中，服务端实现断点续传首先需要读取客户端传送的Range头信息，
 * 比如“Range: bytes=12583394-”这个就是指原来正在下载的文件需要从第12583394字节继续下载，
 * 然后我们利用java.io.File的skip方法，舍弃掉原文件的前n个字节，接着就继续慢慢write。
 *
 * Created by Ken Pan on 2017/5/2.
 */
@Controller
@RequestMapping("/seckillDownload")
public class downLoadController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @SuppressWarnings({"unchecked"})
    @RequestMapping("{fileName}/downFile")
    public ResponseEntity<String> downFile(
            @PathVariable("fileName")String fileName,
            HttpServletResponse response,
            HttpServletRequest request){
        InputStream inputStream = null;
        ServletOutputStream out = null;
        try {
            File file = new File("C:\\Users\\Administrator\\Desktop" + "\\" + fileName);
            int fSize = Integer.parseInt(String.valueOf(file.length()));
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", String.valueOf(fSize));
            //解決文件名亂碼
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            inputStream=new FileInputStream("C:\\Users\\Administrator\\Desktop" + "\\" + fileName);
            long pos = 0;
            if (null != request.getHeader("Range")) {
                // 断点续传
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                try {
                    pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
                } catch (NumberFormatException e) {
                    pos = 0;
                }
            }
            out = response.getOutputStream();
            String contentRange = new StringBuffer("bytes ").append(pos+"").append("-").append((fSize - 1)+"").append("/").append(fSize+"").toString();
            response.setHeader("Content-Range", contentRange);
            inputStream.skip(pos);
            byte[] buffer = new byte[1024*100];
            int length = 0;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, length);
                Thread.sleep(100);//加延時，看效果
            }
        } catch (Exception e) {
            logger.error("软件下载异常："+e);
        }finally{
            try {
                if(null != out) out.flush();
                if(null != out) out.close();
                if(null != inputStream) inputStream.close();
            } catch (IOException e) {
            }
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
