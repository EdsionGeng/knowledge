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
    @Insert("insert into FileDetail(departmentName,username,userId,fileStyleId,fileNo,title,fileStyle,fileContent,fileUrl,photoUrl, " +
            "lookPcs,downloadPcs,updatePcs,fileSize,fileDisplay,enclosureInfo,addFileTime,fileSpecies,userGroupId,companyId) values(#{departmentName},#{username},#{userId},#{fileStyleId}," +
            "#{fileNo},#{title},#{fileStyle},#{fileContent},#{fileUrl},#{photoUrl},#{lookPcs},#{downloadPcs}," +
            "#{updatePcs},#{fileSize},#{fileDisplay},#{enclosureInfo},#{addFileTime},#{fileSpecies},#{userGroupId},#{companyId})")
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
     * @param map
     * @return
     */
    //@Select("select f.id,f.departmentName,f.fileStyle,f.username,f.fileNo,f.title,f.addFileTime from FileDetail f where f.fileDisplay = 1 and f.companyId in (#{companyId}) Order By addFileTime ${sortType} limit #{startSize},#{limit} ")
    @SelectProvider(type = FileQuery.class, method = "showAllFile")
    List<Map> showAllFile(Map<String, Object> map);

    /**
     * 统计数量
     *
     * @return
     */
    // @Select("select count(id) from  FileDetail where fileDisplay=1 and f.companyId in (#{companyId}) ")
    @SelectProvider(type = FileQuery.class, method = "countAllFile")
    Integer countFile(@Param("companyId") String companyId);


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
     * 单个文件
     *
     * @param id
     * @return
     */
    @Select("select f.id,f.departmentName,f.fileStyle,f.fileStyleId,f.fileSpecies,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.fileContent  from  FileDetail f where f.id=#{id} ")
    Map showSingleFile(@Param("id") Integer id);

    /**
     * 更新文件
     *
     * @param id
     * @param content
     * @param fileurl
     * @param fileStyleId
     * @param fileSize
     * @param photourl
     * @param describle
     * @return
     */
    @Update("update FileDetail set fileStyleId=#{fileStyleId},fileContent=#{content},fileUrl=#{fileurl},fileSize=#{fileSize},photoUrl=#{photourl},enclosureInfo=#{describle},fileStyle=#{fileStyleName},fileSpecies=#{fileSpecies},updatePcs=updatePcs+1 where id=#{id}")
    Integer updateFileContentUrl(@Param("id") Integer id, @Param("content") String content, @Param("fileurl") String fileurl, @Param("fileStyleId") Integer fileStyleId, @Param("fileSize") String fileSize, @Param("photourl") String photourl, @Param("describle") String describle, @Param("fileStyleName") String fileStyleName, @Param("fileSpecies") Integer fileSpecies);

