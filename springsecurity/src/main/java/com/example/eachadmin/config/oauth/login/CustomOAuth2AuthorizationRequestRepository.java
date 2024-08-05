package com.example.eachadmin.config.oauth.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.Assert;

// 将类标记为Spring组件，使其能够被Spring容器管理和注入
public class CustomOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // 默认的会话属性名称，由Spring Security的HttpSessionOAuth2AuthorizationRequestRepository类使用
    private static final String DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME = HttpSessionOAuth2AuthorizationRequestRepository.class.getName() + ".AUTHORIZATION_REQUEST";

    // 用于此存储库实例的会话属性名称
    private final String sessionAttributeName;

    // 构造函数将会话属性名称初始化为默认值
    public CustomOAuth2AuthorizationRequestRepository() {
        this.sessionAttributeName = DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME;
    }

    // 根据请求中的 'state' 参数从会话中加载 OAuth2AuthorizationRequest
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");  // 断言请求对象不为null
        String stateParameter = this.getStateParameter(request);  // 获取请求中的'state'参数值
        if (stateParameter == null) {  // 如果'state'参数为null，则返回null
            return null;
        } else {
            OAuth2AuthorizationRequest authorizationRequest = this.getAuthorizationRequest(request);  // 从会话中获取OAuth2AuthorizationRequest对象
            // 如果获取到的OAuth2AuthorizationRequest不为null，并且其状态等于'state'参数值，则返回该授权请求对象；否则返回null
            return (authorizationRequest != null && stateParameter.equals(authorizationRequest.getState())) ? authorizationRequest : null;
        }
    }

    // 将 OAuth2AuthorizationRequest 保存到会话中
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");  // 断言请求对象不为null
        Assert.notNull(response, "response cannot be null");  // 断言响应对象不为null
        if (authorizationRequest == null) {  // 如果授权请求对象为null，则从会话中移除该授权请求
            this.removeAuthorizationRequest(request, response);
        } else {
            String state = authorizationRequest.getState();  // 获取授权请求的状态
            Assert.hasText(state, "authorizationRequest.state cannot be empty");  // 断言授权请求的状态不为空
            request.getSession().setAttribute(this.sessionAttributeName, authorizationRequest);  // 将授权请求对象存储到会话中
        }
    }

    // 从会话中移除 OAuth2AuthorizationRequest
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(response, "response cannot be null");  // 断言响应对象不为null
        OAuth2AuthorizationRequest authorizationRequest = this.loadAuthorizationRequest(request);  // 从会话中加载授权请求对象
        if (authorizationRequest != null) {  // 如果加载到授权请求对象不为null，则从会话中移除该授权请求
            request.getSession().removeAttribute(this.sessionAttributeName);
        }
        return authorizationRequest;  // 返回移除的授权请求对象
    }

    // 辅助方法：从请求中获取 'state' 参数
    private String getStateParameter(HttpServletRequest request) {
        return request.getParameter("state");  // 返回请求中的'state'参数值
    }

    // 辅助方法：从会话中获取 OAuth2AuthorizationRequest
    private OAuth2AuthorizationRequest getAuthorizationRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 获取当前请求的会话对象，如果会话不存在则返回null
        return (session != null) ? (OAuth2AuthorizationRequest) session.getAttribute(this.sessionAttributeName) : null;  // 从会话中获取指定属性名的OAuth2AuthorizationRequest对象，如果会话或属性不存在则返回null
    }
}
