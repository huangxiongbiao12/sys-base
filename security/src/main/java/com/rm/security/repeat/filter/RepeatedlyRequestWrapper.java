package com.rm.security.repeat.filter;


import com.rm.security.utils.http.HttpHelper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 构建可重复读取inputStream的request
 * 
 */
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper
{
    private final byte[] body;

    public RepeatedlyRequestWrapper(HttpServletRequest request, ServletResponse response) throws IOException
    {
        super(request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        body = HttpHelper.getBodyString(request).getBytes("UTF-8");
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream()
        {
            @Override
            public int read() throws IOException
            {
                return bais.read();
            }

            @Override
            public int available() throws IOException
            {
                return body.length;
            }

            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public boolean isReady()
            {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener)
            {

            }
        };
    }
}