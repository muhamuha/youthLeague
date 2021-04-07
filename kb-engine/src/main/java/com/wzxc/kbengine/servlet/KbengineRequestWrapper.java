package com.wzxc.kbengine.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class KbengineRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public KbengineRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer body = new StringBuffer();
        String s;
        while((s = reader.readLine()) != null){
            body.append(s);
        }
        reader.close();
        this.body = body.toString();
    }

    public String getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream()  {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read(){
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
