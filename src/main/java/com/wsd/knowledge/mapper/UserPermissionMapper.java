package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserPermission;
import org.apache.ibatis.annotations.Insert;
/**
*@Author EdsionGeng
*@Description 用户操作文件权限持久层
*@Date:9:04 2017/11/2
*/
public interface UserPermissionMapper {
    /**
     * 添加用户操作文件权限记录
     * @param userPermission
     * @return
     */
    @Insert("insert into UserPermission (addPermissionTime,deleteFile,readFile,updateFile,fileId,userId) " +
            "values(#{addPermissionTime},#{deleteFile},#{readFile},#{updateFile},#{fileId},#{userId})")
    Integer insertUserPermission(UserPermission userPermission);
}
