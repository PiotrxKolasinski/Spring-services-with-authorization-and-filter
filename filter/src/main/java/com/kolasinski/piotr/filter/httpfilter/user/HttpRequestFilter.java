package com.kolasinski.piotr.filter.httpfilter.user;

import com.kolasinski.piotr.filter.user.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HttpRequestFilter extends ZuulFilter {
    private final HttpRequestService httpRequestService;

    @Autowired
    public HttpRequestFilter(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        User user;

        switch (httpRequestService.getAuthorizationType(request)) {
            case ACCESS_TOKEN_VERIFICATION:
                user = httpRequestService.verifyAccessTokenAndGetUser(request);
                ctx.addZuulRequestHeader("userId", Long.toString(user.getId()));
                break;
            case ACCESS_TOKEN_AND_REFRESH_TOKEN_VERIFICATION:
                user = httpRequestService.verifyAccessTokenAndRefreshTokenAndGetUser(request);
                ctx.addZuulRequestHeader("userId", Long.toString(user.getId()));
                break;
            case PERMIT_ALL:
                break;
        }

        return null;
    }

    @Override
    public boolean shouldFilter() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestPath = request.getRequestURI();
        return !requestPath.contains("admins");
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "pre";
    }

}
