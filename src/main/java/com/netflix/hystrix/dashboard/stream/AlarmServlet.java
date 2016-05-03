package com.netflix.hystrix.dashboard.stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.MessageFormat;

/**
 *   post to influxdb
 *
 * Created by chunhua.zhang@yoho.cn on 2016/5/3.
 */
public class AlarmServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MockStreamServlet.class);

    private final static PoolingClientConnectionManager threadSafeConnectionManager = new PoolingClientConnectionManager();
    private final static HttpClient httpClient = new DefaultHttpClient(threadSafeConnectionManager);

    public AlarmServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //curl -i -XPOST 'http://influxdb.yohoops.org:8086/write?db=yoho-monitor' --data-binary 'cpu_load_short,host=server01,region=us-west success=false '

        String  service_name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
        logger.warn("error happened at: {}", service_name );

        this.postToInfluxdb(service_name);
    }

    public void postToInfluxdb(String service_name) throws IOException {
        //curl -i -XPOST 'http://influxdb.yohoops.org:8086/write?db=yoho-monitor' --data-binary 'cpu_load_short,host=server01,region=us-west success=false '


        if(service_name != null && !service_name.isEmpty()) {

            HttpPost httppost = new HttpPost("http://influxdb.yohoops.org:8086/write?db=yoho-monitor");

            String message_format = "dashboard_error,name={0} success=false";
            String message = MessageFormat.format(message_format, service_name);
            httppost.setEntity(new ByteArrayEntity(message.getBytes("UTF-8")));

            httpClient.execute(httppost);
        }
    }

}
