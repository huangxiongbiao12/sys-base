package com.rm.common.web.log;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
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
        boolean isJson = false;
        if (StringUtils.hasLength(request.getContentType()) && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            isJson = true;
        }
        javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest) request;
        RepeatedlyReadRequestWrapper requestWrapper = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // Get request URL.
            map.put("URL", req.getRequestURL());
            map.put("Method", req.getMethod());
            map.put("Protocol", req.getProtocol());
            // 获取header信息
            List<Map<String, String>> headerList = new ArrayList<>();
            Map<String, String> headerMaps = new HashMap<String, String>();
            for (Enumeration<String> enu = req.getHeaderNames(); enu.hasMoreElements(); ) {
                String name = enu.nextElement();
                headerMaps.put(name, req.getHeader(name));
            }
            headerList.add(headerMaps);
            map.put("headers", headerList);
            //获取parameters信息
            List<Map<String, String>> parameterList = new ArrayList<>();
            Map<String, String> parameterMaps = new HashMap<String, String>();
            for (Enumeration<String> names = req.getParameterNames(); names.hasMoreElements(); ) {
                String name = names.nextElement();
                parameterMaps.put(name, req.getParameter(name));
            }
            parameterList.add(parameterMaps);
            map.put("parameters", parameterList);
            String line = "";
            // 获取请求体信息
            if (req.getMethod().equalsIgnoreCase("POST")) {
                if (isJson) {
                    requestWrapper = new RepeatedlyReadRequestWrapper(req);
                    line = requestWrapper.getBody();
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
                map.put("Context", new String[]{line});
            }
            log.info("接收请求报文：\n" + JSONObject.toJSONString(map));
            if (requestWrapper != null) {
                chain.doFilter(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (IOException ex) {
            log.error("请求日志打印异常：", ex);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }


}
