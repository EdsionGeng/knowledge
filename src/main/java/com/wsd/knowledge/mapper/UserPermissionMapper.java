package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserPermission;
import org.apache.ibatis.annotations.Insert;

public interface UserPermissionMapper {
    @Insert("insert into UserPermission (addPermissionTime,deleteFile,readFile,updateFile,fileId,userId) " +
            "values(#{addPermissionTime},#{deleteFile},#{readFile},#{updateFile},#{fileId},#{userId})")
    Integer insertUserPermission(UserPermission userPermission);
}
