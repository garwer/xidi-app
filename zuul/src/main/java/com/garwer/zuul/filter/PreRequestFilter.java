//package com.garwer.zuul.filter;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.PatternMatchUtils;
//
//import javax.servlet.http.HttpServletRequest;
//
//
///**
// * user-anno/internal接口内部不能拦截 但也不允许外部直接访问
// */
//@Component
//public class PreRequestFilter extends ZuulFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(PreRequestFilter.class);
//
//
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;//int值来定义过滤器的执行顺序，数值越小优先级越高
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//        System.out.println("request.getRequestURI()" + request.getRequestURI());
//        return PatternMatchUtils.simpleMatch("*-anon/internal*", request.getRequestURI());
//    }
//
//    @Override
//    public Object run() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
//        requestContext.setResponseBody(HttpStatus.FORBIDDEN.getReasonPhrase());
//        requestContext.setSendZuulResponse(false);
//        return null;
//    }
//}
