package com.hzg.pay;

import com.hzg.base.Dao;
import com.hzg.tools.DateUtil;
import com.hzg.tools.PayConstants;
import com.hzg.tools.StrUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class PayDao extends Dao {

    Logger logger = Logger.getLogger(PayDao.class);

    @Autowired
    private StrUtil strUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    public RedisTemplate<String, Long> redisTemplateLong;

    private String currentDay = "";
    private int countLength = 3;

    private RedisAtomicLong counter;

    /**
     * 产生支付编号
     * @return
     */
    public String getNo() {

        String no = PayConstants.no_prefix_pay + strUtil.generateRandomStr(30);

        logger.info("generate no:" + no);

        return no;
    }

    /**
     * 产生一个编号
     * @param prefix 编号前缀，如采购单的编号前缀：CG
     * @return
     */
    public String getNo(String prefix) {
        String key = "counter_" + prefix;

        Long count = 1L;
        Long value = redisTemplateLong.opsForValue().get(key);

        if (value == null) {
            counter = new RedisAtomicLong(key, redisTemplateLong.getConnectionFactory(), count);
            counter.expireAt(dateUtil.getDay(1));
            currentDay = dateUtil.getCurrentDayStr("yyMMdd");

        } else {
            if (counter == null) {
                counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
                counter.set(value);
                counter.expireAt(dateUtil.getDay(1));
                currentDay = dateUtil.getCurrentDayStr("yyMMdd");
            }

            count = counter.incrementAndGet();
        }


        String countStr = String.valueOf(count);

        int minusLength = countLength - countStr.length();
        while (minusLength > 0) {
            countStr = "0" + countStr;
            --minusLength;
        }

        String no = prefix + currentDay + countStr;

        logger.info("generate no:" + no);

        return no;
    }
}
