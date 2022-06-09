package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.OffsetDateTime;

@Mapper
public interface UserMapper {
    @Insert(" INSERT INTO sys_user " +
            " (username, email, passwd, create_time) " +
            " VALUES ( #{user.username}, #{user.email}, #{user.password}, #{user.createTime}) ")
    void insert(@Param("user") User user);

    @Select(" SELECT EXISTS(SELECT username FROM sys_user WHERE username=#{username}) ")
    boolean usernameExist(@Param("username") String username);

    @Select(" SELECT EXISTS(SELECT email FROM sys_user WHERE email=#{email}) ")
    boolean emailExist(@Param("email") String email);

    @Select(" SELECT id, username, passwd, email, create_time FROM sys_user WHERE email=#{email} ")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "username", property = "username", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "passwd", property = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", javaType = OffsetDateTime.class, jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE),
    })
    User queryUserByEmail(@Param("email") String email);

    @Select(" SELECT id, username, passwd, email, create_time FROM sys_user WHERE username=#{username} ")
    @ResultMap({"userMap"})
    User queryUserByName(@Param("username") String username);


}
