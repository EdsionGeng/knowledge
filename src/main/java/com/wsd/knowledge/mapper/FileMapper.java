package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.FileDetail;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 文件持久层
 * @Date:9:06 2017/11/2
 */
public interface FileMapper {
    /**
     * 添加上传文件
     *
     * @param fileDetail
     * @return
     */
    @Insert("insert into FileDetail(departmentName,username,fileStyleId,userId,fileNo,title,fileStyle,fileContent,fileUrl,photoUrl, enclosureInfo" +
            "lookPcs,downloadPcs,updatePcs,fileSize,fileDisplay,addFileTime,fileSpecies,userGroupId) values(#{departmentName},#{username},#{fileStyleId},#{userId}," +
            "#{fileNo},#{title},#{fileStyle},#{fileContent},#{fileUrl},#{photoUrl},#{lookPcs},#{downloadPcs}," +
            "#{updatePcs},#{fileSize},#{fileDisplay},#{enclosureInfo},#{addFileTime},#{fileSpecies},#{userGroupId})")
    Integer insertFileDetail(FileDetail fileDetail);

    /**
     * 更新此文件下载次数
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set downloadPcs=downloadPcs+1 where id=#{id}")
    Integer updateDownPcs(@Param("id") int id);

    /**
     * 更新此文件被查看次数
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set lookPcs = lookPcs+1 where id=#{id}")
    Integer lookPcs(@Param("id") int id);

    /**
     * 查找所有文件分页处理
     *
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select * from FileDetail  where fileDisplay = 1 Order By addFileTime DESC limit #{startSize},#{limit} ")
    List<Map> showAllFile(@Param("startSize") int startSize, @Param("limit") int limit);

    /**
     * 统计数量
     *
     * @return
     */
    @Select("select count(id) from  FileDetail where fileDisplay=1 ")
    Integer countFile();

    /**
     * 组合查询文件结果
     */
    @SelectProvider(type = FileQuery.class, method = "queryFileByDep")
    List<Map> queryFileByIf(Map<String, Object> map);

    /**
     * 组合查询结果数量
     *
     * @param map
     * @return
     */
    @SelectProvider(type = FileQuery.class, method = "countFilePcs")
    Integer countFilePcs(Map<String, Object> map);

    /**
     * 查找文件ID返回前台执行其他操作
     *
     * @param fileNo
     * @return
     */
    @Select("select id from FileDetail where fileNo=#{fileNo}")
    Integer selectIdByIf(@Param("fileNo") String fileNo);

    /**
     * 删除文件让其不显示
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set fileDisplay=0 where id=#{id}")
    Integer updateFileShow(@Param("id") String id);

    /**
     *  单个文件
     *
     * @param fileId
     * @return
     */
    @Update("select * from  FileDetail where id=#{id} and fileDisplay=1")
    FileDetail showSingleFile(@Param("id") Integer  fileId);

    /**
     * 更新文件
     *
     * @param fileStyleId
     * @param fileContent
     * @param id
     * @return
     */
    @Update("update FileDetail set fileStyleId=#{fileStyleId},fileContent=#{fileContent},updatePcs=updatePcs+1 where id=#{id}")
    Integer updateFileContent(@Param("fileStyleId") Integer fileStyleId, @Param("fileContent") String fileContent, @Param("id") Integer id);

    /**
     * 更新文件
     *
     * @param fileStyleId
     * @param fileContent
     * @param id
     * @param fileUrl
     * @return
     */
    @Update("update FileDetail set fileStyleId=#{fileStyleId},fileContent=#{fileContent},fileUrl=#{fileUrl},updatePcs=updatePcs+1 where id=#{id}")
    Integer updateFileContentUrl(@Param("fileStyleId") Integer fileStyleId, @Param("fileContent") String fileContent, @Param("id") Integer id, @Param("fileUrl") String fileUrl);

