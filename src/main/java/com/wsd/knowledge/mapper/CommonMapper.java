package com.wsd.knowledge.mapper;


import com.wsd.knowledge.entity.FileKind;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 各个模块共用的数据接口
 *
 * @Author EdsionGeng
 * @Description
 * @Date:9:14 2017/10/31
 */
public interface CommonMapper {


    /**
     * 文档目录树形结构
     *
     * @param map
     * @return
     */
    @SelectProvider(type = KindTree.class, method = "showKindTree")
    List<FileKind> findKindList(Map<String, Object> map);

    /**
     * 添加文件目录
     * @param parentId
     * @param docName
     * @param operationTime
     * @return
     */
    @Insert(" insert into FileKind (fileKindName,fileParentId,operationTime) values(#{docName},#{parentId},#{operationTime})")
    Integer insertDocRule(@Param("parentId") Integer parentId, @Param("docName") String docName, @Param("operationTime") String operationTime);


    @Select("select id from FileKind where fileParentId=#{parentId} and fileKindName=#{docName} order by operationTime DESC limit 1")
    Integer selectDocId(@Param("parentId")int parentId,@Param("docName")String  docName);


    /**
     * 删除文件目录
     * @param fileStyleId
     * @return
     */
    @Delete(" delete  from  FileKind where id = #{fileStyleId}")
    Integer deleteDocRule(@Param("fileStyleId") Integer fileStyleId);

    /**
     * 修改目录名称
     * @param fileStyleId
     * @return
     */
    @Update(" update FileKind set fileKindName=#{fileName} where id = #{fileStyleId}")
    Integer updateDocRule(@Param("fileStyleId") Integer fileStyleId,@Param("fileName")String fileName);

    /**
     * 修改目录名称及父id
     * @param fileStyleId
     * @return
     */
    @Update(" update FileKind set fileKindName=#{fileName},fileParentId=#{parentId} where id = #{fileStyleId}")
    Integer updateDocStyle(@Param("fileStyleId") Integer fileStyleId,@Param("fileName")String fileName,@Param("parentId")Integer parentId);

    class KindTree {
        public String showKindTree(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from FileKind   where 1=1 ");
            if (StringUtils.isNotEmpty((String) map.get("fileParentId"))) {
                sql.append(" AND fileParentId = #{pid} ");
            }
            return sql.toString();
        }
    }
}
