package mlt.boot.blog.exception;

import mlt.boot.blog.result.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private  final  static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler. class);
    
    @ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public Result NoPerm(HttpServletRequest req){
		logger.info("请求[ "+req.getRequestURI()+" ]验证未通过:{}", "无权限操作");
		return Result.fail("无权限操作",403);
	}

    @ExceptionHandler(value= RuntimeException.class)
    @ResponseBody
    public Result runTimeException(RuntimeException e, HttpServletRequest req){
        logger.error("错误请求地址URI [{}]", req.getRequestURI());
        return Result.fail(e.getMessage());
    }
}
