package mlt.boot.blog.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfigForMessageListener {

	 @Autowired
	    private ApplicationContext applicationContext;

	    @Autowired
	    private RedisConnectionFactory connectionFactory;

	    @Bean
	    public KeyExpiredListentForMessageListener registerListener() {
	        RedisMessageListenerContainer redisMessageListenerContainer = applicationContext.getBean(RedisMessageListenerContainer.class);
	        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
	        KeyExpiredListentForMessageListener listener = new KeyExpiredListentForMessageListener();
	        redisMessageListenerContainer.addMessageListener(listener,new PatternTopic("__keyevent@*__:expired"));
	        return listener;
	    }
}
