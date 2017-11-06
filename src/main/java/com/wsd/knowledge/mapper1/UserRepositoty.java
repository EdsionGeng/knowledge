package com.wsd.knowledge.mapper1;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.entity.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
*@Author EdsionGeng
*@Description 用户模块持久层
*@Date:9:20 2017/10/31
*/

public interface UserRepositoty  {
    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    @Select("select id,username,password from SystemUser  where username = #{name} and password = #{password}")
    SystemUser findUser(@Param("name") String name,@Param("password")String password);

    /**
     * 获取个人信息
     * @param id
     * @return
     */
    @Select("select username,department from SystemUser where id=#{id} ")
    SystemUser  findInfo(@Param("id")int id);

    //查看部门列表
@Select("   SELECT id,deptno,no,pid,deptno AS name FROM Department_new" +
        "        WHERE 1 = 1" +
        "        <if test=id != null and id != ''>" +
        "            AND pid = #{id}" +
        "        </if>")
    List<NewDepartment> findList(NewDepartment department);


}
