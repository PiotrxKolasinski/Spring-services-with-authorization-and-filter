package com.kolasinski.piotr.filter.httpfilter.user;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpResponseFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(HttpResponseFilter.class);

    private final HttpResponseService httpResponseService;

    @Autowired
    public HttpResponseFilter(HttpResponseService httpResponseService) {
        this.httpResponseService = httpResponseService;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        String requestPath = request.getRequestURI();

        if (requestPath.contains("auth")) {
            if (requestPath.contains("login") && ctx.getResponseStatusCode() == 200) {
                httpResponseService.prepareLoginResponse(httpResponseService.getUserEmail(ctx), response);
            } else if (requestPath.contains("logout") && ctx.getResponseStatusCode() == 200) {
                httpResponseService.deleteAccessToken(request);
                httpResponseService.deleteRefreshToken(request);
            } else if (request.getMethod().equals(HttpMethod.DELETE.toString()) && ctx.getResponseStatusCode() == 200) {
                httpResponseService.deleteAccessToken(request);
                httpResponseService.deleteRefreshToken(request);
            }
        }

        log.info(String.format("REQUEST  => %s %s", request.getMethod(), request.getRequestURL().toString()));
        log.info("RESPONSE => HTTP:" + response.getStatus());
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return "post";
    }

}
