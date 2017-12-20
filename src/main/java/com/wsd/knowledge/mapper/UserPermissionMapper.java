package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserPermission;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into UserPermission (fileId,userId,readFile,addPermissionTime,deleteFile,updateFile) " +
            "values(#{fileId},#{userId},#{readFile},#{addPermissionTime},#{deleteFile},#{updateFile})")
    Integer insertUserPermission(UserPermission userPermission);

    /**
     * 更改是否可以修改文件权限
     * @param userId
     *
     *
     *
     * @param fileId
     * @param operationStyleId
     * @return
     */
    @Update("update UserPermission set updateFile=#{operationStyleId} where userId=#{userId} and fileId=#{fileId}")
    Integer  addUpdatePermission(@Param("userId")Integer userId,@Param("operationStyleId")Integer operationStyleId,@Param("fileId")Integer fileId);

    /**
     * 更改是否可以修改文件权限
     * @param userId
     * @param fileId
     * @param operationStyleId
     * @return
     */
    @Update("update UserPermission set deleteFile=#{operationStyleId} where userId=#{userId} and fileId=#{fileId}")
    Integer  addDeletePermission(@Param("userId")Integer userId,@Param("operationStyleId")Integer operationStyleId,@Param("fileId")Integer fileId);
    /**
     * 查看对单个文件操作权限
     * @param userId
     * @param fileId
     * @return
     */
    @Select("select id,addPermissionTime,deleteFile,fileId,readFile,updateFile userId from UserPermission where userid=#{userId} and fileId=#{fileId}")
    UserPermission queryFilePermission(@Param("userId") Integer userId, @Param("fileId") Integer fileId);


    /**
     * 删除文件权限相关人员
     * @param fileId
     * @return
     */
    @Delete("delete from UserPermission where  fileId=#{fileId}")
    Integer deletePerByFileId(@Param("fileId")Integer fileId);


}
