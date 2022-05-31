package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.apache.ibatis.annotations.*;

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

    @Select(" SELECT id, name, password, email, is_active, nick_name FROM sys_user WHERE name=#{username} ")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
            @Result(column = "is_active", property = "isActive"),
            @Result(column = "nick_name", property = "nickName"),
    })
    User queryUserByName(@Param("username") String username);


    @Select(" SELECT id, name, password, email, is_active, nick_name FROM sys_user WHERE email=#{email} ")
    @ResultMap({"userMap"})
    User queryUserByEmail(@Param("email") String email);

}
