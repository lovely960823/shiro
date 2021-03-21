package mlt.boot.blog.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis  key 过期回调
 * @author Administrator
 *
 */
@Component
public class KeyExpiredListentForMessageListener implements MessageListener  {

	@Autowired
    public RedisTemplate<String,String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.err.println("过期key"+message+"*******************************");
    }
	


}
