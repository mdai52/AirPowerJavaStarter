package cn.hamm.demo.module.chat.member;

import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.demo.base.BaseService;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.chat.room.RoomEntity;
import cn.hamm.demo.module.personnel.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class MemberService extends BaseService<MemberEntity, MemberRepository> {
    /**
     * <h2>根据用户ID和房间ID获取成员信息</h2>
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 成员信息
     */
    public MemberEntity getMember(long userId, long roomId) {
        return repository.getByUserAndRoom(new UserEntity().setId(userId), new RoomEntity().setId(roomId));
    }

    /**
     * <h2>添加成员</h2>
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 成员ID
     */
    public long addMember(long userId, long roomId) {
        MemberEntity member = getMember(userId, roomId);
        ServiceError.FORBIDDEN_EXIST.whenNotNull(member);
        // 没有查到成员信息
        RoomEntity room = Services.getRoomService().get(roomId);
        member = new MemberEntity()
                .setUser(new UserEntity().setId(userId))
                .setRoom(new RoomEntity().setId(roomId));
        if (userId == room.getOwner().getId()) {
            // 是所有者
            member.setRole(MemberRole.OWNER.getKey());
        } else {
            member.setRole(MemberRole.VISITOR.getKey());
        }
        return add(member);
    }

    /**
     * <h2>获取成员信息，若不存在则自动创建</h2>
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 成员信息
     */
    public MemberEntity getMemberWithAutoCreate(long userId, long roomId) {
        MemberEntity member = getMember(userId, roomId);
        if (Objects.isNull(member)) {
            return get(addMember(userId, roomId));
        }
        return member;
    }
}
