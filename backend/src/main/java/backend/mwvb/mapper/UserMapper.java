package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert(" INSERT INTO sys_user " +
            " (name, email,password, is_active, nick_name) " +
            " VALUES ( #{user.name}, #{user.email}, #{user.password}, false, #{user.nickName}) ")
    void insert(@Param("user") User user);

    @Select(" SELECT EXISTS(SELECT name FROM sys_user WHERE name=#{username}) ")
    boolean usernameExist(@Param("username") String username);

    @Select(" SELECT EXISTS(SELECT email FROM sys_user WHERE email=#{email}) ")
    boolean emailExist(@Param("email") String email);
}
