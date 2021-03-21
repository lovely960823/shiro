package mlt.boot.blog.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
public class UserUtil {
	
	
	/**
	 * 根据token获取用户名称
	 * @return
	 */
	public static String getUsername() {
        return JwtUtil.getUsername(getToken());
    }
	
	 /**
     * 获取请求token
     * @return
     */
    public static String getToken() {
        String token = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        token = request.getHeader("Authorization");
        // 去掉前缀和空格
        token = token.replace("Bearer ", "").trim();
        return token;
    }
}
