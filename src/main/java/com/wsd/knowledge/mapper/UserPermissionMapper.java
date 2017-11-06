package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author EdsionGeng
 * @Description 用户操作文件权限持久层
 * @Date:9:04 2017/11/2
 */
public interface UserPermissionMapper {
    /**
     * 添加用户操作文件权限记录
     *
     * @param userPermission
     * @return
     */
    @Insert("insert into UserPermission (addPermissionTime,deleteFile,readFile,updateFile,fileId,userId) " +
            "values(#{addPermissionTime},#{deleteFile},#{readFile},#{updateFile},#{fileId},#{userId})")
    Integer insertUserPermission(UserPermission userPermission);


    /**
     * 查看对单个文件操作权限
     * @param userId
     * @param fileId
     * @return
     */
    @Select("select id,addPermissionTime,deleteFile,fileId,readFile,updateFile userId from UserPermission where userid=#{userId} and fileId=#{fileId}")
    UserPermission queryFilePermission(@Param("userId") Integer userId, @Param("fileId") Integer fileId);
}
