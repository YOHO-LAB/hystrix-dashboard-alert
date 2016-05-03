package com.yoho.opration.test;

import com.netflix.hystrix.dashboard.stream.AlarmServlet;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by chunhua.zhang@yoho.cn on 2016/5/3.
 */
public class TestAlarmServlet {


    private AlarmServlet larmServlet;

    @Test
    public void test() throws IOException {

        larmServlet = new AlarmServlet();

        larmServlet.postToInfluxdb("aa");

    }
}
