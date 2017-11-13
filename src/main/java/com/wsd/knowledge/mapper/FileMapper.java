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
            "lookPcs,downloadPcs,updatePcs,fileSize,fileDisplay,addFileTime) values(#{departmentName},#{username},#{fileStyleId},#{userId},#{fileNo},#{title},#{fileStyle},#{fileContent},#{fileUrl},#{photoUrl},#{lookPcs},#{downloadPcs}," +
            "#{updatePcs},#{fileSize},#{fileDisplay},#{ enclosureInfo},#{addFileTime})")
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
    @Select("select count(id) from ((select id from FileDetail where fileDisplay=1 limit #{startSize},#{limit})as s)")
    Integer countFile(@Param("startSize") Integer startSize, @Param("limit") Integer limit);

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
    Integer updateFileShow(@Param("id") Integer id);


    /**
     * 更新文件
     * @param fileStyleId
     * @param fileContent
     * @param id
     * @return
     */
    @Update("update FileDetail set fileStyleId=#{fileStyleId},fileContent=#{fileContent},updatePcs=updatePcs+1 where id=#{id}")
    Integer updateFileContent(@Param("fileStyleId") Integer fileStyleId, @Param("fileContent") String fileContent, @Param("id") Integer id);

    @Update("update FileDetail set fileStyleId=#{fileStyleId},fileContent=#{fileContent},fileUrl=#{fileUrl},updatePcs=updatePcs+1 where id=#{id}")
    Integer updateFileContentUrl(@Param("fileStyleId") Integer fileStyleId, @Param("fileContent") String fileContent, @Param("id") Integer id, @Param("fileUrl") String fileUrl);

    @Select("select f.* from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and f.fileDisplay=1 and  u.userId=#{userId} order by f.addFileTime Desc limit #{startSize},#{limit} ")
    List<Map> showUserLookFile(@Param("userId")Integer userId,@Param("startSize")Integer startSize,@Param("limit")Integer limit);

    @Select("select count(*)   from FileDetail f left join UserPermission  u on  f.id=u.fileId where u.readFile=1 and  f.fileDisplay=1 and u.userId=#{userId} order by f.addFileTime Desc ")
    Integer  countUserLookFile(@Param("userId")Integer userId,@Param("startSize")Integer startSize,@Param("limit")Integer limit);

    class FileQuery {
        public String queryFileByDep(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select o.* from FileDetail  o  where o.fileDisplay=1 ");
            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND a.fileStyleId = #{fileStyleId} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("type"))) {
                if ("asc".equals(map.get("type"))) {
                    sql.append("order by o.addFileTime asc");
                } else if ("desc".equals(map.get("type"))) {
                    sql.append("order by o.addFileTime desc");
                }
            }
            sql.append(" limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String countFilePcs(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select  count(id ) from  FileDetail  o  where o.fileDisplay=1 ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND o.fileStyleId = #{fileStyleId} ");
            }

            return sql.toString();
        }
    }
}
