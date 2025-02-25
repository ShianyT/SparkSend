package cron.xxl.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.TT.SparkSend.common.constant.CommonConstant;
import com.TT.SparkSend.common.enums.RespStatusEnum;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.support.domain.MessageTemplate;
import cron.xxl.constants.XxlJobConstant;
import cron.xxl.entity.XxlJobGroup;
import cron.xxl.entity.XxlJobInfo;
import cron.xxl.enums.*;
import cron.xxl.service.CronTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.search.AddressTerm;
import java.util.Date;
import java.util.Objects;

/**
 * @Description xxlJob工具类
 * @Author TT
 * @Date 2024/9/18
 */
@Component
public class XxlJobUtils {
    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.jobHandlerName}")
    private String jobHandlerName;

    @Autowired
    private CronTaskService cronTaskService;

    /**
     * 构建xxlJobInfo信息
     */
    public XxlJobInfo buildXxlJobInfo(MessageTemplate messageTemplate) {

        String scheduleCron = messageTemplate.getExpectPushTime();
        // 如果没有指定cron表达式，说明立即执行（给到xxl-job延迟5秒的cron表达式）
        if (messageTemplate.getExpectPushTime().equals(String.valueOf(CommonConstant.FALSE))) {
            scheduleCron = DateUtil.format(DateUtil.offsetSecond(new Date(), XxlJobConstant.DELAY_TIME), CommonConstant.CRON_FORMAT);
        }

        XxlJobInfo xxlJobInfo = XxlJobInfo.builder()
                .jobGroup(queryJobGroupId()).jobDesc(messageTemplate.getName())
                .author(messageTemplate.getCreator())
                .scheduleConf(scheduleCron)
                .scheduleType(ScheduleTypeEnum.CRON.name())
                .misfireStrategy(MisfireStrategyEnum.DO_NOTHING.name())
                .executorRouteStrategy(ExecutorRouteStrategyEnum.CONSISTENT_HASH.name())
                .executorHandler(XxlJobConstant.JOB_HANDLER_NAME)
                .executorParam(String.valueOf(messageTemplate.getId()))
                .executorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name())
                .executorTimeout(XxlJobConstant.TIME_OUT)
                .executorFailRetryCount(XxlJobConstant.RETRY_COUNT)
                .glueType(GlueTypeEnum.BEAN.name())
                .triggerStatus(CommonConstant.FALSE)
                .glueRemark(CharSequenceUtil.EMPTY)
                .glueSource(CharSequenceUtil.EMPTY)
                .alarmEmail(CharSequenceUtil.EMPTY)
                .childJobId(CharSequenceUtil.EMPTY).build();

        if (Objects.nonNull(messageTemplate.getCronTaskId())) {
            xxlJobInfo.setId(messageTemplate.getCronTaskId());
        }
        return xxlJobInfo;
    }

    /**
     * 根据就配置文件的内容获取jobGroupId，没有则创建
     */
    private Integer queryJobGroupId() {
        BasicResultVO<Integer> basicResultVO = cronTaskService.getGroupId(appName, jobHandlerName);
        if (Objects.nonNull(basicResultVO.getData())) {
            XxlJobGroup xxlJobGroup = XxlJobGroup.builder().appname(appName).title(jobHandlerName).addressType(CommonConstant.FALSE).build();
            if (RespStatusEnum.SUCCESS.getCode().equals(cronTaskService.createGroup(xxlJobGroup).getData())) {
                return (int)cronTaskService.getGroupId(appName,jobHandlerName).getData();
            }
        }
        return basicResultVO.getData();
    }
}
