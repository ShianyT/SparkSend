package com.TT.SparkSend.handler.utils;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.ChannelType;
import com.TT.SparkSend.common.enums.EnumUtil;
import com.TT.SparkSend.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description groupId 标识每一个消费者组
 * @Author TT
 * @Date 2024/9/1
 */
public class GroupIdMappingUtils {
    private GroupIdMappingUtils() {
    }

    /**
     * 获取所有的groupIds
     * 不同的渠道不同的消息类型拥有自己的groupId
     */
    public static List<String> getAllGroupIds() {
        ArrayList<String> groupIds = new ArrayList<>();
        for(ChannelType channelType: ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }

    /**
     * 根据TaskInfo获取当前消息的groupId
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = EnumUtil.getEnumByCode(taskInfo.getSendChannel(), ChannelType.class).getCodeEn();
        String msgCodeEn = EnumUtil.getEnumByCode(taskInfo.getMsgType(), MessageType.class).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}
