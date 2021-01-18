package com.why.counter.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author WHY
 * @Date 2021-01-14
 * @Version 1.0
 */

@Component
public class SessionCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * ajax 跨域，这里代码写的有点问题，要改
     * @param servletRequest    给过滤器提供了对进入的信息（包括表单数据、cookie和HTTP请求头）的完全访问
     * @param servletResponse   通常在简单的过滤器中忽略此参数
     * @param filterChain       用来调用 servlet 或 JSP 网页
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //解决 ajax 跨域问题
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin","*");

        //放行请求
        filterChain.doFilter(request, response);

    }
}