    /**
     * 查看用户能看的全部文件
     *
     * @param userId
     * @param startSize
     * @param limit
     * @return
     */
//       this.departmentName = departmentName;
//        this.username = username;
//        this.fileStyleId = fileStyleId;
//        this.userId = userId;
//        this.fileNo = fileNo;
//        this.title = title;
//        this.fileStyle = fileStyle;
//        this.fileContent = fileContent;
//        this.fileUrl = fileUrl;
//        this.photoUrl = photoUrl;
//        this.lookPcs = lookPcs;
//        this.downloadPcs = downloadPcs;
//        this.updatePcs = updatePcs;
//        this.fileSize = fileSize;
//        this.fileDisplay = fileDisplay;
//        this.enclosureInfo=enclosureInfo;
//        this.addFileTime = addFileTime;
//        this.fileSpecies=fileSpecies;
    @Select("select f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId} order by f.addFileTime Desc limit #{startSize},#{limit} ")
    List<Map> showUserLookFile(@Param("userId") Integer userId, @Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 展示所有公司性质的文件
     * @return
     */
    @Select("select f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f  where f.fileSpecies=2 order by f.addFileTime limit #{startSize},#{pageSize}")
    List<Map> showCompanyFile(@Param("startSize")Integer startSize,@Param("pageSize")Integer pageSize);
    /**
     * 统计用户能看的全部文件数量
     *
     * @param userId
     * @return
     */
    @Select("select count(*)   from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and  f.fileDisplay=1 and u.userId=#{userId} order by f.addFileTime Desc ")
    Integer countUserLookFile(@Param("userId") Integer userId);


    @SelectProvider(type = FileQuery.class, method = "showUserIfLookFile")
    List<Map> showUserIfLookFile(Map<String, Object> map);

    @SelectProvider(type = FileQuery.class, method = "showUserIfFilePcs")
    Integer showUserIfFilePcs(Map<String, Object> map);
    /**
     * 全局搜索结果
     *
     * @param userId
     * @param searchContent
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId}   and  f.departmentName " +
            "like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%') or f.title like concat('%',#{searchContent},'%') order by f.addFileTime Desc limit #{startSize},#{limit} ")
    List<Map> showSearchFile(@Param("userId") Integer userId, @Param("searchContent") String searchContent, @Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 全局搜索结果数量
     *
     * @param userId
     * @param searchContent
     * @return
     */
    @Select("select count(*) from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId} and f.departmentName " +
            "like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%') or f.title like concat('%',#{searchContent},'%')  ")
    Integer countSearchFile(@Param("userId") Integer userId, @Param("searchContent") String searchContent);

    /**
     * 全局 再加条件
     * @param map
     * @return
     */
    @SelectProvider(type = FileQuery.class, method = "showIfSearchFile")
    List<Map> showIfSearchFile(Map<String, Object> map);


    /**
     * 全局 再加条件 总数
     * @param map
     * @return
     */
    @SelectProvider(type = FileQuery.class, method = "countIfSearchFile")
     Integer countIfSearchFile(Map<String, Object> map);

    /**
     * 更新文档类型
     *
     * @param id
     * @param fileStyleId
     * @param fileStyle
     * @return
     */
    @Update(" update  FileDetail set fileStyleId = #{fileStyleId},fileStyle=#{fileStyle} where id=#{id}")
    Integer updateFileStyle(@Param("id") Integer id, @Param("fileStyleId") Integer fileStyleId, @Param("fileStyle") String fileStyle);

    /**
     * 统计某一文档类型的文件数量
     *
     * @param fileStyleId
     * @return
     */
    @Select("select count(*) from FileDetail where  fileStyleId = #{fileStyleId} and fileDisplay=1 ")
    Integer countStylePcs(@Param("fileStyleId") Integer fileStyleId);

    class FileQuery {
        public String queryFileByDep(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime from FileDetail  o  where o.fileDisplay=1 and o.addFileTime >= #{startDate} ");
            if (StringUtils.isNotEmpty((String) map.get("endDate"))) {
                sql.append(" AND o.addFileTime <= #{endDate} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.title like concat('%',#{title},'%')");
            }
            sql.append(" order by o.addFileTime Desc limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String countFilePcs(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select  count(id ) from  FileDetail  o  where o.fileDisplay=1 and o.addFileTime >= #{startDate}");
            if (StringUtils.isNotEmpty((String) map.get("endDate"))) {
                sql.append(" AND o.addFileTime = #{endDate} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName}");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.title like concat('%',#{title},'%')");
            }
            return sql.toString();
        }

        public String showUserIfLookFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime from FileDetail  o  left join UserPermission  u on  o.id=u.fileId where u.readFile=1 and o.fileDisplay=1 and  u.userId=#{userId} ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName like concat ('%',#{departmentName},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }
            sql.append("  order by o.addFileTime Desc limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String showUserIfFilePcs(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(o.id) from FileDetail  o  left join UserPermission  u on  o.id=u.fileId where u.readFile=1 and o.fileDisplay=1 and  u.userId=#{userId} ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName like concat ('%',#{departmentName},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }
            return sql.toString();
        }

        public String showIfSearchFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId}   and  f.departmentName " +
                    "like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%') or f.title like concat('%',#{searchContent},'%') ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND f.departmentName like concat ('%',#{departmentName},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND f.fileStyleId = #{fileStyleId} ");
            }
            sql.append(" order by f.addFileTime Desc limit #{startSize},#{limit} ");
            return sql.toString();
        }
        public String countIfSearchFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(f.id) from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId}   and  f.departmentName " +
                    "like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%') or f.title like concat('%',#{searchContent},'%') ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND f.departmentName like concat ('%',#{departmentName},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND f.fileStyleId = #{fileStyleId} ");
            }
            return sql.toString();
        }
    }
}
