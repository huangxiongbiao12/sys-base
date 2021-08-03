package com.rm.common.web.log;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest)request;
        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest requestWrapper = new RepeatedlyReadRequestWrapper(req);

        try {
            // Get request URL.
            map.put("URL", req.getRequestURL());
            map.put("Method", req.getMethod());
            map.put("Protocol",req.getProtocol());
            // 获取header信息

            List<Map<String,String>> headerList = new ArrayList<>();
            Map<String,String> headerMaps = new HashMap<String,String>();
            for(Enumeration<String> enu = req.getHeaderNames(); enu.hasMoreElements();){
                String name = enu.nextElement();
                headerMaps.put(name,req.getHeader(name));
            }
            headerList.add(headerMaps);
            map.put("headers", headerList);
            //获取parameters信息

            List<Map<String,String>> parameterList = new ArrayList<>();
            Map<String,String> parameterMaps = new HashMap<String,String>();
            for(Enumeration<String> names = req.getParameterNames();names.hasMoreElements();){
                String name = names.nextElement();
                parameterMaps.put(name, req.getParameter(name));
            }
            parameterList.add(parameterMaps);
            map.put("parameters", parameterList);
            String line = "";
            // 获取请求体信息
            if (req.getMethod().equalsIgnoreCase("POST")) {
                int len = req.getContentLength();
                char[] buf = new char[len];
                int bufcount = requestWrapper.getReader().read(buf);
                if (len > 0 && bufcount <= len) {
                    line = String.copyValueOf(buf).substring(0, bufcount);
                }
            } else if (req.getMethod().equalsIgnoreCase("GET")) {
                int idx = req.getRequestURL().indexOf("?");
                if (idx != -1) {
                    line = req.getRequestURL().substring(idx + 1);
                } else {
                    line = null;
                }
            }
            if (line != null) {
                map.put("Context", new String[] { line });
            }
            log.info("接收请求报文：\n"+ JSONObject.toJSONString(map));
            chain.doFilter(requestWrapper, response);
        } catch (IOException ex) {
            log.error("请求日志打印异常：", ex);
        }


    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }


}
