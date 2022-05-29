package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    @Insert(" INSERT INTO sys_user " +
            " (name, email,password, is_active, nick_name) " +
            " VALUES ( #{user.name}, #{user.email}, #{user.password}, #{user.isActive}, #{user.nickName}) ")
    void insert(@Param("user") User user);
}
