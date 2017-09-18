package org.seckill.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ken Pan on 2017/4/27.
 */
public class jdkHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(jdkHttpUtil.class);


    private static String urlRequest(final String urlstr)
    {
        String errorReason;
        try
        {
            final URL url = new URL(urlstr);

            // do NOT close urlconnection, it is handled by platform.
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            final int responseCode = con.getResponseCode();

            if ( responseCode == 200 )
            {
                final int contentLength = con.getContentLength();
                if ( contentLength > 0 )
                {
                    final byte[] buffer = new byte[contentLength];
                    final int readed = readStream(con.getInputStream(), buffer, contentLength);

                    if ( readed > 0 )
                    {
                        return new String(buffer, 0, readed, "UTF-8");
                    }
                    else
                    {
                        errorReason = "No data readed";
                    }
                }
                else
                {
                    errorReason = "No Content-Length";
                }
            }
            else
            {
                errorReason = "#" + responseCode;
            }
        }
        catch (MalformedURLException ex)
        {
            logger.error( "URL was bad", ex);
            errorReason = ex.getMessage();
        } catch (IOException ex) {
            logger.error("IOException", ex);
            errorReason = ex.getMessage();
        }

        return "{\"success\":\"false\",\"reason\":\""+errorReason+"\"}";
    }


    private static int readStream( final InputStream inputStream,
                                   final byte[] buffer,
                                   final int length)
            throws IOException
    {
        try
        {
            int pos = 0;
            do
            {
                final int readed = inputStream.read(buffer, pos, length - pos);
                if ( readed == -1 )
                    break;

                pos += readed;
            } while ( pos < length );

            return pos;
        }
        finally
        {
            try { inputStream.close(); } catch (Exception ex) {}
        }
    }

    public static void main(String[] args) {
        System.out.println(urlRequest("http://www.mypm25.cn/cityAvg.action?areano=320100&bj=1"));
    }
}
