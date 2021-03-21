package mlt.boot.blog.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import mlt.boot.blog.result.Result;

public class TokenFilter extends BasicHttpAuthenticationFilter{

	 /**
     * 作用：判断是否认证过了，通俗来说，就是登陆了没。
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        return  null != subject && subject.isAuthenticated();
    }

    /**
     * 没有登录，进入认证
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException{
        //完成token登入
        //1.检查请求头中是否含有token
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String token = httpServletRequest.getHeader("Authorization");
        //2. 如果客户端没有携带token，拦下请求
        if(null==token||"".equals(token)){
            httpServletResponse.getWriter().println(Result.fail(" Token is Valid!!! "));
            return false;
        }
        //3. 如果有，对进行进行token验证
        JwtToken jwtToken = new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
            /**
             * 这里抛异常处理
             * Result(code=400, msg=Realm [mlt.boot.blog.config.UserRealm@2e2bff56] does not support authentication 
             * token [mlt.boot.blog.config.JwtToken@1db51365].  
             * Please ensure that the appropriate Realm implementation is configured correctly or that the realm accepts 
             * AuthenticationTokens of this type., data=null)
             */
        } catch (AuthenticationException e) {
        	httpServletResponse.getWriter().println(Result.fail(e.getMessage()));
            return false;
        }
        return true;
    }


	
	 /**
	  * 方法执行前
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
