package com.wsd.knowledge.mapper;
import com.wsd.knowledge.entity.FileKind;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @Author EdsionGeng
 * @Description 文件类型持久层
 * @Date:13:23 2017/11/3
 */
public interface FileKindMapper {
    /**
     * 根据ID 获得文件类型信息
     * @param id
     * @return
     */
    @Select("select * from FileKind where id=#{id}")
    FileKind selectFileKind(@Param("id") Integer id);

}
