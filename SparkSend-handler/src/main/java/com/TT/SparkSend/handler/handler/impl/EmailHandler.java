package com.TT.SparkSend.handler.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.dto.model.EmailContentModel;
import com.TT.SparkSend.common.enums.ChannelType;
import com.TT.SparkSend.handler.handler.BaseHandler;
import com.TT.SparkSend.support.utils.AccountUtils;
import com.TT.SparkSend.support.utils.SparkSendFileUtils;
import com.google.common.base.Throwables;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @Description 邮件发送处理
 * @Author TT
 * @Date 2024/9/11
 */
@Slf4j
@Component
public class EmailHandler extends BaseHandler {

    @Autowired
    private AccountUtils accountUtils;

    @Value("${SparkSend.business.upload.crowd.path}")
    private String dataPath;

    public EmailHandler() {
        channelCode = ChannelType.EMAIL.getCode();
        // TODO 限流操作
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        MailAccount account = getAccountConfig(taskInfo.getSendAccount());
        try {
            List<File> files = CharSequenceUtil.isNotBlank(emailContentModel.getUrl()) ? SparkSendFileUtils
                    .getRemoteUrl2File(dataPath, CharSequenceUtil.split(emailContentModel.getUrl(), StrPool.COMMA)) : null;
            if (CollUtil.isEmpty(files)) {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true);
            } else {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true, files.toArray(new File[files.size()]));
            }
        } catch (Exception e) {
            log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
            return false;
        }
        return true;
    }

    private MailAccount getAccountConfig(Integer sendAccount) {
        MailAccount account = accountUtils.getAccountById(sendAccount, MailAccount.class);
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            account.setAuth(account.isAuth()).setStarttlsEnable(account.isStarttlsEnable())
                    .setSslEnable(account.isSslEnable()).setCustomProperty("mail.smtp.ssl.socketFactory", sf);
            account.setTimeout(25000).setConnectionTimeout(25000);
        } catch (Exception e) {
            log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
        }
        return account;
    }

    /**
     * 邮箱 api 不支持撤回消息
     */
    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }


}
