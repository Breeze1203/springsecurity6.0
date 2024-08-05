package com.example.eachadmin.config.csrf;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

public final class CustomizeCsrfTokenRepository implements CsrfTokenRepository {
    /*

这段代码是Spring Security中`CsrfTokenRepository`接口的三个方法的实现。`CsrfTokenRepository`接口用于定义如何生成、保存和加载CSRF令牌。以下是每个方法的说明和可能的实现：

        1. **generateToken(HttpServletRequest request)**:
        - 这个方法用于生成一个新的CSRF令牌。当一个请求需要一个新的令牌时，Spring Security会调用这个方法。
        - 返回值应该是一个新的`CsrfToken`对象，它包含了令牌的值和相关的属性（如参数名和头部名）。

        2. **saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response)**:
        - 这个方法用于将生成的CSRF令牌保存到请求和响应中。这通常涉及到将令牌设置到cookie、session或响应头中。
        - 在当前的实现中，这个方法是空的，没有执行任何操作。

        3. **loadToken(HttpServletRequest request)**:
        - 这个方法用于从请求中加载CSRF令牌。Spring Security在验证请求时会调用这个方法来获取令牌。
        - 返回值应该是一个`CsrfToken`对象，它包含了请求中携带的令牌信息。
        - 在当前的实现中，这个方法返回了`null`，这意味着没有加载任何令牌。
     */

    static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";
    static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private static final String CSRF_TOKEN_REMOVED_ATTRIBUTE_NAME = CookieCsrfTokenRepository.class.getName().concat(".REMOVED");
    private final String parameterName = "_csrf";
    private final String headerName = "X-XSRF-TOKEN";
    private final String cookieName = "XSRF-TOKEN";
    private boolean cookieHttpOnly = true;
    private String cookiePath;
    private String cookieDomain;
    private Boolean secure;
    private final int cookieMaxAge = -1;

    public CustomizeCsrfTokenRepository() {
    }

    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(this.headerName, this.parameterName, this.createNewToken());
    }

    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String tokenValue = token != null ? token.getToken() : "";
        Cookie cookie = new Cookie(this.cookieName, tokenValue);
        cookie.setSecure(this.secure != null ? this.secure : request.isSecure());
        cookie.setPath(StringUtils.hasLength(this.cookiePath) ? this.cookiePath : this.getRequestContext(request));
        cookie.setMaxAge(token != null ? this.cookieMaxAge : 0);
        cookie.setHttpOnly(this.cookieHttpOnly);
        if (StringUtils.hasLength(this.cookieDomain)) {
            cookie.setDomain(this.cookieDomain);
        }
        response.addCookie(cookie);
        if (!StringUtils.hasLength(tokenValue)) {
            request.setAttribute(CSRF_TOKEN_REMOVED_ATTRIBUTE_NAME, Boolean.TRUE);
        } else {
            request.removeAttribute(CSRF_TOKEN_REMOVED_ATTRIBUTE_NAME);
        }

    }

    public CsrfToken loadToken(HttpServletRequest request) {
        if (Boolean.TRUE.equals(request.getAttribute(CSRF_TOKEN_REMOVED_ATTRIBUTE_NAME))) {
            return null;
        } else {
            Cookie cookie = WebUtils.getCookie(request, this.cookieName);
            if (cookie == null) {
                return null;
            } else {
                String token = cookie.getValue();
                return !StringUtils.hasLength(token) ? null : new DefaultCsrfToken(this.headerName, this.parameterName, token);
            }
        }
    }

    public void setCookieHttpOnly(boolean cookieHttpOnly) {
        this.cookieHttpOnly = cookieHttpOnly;
    }

    private String getRequestContext(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }


    private String createNewToken() {
        return UUID.randomUUID().toString();
    }

}

