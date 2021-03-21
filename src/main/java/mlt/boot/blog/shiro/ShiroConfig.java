package mlt.boot.blog.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	@Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        bean.setLoginUrl("/login");
        // 添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("tokenFilter", new TokenFilter());
        bean.setFilters(filterMap);
        /**
         * 自定义拦截规则
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        //filterRuleMap.put("/sys/**","anon");
        filterRuleMap.put("/login","anon");
        filterRuleMap.put("/noPer","anon");//不需要认证，可以直接访问的
        // 其余请求都要经过BearerTokenFilter自定义拦截器
        filterRuleMap.put("/**", "tokenFilter");
        bean.setFilterChainDefinitionMap(filterRuleMap);
        return bean;
    }
	
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
    	/**
    	 * 托管userrealm
    	 */
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        userRealm.setAuthenticationTokenClass(JwtToken.class);//如果不进行设置就默认UsernamePasswordToken
        securityManager.setRealm(userRealm);
        /**
         * 禁用session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
	
    /**
     * 自定义realm
     * @return
     */
	@Bean(name = "userRealm")
	public UserRealm userRealm(){
		return new UserRealm();
	}
	

	//开启shiro aop注解支持  作用在方法上
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
}
