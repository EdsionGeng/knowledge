package com.wsd.knowledge.mapper1;
import com.wsd.knowledge.entity.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
*@Author EdsionGeng
*@Description 用户模块持久层
*@Date:9:20 2017/10/31
*/

public interface UserRepositoty  {

    @Select("select id,username,password from SystemUser  where username = #{name} and password = #{password}")
    SystemUser findUser(@Param("name") String name,@Param("password")String password);
}
