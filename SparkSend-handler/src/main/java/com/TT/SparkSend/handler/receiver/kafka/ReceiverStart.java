package com.TT.SparkSend.handler.receiver.kafka;

import cn.hutool.core.text.StrPool;
import com.TT.SparkSend.handler.utils.GroupIdMappingUtils;
import com.tencentcloudapi.batch.v20170312.models.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.Element;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description 启动消费者
 * @Author TT
 * @Date 2024/9/5
 */
@Slf4j
@Service
public class ReceiverStart {
    /**
     * receiver的消费方法常量
     */
    private static final String RECEIVER_METHOD_NAME = "Receiver.consumer";
    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupId = GroupIdMappingUtils.getAllGroupIds();
    /**
     * 下标（用于迭代groupIds位置）
     */
    private static Integer index = 0;
    @Autowired
    private ApplicationContext context;

    /**
     * 给每个Receiver对象的consumer方法 @KafkaListener赋值相应的groupId
     */
    @Bean
    private static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            if (element instanceof Method) {
                String name = ((Method) element).getDeclaringClass().getSimpleName() + StrPool.DOT + ((Method) element);
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", groupId.get(index));
                }
            }
            return attrs;
        };
    }

    /*
     * @PostConstruct：用来修饰一个非静态的void()方法，该方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
     * @PreDestroy：修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < groupId.size(); i++) {
            context.getBean(Receiver.class);
        }
    }

}


