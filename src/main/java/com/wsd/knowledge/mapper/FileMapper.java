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
    @Insert("insert into FileDetail(addFileTime,departmentName,downloadPcs,fileContent,fileNo,fileSize,fileStyle,fileUrl," +
            "lookPcs,photoUrl,title,userId,updatePcs,fileStyleId,username) values(#{addFileTime},#{departmentName},#{downloadPcs},#{fileContent},#{fileNo}," +
            "#{fileSize},#{fileStyle},#{fileUrl},#{lookPcs},#{updatePcs},#{photoUrl},#{title},#{userId},#{fileStyleId},#{username})")
    Integer insertFileDetail(FileDetail fileDetail);

    /**
     * 展示上传文件书面信息
     *
     * @return
     */
    @Select("select f.id,f.fileNo,f.addFileTime,f.username,f.departmentName from fileDetail f  where f.fileDisplay= 1 order by f.addFileTime DESC ")
    List<Map> showFileInfo();

    /**
     * 删除文件，修改属性让其不显示
     *
     * @param id
     * @return
     */
    @Update(" update FileDetail set fileDisplay=0 where id=#{id}")
    Integer updateFileDisplay(@Param("id") int id);

    /**
     * 更新此文件下载次数
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set downloadPcs=downloadPcs+1 where id=#{id}")
    Integer updateDwonPcs(@Param("id") int id);

    /**
     * 更新此文件更新次数
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set updatePcs = updatePcs+1 where id=#{id}")
    Integer updatePcs(@Param("id") int id);

    /**
     * 更新此文件被查看次数
     *
     * @param id
     * @return
     */
    @Update("update FileDetail set lookPcs = lookPcs+1 where id=#{id}")
    Integer lookPcs(@Param("id") int id);


    @Select("select * from FileDetail  where fileDisplay = 1 Order By addFileTime DESC limit #{startSize},#{limit} ")
    List<Map> showAllFile(@Param("startSize") int startSize, @Param("limit") int limit);

    @Select("select count(*) from FileDetail where fileDisplay=1")
    Integer countFile();

    @SelectProvider(type = FileQuery.class, method = "queryFileByDep")
    List<Map> queryFileByIf(Map<String, Object> map);

    @SelectProvider(type = FileQuery.class, method = "countFilePcs")
    Integer countFilePcs(Map<String, Object> map);
    class FileQuery{

         public String queryFileByDep(Map<String,Object> map ){
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
             sql.append(" limit #{startsize},#{limit} ");
             return sql.toString();
         }
        public String countFilePcs(Map<String,Object> map ){
            StringBuffer sql = new StringBuffer();
            sql.append("select o.* from FileDetail  o  where o.fileDisplay=1 ");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND a.fileStyleId = #{fileStyleId} ");
            }

            return sql.toString();
        }
    }
}