//    /**
//     * 查看用户能看的全部文件
//     *
//     * @param userId
//     * @return
//     */
//    @Select("select  distinct  f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f left join UserPermission  u on  f.id=u.fileId where f.fileSpecies=0 and f.fileDisplay=1 and  u.userId=#{userId} order by f.addFileTime Desc  ")

    @Select("select f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.lookPcs from  ((select  distinct   f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.lookPcs from FileDetail f left join UserPermission  u on  f.id=u.fileId where f.fileSpecies=0 and f.fileDisplay=1 and  u.userId=#{userId})UNION \n" +
            " (select  f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime ,f.lookPcs from FileDetail f    where f.fileSpecies=2 and f.fileDisplay = 1 and f.companyId in (#{companyId})) " +
            "UNION  (select  f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime ,f.lookPcs from FileDetail f where f.fileSpecies=1 and f.fileDisplay = 1 and f.userGroupId in (#{result}))" +
            "UNION  (select   f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime ,f.lookPcs from FileDetail f where  f.fileDisplay = 1 and f.userId=#{userId}))  as f order by f.addFileTime ${sortType} limit #{startSize},#{pageSize}")
    List<Map> showUserLookFile(@Param("userId") Integer userId, @Param("companyId") String companyId, @Param("result") String result, @Param("sortType") String sortType, @Param("startSize") Integer startSize, @Param("pageSize") Integer pageSize);


    @Select("select f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.lookPcs from  ((select    f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.lookPcs from FileDetail f left join UserPermission  u on  f.id=u.fileId where f.fileSpecies=0 and f.fileDisplay=1 and  u.userId=#{userId})UNION \n" +
            " (select  distinct  f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime,f.lookPcs from FileDetail f    where f.fileSpecies=2 and f.fileDisplay = 1 and f.companyId in (#{companyId})) " +
            "UNION  (select distinct  f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime ,f.lookPcs from FileDetail f where f.fileSpecies=1 and f.fileDisplay = 1 and f.userGroupId in (#{result}))" +
            "UNION  (select distinct  f.id,f.departmentName,f.fileStyle,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime ,f.lookPcs from FileDetail f where  f.fileDisplay = 1 and f.userId=#{userId}))  as f order by f.lookPcs ${sortType} limit #{startSize},#{pageSize}")
    List<Map> showUserLookFile1(@Param("userId") Integer userId, @Param("companyId") String companyId, @Param("result") String result, @Param("sortType") String sortType, @Param("startSize") Integer startSize, @Param("pageSize") Integer pageSize);

    /**
     * 统计用户能看的全部文件数量
     *
     * @param userId
     * @return
     */
    @Select("select count(f.id) from  ((select  f.id from FileDetail f left join UserPermission  u on  f.id=u.fileId where f.fileSpecies=0 and f.fileDisplay=1 and  u.userId=#{userId})UNION \n" +
            " (select  f.id from FileDetail f    where f.fileSpecies=2 and f.fileDisplay = 1 and f.companyId in (#{companyId})) " +
            "UNION  (select   f.id from FileDetail f where f.fileSpecies=1 and f.fileDisplay = 1 and f.userGroupId in (#{result}))" +
            "UNION  (select   f.id from FileDetail f where  f.fileDisplay = 1 and f.userId=#{userId}))  as f ")
    Integer countUserLookFile(@Param("userId") Integer userId, @Param("companyId") String companyId, @Param("result") String result);


    @SelectProvider(type = FileQuery.class, method = "showUserIfLookFile")
    List<Map> showUserIfLookFile(Map<String, Object> map);

    @SelectProvider(type = FileQuery.class, method = "showUserIfCompanyFile")
    List<Map> showUserLookCompanyFile(Map<String, Object> map);

    @SelectProvider(type = FileQuery.class, method = "showUserIfGroupFile")
    List<Map> showUserLookGroupFile(Map<String, Object> map);

    /**
     * 全局搜索结果
     *
     * @param userId
     * @param searchContent
     * @return
     */
    @Select("select distinct  f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f left join UserPermission  u on  f.id=u.fileId where f.fileDisplay=1 and " +
            " u.userId=#{userId} and  ( f.fileContent like concat('%',#{searchContent},'%') or  f.title like concat('%',#{searchContent},'%') or f.fileUrl like concat('%',#{searchContent},'%'))  order by f.addFileTime desc")
    List<Map> showSearchFile1(@Param("userId") Integer userId, @Param("searchContent") String searchContent);

    /**
     * 搜索所有公司性质的文件
     *
     * @return
     */
    @Select("select  distinct f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f    where f.fileSpecies=2 and f.fileDisplay = 1 and f.companyId in (#{companyId}) and (f.title like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%') or f.fileUrl like concat('%',#{searchContent},'%')) order by f.addFileTime desc ")
    List<Map> searchCompanyFile1(@Param("searchContent") String searchContent, @Param("companyId") String companyId);

    /**
     * 展示部门性质的文件
     *
     * @return
     */
    @Select("select  distinct f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f  where f.fileSpecies=1 and f.fileDisplay = 1 and f.userGroupId  in (#{result}) and (f.title like concat('%',#{searchContent},'%') or f.fileUrl like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%'))  order by f.addFileTime desc ")
    List<Map> searchGroupFile1(@Param("searchContent") String searchContent, @Param("result") String result);

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

    @Update(" update FileDetail  set fileStyle=#{fileStyleName} where fileStyleId=#{fileStyleId}")
    Integer updateFileStyleName(@Param("fileStyleId") Integer fileStyleId, @Param("fileStyleName") String fileStyleName);

    /**
     * 统计某一文档类型的文件数量
     *
     * @param fileStyleId
     * @return
     */
    @Select("select count(*) from FileDetail where  fileStyleId = #{fileStyleId} and fileDisplay=1 ")
    Integer countStylePcs(@Param("fileStyleId") Integer fileStyleId);

    /**
     * 并发问题下删除文件
     *
     * @param id
     * @return
     */
    @Delete("delete from FileDetail where id=#{id}")
    Integer deleteFile(@Param("id") Integer id);


    class FileQuery {
        public String queryFileByDep(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select   o.id, o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime,o.fileStyle from FileDetail  o  where o.fileDisplay=1 ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" AND o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("startDate"))) {

                sql.append(" AND o.addFileTime >= #{startDate} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("endDate"))) {
                sql.append(" AND o.addFileTime <= #{endDate} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.title like concat('%',#{title},'%')");
            }
            if (StringUtils.isNotEmpty((String) map.get("sortType"))) {
                if (map.get("sortType").equals("desc")) {
                    sql.append("  order by o.addFileTime desc ");
                }
                if (map.get("sortType").equals("asc")) {
                    sql.append("  order by o.addFileTime asc ");
                }
            }
            sql.append("   limit #{startSize},#{limit}");
            return sql.toString();
        }

        public String countFilePcs(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select  count(id ) from  FileDetail  o  where o.fileDisplay=1 and o.addFileTime >= #{startDate}");

            if (StringUtils.isNotEmpty((String) map.get("endDate"))) {
                sql.append(" AND o.addFileTime = #{endDate} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" AND o.companyId in (" + (String) map.get("companyId") + ")");
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
            sql.append("select distinct o.id, o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime,o.fileStyle from FileDetail  o  left join UserPermission  u on  o.id=u.fileId where  o.fileDisplay=1 and o.fileSpecies=0 and u.userId=#{userId} ");

            if (StringUtils.isNotEmpty((String) map.get("groupId"))) {

                sql.append(" AND o.userGroupId in (" + (String) map.get("groupId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId in (" + (String) map.get("fileStyleId") + ")");
            }
            sql.append("  order by o.addFileTime Desc  ");
//            System.out.print(sql);
            return sql.toString();
        }

        public String showUserIfCompanyFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select distinct o.id, o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime,o.fileStyle from FileDetail  o  left join UserPermission  u on  o.id=u.fileId where  o.fileDisplay=1 and o.fileSpecies=2 ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" AND o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("groupId"))) {
                sql.append(" AND o.userGroupId in (" + (String) map.get("groupId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId in (" + (String) map.get("fileStyleId") + ")");
            }
            sql.append("  order by o.addFileTime Desc  ");
            System.out.print(sql);
            return sql.toString();
        }

        //select  distinct f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime from FileDetail f    where f.fileSpecies=1 and f.fileDisplay = 1 and f.userGroupId  in (#{result}) and f.title like concat('%',#{searchContent},'%') or f.fileUrl like concat('%',#{searchContent},'%') or f.fileContent like concat('%',#{searchContent},'%')  order by f.addFileTime desc
        public String showUserIfGroupFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select distinct o.id, o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime,o.fileStyle from FileDetail  o  left join UserPermission  u on  o.id=u.fileId where  o.fileDisplay=1 and o.fileSpecies=1  ");
            if (StringUtils.isNotEmpty((String) map.get("groupId"))) {
                sql.append(" AND o.userGroupId in (" + (String) map.get("groupId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("fileStyleId"))) {
                sql.append(" AND o.fileStyleId in (" + (String) map.get("fileStyleId") + ")");
            }
            sql.append("  order by o.addFileTime Desc  ");
            return sql.toString();

        }

        public String showAllFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select distinct o.id, o.departmentName,o.username,o.fileSize,o.fileNo,o.title,o.fileUrl,o.photoUrl,o.enclosureInfo,o.addFileTime,o.fileStyle from FileDetail  o   where  o.fileDisplay=1   ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" AND o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("sortType"))) {
                if (map.get("sortType").equals("desc")) {
                    sql.append("  order by o.addFileTime desc ");
                }
                if (map.get("sortType").equals("asc")) {
                    sql.append("  order by o.addFileTime asc ");
                }
            }
            sql.append("  limit #{startSize},#{limit}  ");
            return sql.toString();
        }

        public String countAllFile(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(o.id) from FileDetail  o   where  o.fileDisplay=1   ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" AND o.companyId in (" + (String) map.get("companyId") + ")");
            }
            return sql.toString();
        }
    }
}
