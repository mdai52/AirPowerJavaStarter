package cn.hamm.demo.module.chat.room.model;

import cn.hamm.demo.module.chat.event.ChatEvent;
import cn.hamm.demo.module.chat.member.MemberEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <h1>房间成员事件</h1>
 *
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RoomMemberEvent extends ChatEvent {
    /**
     * <h2>成员信息</h2>
     */
    private MemberEntity member;
}
