package cn.hamm.demo.module.chat.member;

import cn.hamm.demo.base.BaseRepository;
import cn.hamm.demo.module.chat.room.RoomEntity;
import cn.hamm.demo.module.user.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * <h1>Repository</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface MemberRepository extends BaseRepository<MemberEntity> {
    /**
     * <h2>根据用户和房间查询</h2>
     *
     * @param user 用户
     * @param room 房间
     * @return 成员
     */
    MemberEntity getByUserAndRoom(UserEntity user, RoomEntity room);
}
