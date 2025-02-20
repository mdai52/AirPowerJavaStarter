package cn.hamm.demo.module.chat.room;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.demo.base.BaseController;
import cn.hamm.demo.module.personnel.user.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("room")
@Description("房间")
public class RoomController extends BaseController<RoomEntity, RoomService, RoomRepository> implements IRoomAction {
    @Permission(authorize = false)
    @Override
    public Json getDetail(@NotNull RoomEntity entity) {
        return super.getDetail(entity);
    }

    @Description("创建房间")
    @PostMapping("create")
    @Permission(authorize = false)
    public Json create(@RequestBody @Validated(WhenCreate.class) RoomEntity room) {
        Long id = service.create(room, getCurrentUserId());
        return Json.entity(id, "房间创建成功");
    }

    @Description("获取我的房间")
    @PostMapping("getMyRoomList")
    @Permission(authorize = false)
    public Json getMyRoomList() {
        RoomEntity filter = new RoomEntity();
        filter.setOwner(new UserEntity().setId(getCurrentUserId()));
        return Json.data(service.filter(filter));
    }

    @Description("获取热门房间")
    @PostMapping("getHotList")
    @Permission(authorize = false)
    public Json getHotList() {
        return Json.data(service.getHotRoomList());
    }
}
