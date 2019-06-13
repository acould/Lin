package star.warehouse.rocketmq.test;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import star.mq.consumer.listener.SingleMessageListener;
import star.util.StringUtil;


/**
 * rocketmq 消费者测试
 * @author:chistar
 * @since:2019年5月30日下午2:44:12
 */
public class TestMqConsumer extends SingleMessageListener {
    private static Logger logger = LoggerFactory.getLogger(TestMqConsumer.class);

//    @Resource
//    private UserBaseService userBaseService;
//
//    @Autowired
//    private RedisClient redisClient;



    @Override
    public boolean onMessage(MessageExt message, ConsumeConcurrentlyContext Context) {
        String userIdStr = new String(message.getBody());
        if (StringUtil.isEmpty(userIdStr)) {
            logger.error("TestMqConsumer userid is null");
            return true;
        }

        logger.info("TestMqConsumer userid={}",userIdStr);

        return true;
    }

}
