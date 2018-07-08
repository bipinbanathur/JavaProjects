package com.rtaware.repoapp;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CORSFilter implements Filter {

   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
       HttpServletResponse response = (HttpServletResponse) res;
       HttpServletRequest request = (HttpServletRequest) req;
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
       response.setHeader("Access-Control-Max-Age", "3600");
       response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, authorization");

       if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
           response.setStatus(HttpServletResponse.SC_OK);
       } else {
           chain.doFilter(req, res);
       }
   }

   @Override
   public void init(FilterConfig filterConfig)
   {
   }

   @Override
   public void destroy() 
   {
   }
}


