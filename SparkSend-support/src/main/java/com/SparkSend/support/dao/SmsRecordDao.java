package com.SparkSend.support.dao;

import com.SparkSend.support.domain.SmsRecord;
import org.springframework.data.repository.CrudRepository;

import javax.sound.midi.ShortMessage;
import java.util.List;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/20
 */
public interface SmsRecordDao extends CrudRepository<SmsRecord, Long> {

    /**
     * 通过日期和手机号找到发送记录
     * @param phone
     * @param sendDate
     * @return
     */
    List<SmsRecord> findByPhoneAndSendDate(Long phone, Integer sendDate);
}
