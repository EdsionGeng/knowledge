package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.OperationLog;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 文件操作日志持久层
 * @Date:17:20 2017/11/1
 */
public interface OperationMapper {

    /**
     * 添加对某一文件操作日志 更新 删除
     * @param operationLog
     * @return
     */
    @Insert("insert into OperationLog(departmentName,username,fileId,operationStyle,operationTime,userId) " +
            "values(#{departmentName},#{username},#{fileId},#{operationStyle},#{operationTime},#{userId})")
    Integer insertOperationLog(OperationLog operationLog);


    /**
     * 查询某一文件操作日志记录
     * @param fileId
     * @return
     */
    @Select("select o.*,CASE o.operationStyle WHEN 1 THEN ‘添加文档'WHEN 2 THEN '删除文档' WHEN 3 THEN '更改文档' WHEN 4 THEN '查阅文档' WHEN 5 THEN '下载文档' ELSE ’其他'"+
            " END AS operation from OperationLog  o  where o.fileId=#{fileId} order by o.operationTime DESC ")
    List<Map> showAllOperationLog(@Param("fileId")int fileId);

    /**
     * 统计日志记录数量
     * @param fileId
     * @return
     */
    @Select("select count(*) from OperationLog where  fileId=#{fileId}")
    Integer countOperationLog(@Param("fileId")int fileId);

    /**
     * 查询个人历史下载记录
     * @param userId
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select o.*，f.*  from OperationLog o  Left Join FileDetail f on o.fileId=f.id  where o.operationStyle=5 and o.userId=#{userId} Order By o.operationTime DESC" +
            " limit #{startSize},#{limit} ")
    List<Map> queryUserDownload(@Param("userId")Integer userId,@Param("startSize")Integer startSize,@Param("limit")Integer limit);

    /**
     * 统计个人历史下载记录数量
     * @param userId
     * @return
     */
    @Select("select count(*) from OperationLog where o.operationStyle=5 and o.userId=#{userId}")
    Integer counUserDownPcs(@Param("userId")Integer userId);

    /**
     * 超管查询历史下载
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select o.*，f.*  from OperationLog o  Left Join FileDetail f on o.fileId=f.id  where o.operationStyle=5  Order By o.operationTime DESC" +
            " limit #{startSize},#{limit} ")
    List<Map> queryAllDownload(@Param("startSize")Integer startSize,@Param("limit")Integer limit);


    /**
     * 统计个人历史下载记录数量
     *
     * @return
     */
    @Select("select count(*) from OperationLog where o.operationStyle=5 ")
    Integer counAllDownPcs();

    /**
     * 组合查询操作日志
     *
     *
     * @param map
     * @return
     */
    @SelectProvider(type = Operation.class, method = "queryLogByDep")
    List<Map> queryLogByIf(Map<String, Object> map);


    /**
     * 统计查询日志数量
     * @param map
     * @return
     */
    @SelectProvider(type = Operation.class, method = "countLogByDep")
    Integer countLogByIf(Map<String, Object> map);

    /**
     * 展示上传所有文件书面信息
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select f.* from FileDetail f  where f.fileDisplay= 1 and userId=#{userId} order by f.addFileTime DESC limit #{startSize},#{limit}")
    List<Map> showUserUpFile(@Param("userId")Integer userId,@Param("startSize")Integer startSize,@Param("limit")Integer limit);

    @Select("select count(*) from FileDetail where fileDisplay= 1 and userId=#{userId}")
    Integer countAllFilePcs(@Param("userId")Integer userId);

    class Operation {
        public String queryLogByDep(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
            sql.append("select o.*,CASE o.operationStyle WHEN 1 THEN '添加文档' WHEN 2 THEN '删除文档' WHEN 3 THEN '更改文档' WHEN 3 THEN '查阅文档' ELSE '其他' " +
                    "END AS operation from OperationLog  o  where o.fileId=#{fileId}");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName <= #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND a.operationStyle = #{operationStyle} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("type"))) {
                if ("asc".equals(map.get("type"))) {
                    sql.append("order by a.entryDate asc");
                } else if ("desc".equals(map.get("type"))) {
                    sql.append("order by a.entryDate desc");
                }
            }
            sql.append(" limit #{startsize},#{limit} ");
            return sql.toString();
    }

    public String countLogByDep(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from OperationLog  o  where o.fileId=#{fileId}");

        if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
            sql.append(" AND o.departmentName <= #{departmentName} ");
        }
        if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
            sql.append(" AND a.operationStyle = #{operationStyle} ");
        }
        return sql.toString();
    }
}
}
