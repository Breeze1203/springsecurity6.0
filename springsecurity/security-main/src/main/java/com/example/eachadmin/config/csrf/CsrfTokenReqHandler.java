package com.example.eachadmin.config.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

/*

1. `handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken)`：
   - 这是`CsrfTokenRequestHandler`接口的一个方法，用于处理HTTP请求时的CSRF令牌。在这个方法中，
   你可以自定义当请求到达时如何生成或验证CSRF令牌。
   `Supplier<CsrfToken>`是一个提供CSRF令牌的函数式接口。
2. `resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken)`：
   - 这个方法用于解析请求中的CSRF令牌的值。通常，这个值是从请求的表单数据、HTTP头部或URL参数中获取的。
   - 这里使用了`super.resolveCsrfTokenValue(request, csrfToken)`，表示调用了父类（接口默认实现）的`resolveCsrfTokenValue`方法
   。这通常意味着你想要使用接口默认实现的逻辑来解析CSRF令牌值，但你可能需要根据你的具体需求来覆盖这个方法。
 */
public class CsrfTokenReqHandler extends CsrfTokenRequestAttributeHandler {
    private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
        /*
         * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection of
         * the CsrfToken when it is rendered in the response body.
         */
        this.delegate.handle(request, response, csrfToken);
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        /*
         * If the request contains a request header, use CsrfTokenRequestAttributeHandler
         * to resolve the CsrfToken. This applies when a single-page application includes
         * the header value automatically, which was obtained via a cookie containing the
         * raw CsrfToken.
         */
        if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) {
            return super.resolveCsrfTokenValue(request, csrfToken);
        }
        /*
         * In all other cases (e.g. if the request contains a request parameter), use
         * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
         * when a server-side rendered form includes the _csrf request parameter as a
         * hidden input.
         */
        return this.delegate.resolveCsrfTokenValue(request, csrfToken);
    }
}
    /*
### CsrfTokenReqHandler
- **职责**：负责在请求处理过程中处理CSRF令牌，例如在添加令牌到响应头或验证请求头中的令牌。
- **处理请求** (`handle`)：这个方法在每个请求处理之前被调用，用于生成（如果需要）和添加CSRF令牌到响应头中，以便客户端可以获取并在后续请求中使用。
- **解析令牌值** (`resolveCsrfTokenValue`)：这个方法用于从请求中解析CSRF令牌的值，通常在需要验证CSRF令牌时使用。

### 调用顺序
1. **CsrfTokenRepository** 的 `loadToken` 方法首先被调用，以从存储中加载现有的CSRF令牌。
2. 如果需要生成新的CSRF令牌（例如，用户访问了一个需要认证的页面），则 **CsrfTokenRepository** 的 `generateToken` 方法会被调用。
3. 接着，**CsrfTokenRepository** 的 `saveToken` 方法会被调用，以保存新生成的令牌。
4. 当响应被发送回客户端之前，**CsrfTokenReqHandler** 的 `handle` 方法被调用，将CSRF令牌添加到响应头中。
5. 对于需要CSRF验证的请求，**CsrfTokenReqHandler** 的 `resolveCsrfTokenValue` 方法会在请求处理时被调用，以解析请求中的CSRF令牌值。

通过这种机制，Spring Security确保了CSRF令牌在客户端和服务器之间安全地传输和验证。`CsrfTokenRepository`主要负责令牌的生命周期管理，而`CsrfTokenReqHandler`负责在请求处理过程中使用这些令牌。两者的协同工作确保了CSRF保护的有效实施。

     */


