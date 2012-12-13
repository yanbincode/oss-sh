package security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

//public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
//
////	// 利用注解
////	// 与spring-security.xml里的myFilter的属性securityMetadataSource对应，
////	// 其他的两个组件，已经在AbstractSecurityInterceptor定义
////	private FilterInvocationSecurityMetadataSource securityMetadataSource;
////
////	@Override
////	public SecurityMetadataSource obtainSecurityMetadataSource() {
////		return this.securityMetadataSource;
////	}
////
////	@Override
////	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
////			ServletException {
////		FilterInvocation fi = new FilterInvocation(request, response, chain);
////		invoke(fi);
////
////	}
////
////	private void invoke(FilterInvocation fi) throws IOException, ServletException {
////		// object为FilterInvocation对象
////		// super.beforeInvocation(fi);源码
////		// 1.获取请求资源的权限
////		// 执行Collection<ConfigAttribute> attributes =
////		// SecurityMetadataSource.getAttributes(object);
////		// 2.是否拥有权限
////		// this.accessDecisionManager.decide(authenticated, object, attributes);
////		InterceptorStatusToken token = super.beforeInvocation(fi);
////		try {
////			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
////		} finally {
////			super.afterInvocation(token, null);
////		}
////	}
////
////	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
////		return securityMetadataSource;
////	}
////
////	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
////		this.securityMetadataSource = securityMetadataSource;
////	}
////
////	@Override
////	public void init(FilterConfig arg0) throws ServletException {
////
////	}
////
////	@Override
////	public void destroy() {
////
////	}
////
////	@Override
////	public Class<?> getSecureObjectClass() {
////		return null;
////	}
//
//}
