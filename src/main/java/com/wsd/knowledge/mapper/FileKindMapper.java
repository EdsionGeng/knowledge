package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.FileKind;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 文件类型持久层
 * @Date:13:23 2017/11/3
 */
public interface FileKindMapper {
    /**
     * 根据ID 获得文件类型信息
     *
     * @param id
     * @return
     */
    @Select("select id ,fileKindName,fileParentId from FileKind where id=#{id}")
    FileKind selectFileKind(@Param("id") Integer id);


    @Select("select a.id from  FileKind a LEFT JOIN FileKind b ON b.id =a.fileParentId where b.fileParentId=#{id} OR b.id=#{id}")
    List<Integer> queryAllSonId(@Param("id") Integer id);

    @Select("select id as fileStyleId,fileKindName,fileParentId from FileKind where fileParentId=0")
    List<Map> showSeniorDoc();

    @Select("select id as fileStyleId,fileKindName,fileParentId from FileKind where fileParentId=#{pid}")
    List<Map> showSonDoc(@Param("pid")Integer pid);

}
