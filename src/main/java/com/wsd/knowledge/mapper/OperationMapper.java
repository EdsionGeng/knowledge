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
     *
     * @param operationLog
     * @return
     */
    @Insert("insert into OperationLog(departmentName,username,fileId,operationStyle,operationTime,userId) " +
            "values(#{departmentName},#{username},#{fileId},#{operationStyle},#{operationTime},#{userId})")
    Integer insertOperationLog(OperationLog operationLog);

    /**
     * 查看查阅文档操作日志是否已存在
     *
     * @param userId
     * @param fileId
     * @return
     */
    @Select("select id from OperationLog where userId =#{userId} and fileId=#{fileId} and operationStyle = 4")
    Integer queryLookLog(@Param("userId") Integer userId, @Param("fileId") Integer fileId);


    @Select("select id from OperationLog where userId =#{userId} and fileId=#{fileId} and operationStyle = 5")
    Integer queryDownLog(@Param("userId") Integer userId, @Param("fileId") Integer fileId);

    /**
     * 查询某一文件操作日志记录
     *
     * @param fileId
     * @return
     */
    @Select("select o.*,CASE o.operationStyle WHEN 1 THEN '添加文档'  WHEN 2 THEN '删除文档' WHEN 3 THEN '更改文档' WHEN 4 THEN '查阅文档' WHEN 5 THEN '下载文档' ELSE '其他'\n" +
            " END AS operation from OperationLog  o  where o.fileId = #{fileId} Order by o.operationTime DESC limit #{startSize},#{limit} ")
    List<Map> showAllOperationLog(@Param("fileId") int fileId,@Param("startSize")Integer startSize,@Param("limit")Integer limit);

    /**
     * 统计日志记录数量
     *
     * @param fileId
     * @return
     */
    @Select("select count(*) from OperationLog where  fileId=#{fileId}")
    Integer countOperationLog(@Param("fileId") int fileId);


    /**
     * 查询个人历史下载记录
     *
     * @param userId
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select o.* ,f.*  from OperationLog o  Left Join FileDetail f on o.fileId=f.id  where o.operationStyle=5 and o.userId=#{userId} Order By o.operationTime DESC" +
            " limit #{startSize},#{limit} ")
    List<Map> queryUserDownload(@Param("userId") Integer userId, @Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 统计个人历史下载记录数量
     *
     * @param userId
     * @return
     */
    @Select("select count(*) from OperationLog where operationStyle=5 and userId=#{userId}")
    Integer counUserDownPcs(@Param("userId") Integer userId);

    /**
     * 超管查询历史下载
     *
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select o.*,f.*  from OperationLog o  Left Join FileDetail f on o.fileId=f.id  where o.operationStyle=5  Order By o.operationTime DESC" +
            " limit #{startSize},#{limit} ")
    List<Map> queryAllDownload(@Param("startSize") Integer startSize, @Param("limit") Integer limit);


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
     * @param map
     * @return
     */
    @SelectProvider(type = Operation.class, method = "queryLogByDep")
    List<Map> queryLogByIf(Map<String, Object> map);


    /**
     * 统计查询日志数量
     *
     * @param map
     * @return
     */
    @SelectProvider(type = Operation.class, method = "countLogByDep")
    Integer countLogByIf(Map<String, Object> map);

    /**
     * 展示上传所有文件书面信息
     *
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select f.* from FileDetail f  where f.fileDisplay= 1 and userId=#{userId} order by f.addFileTime DESC limit #{startSize},#{limit}")
    List<Map> showUserUpFile(@Param("userId") Integer userId, @Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 统计个人上传文件 总数
     * @param userId
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select count(id) from ((select  id from FileDetail f  where f.fileDisplay= 1 and userId=#{userId}  limit #{startSize},#{limit}) as s)")
    Integer countAllFilePcs(@Param("userId") Integer userId, @Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 查询当日查看文件日志次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=4")
    Integer countDataLookPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询当日下载次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=5")
    Integer countDataDownloadPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询当日上传文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=1")
    Integer countDataAddPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询当日删除文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=2")
    Integer countDataDeletePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询当日更改次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=3")
    Integer countDataUpdatePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 本周查看文件日志次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=4")
    Integer countWeekLookPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询本周下载次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=5")
    Integer countWeekDownloadPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询本周上传文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=1")
    Integer countWeekAddPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询本周删除文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=2")
    Integer countWeekDeletePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询本周更改次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=3")
    Integer countWeekUpdatePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 本月查看文件日志次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=4")
    Integer countMonthLookPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询本月下载次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=5")
    Integer countMonthDownloadPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询本月上传文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=1")
    Integer countMonthAddPcs(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询本月删除文件次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=2")
    Integer countMonthDeletePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询本月更改次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select count(*) from OperationLog where operationTime between #{startTime} and #{endTime}  and operationStyle=3")
    Integer countMonthUpdatePcs(@Param("startTime")String startTime,@Param("endTime")String endTime);


    class Operation {
        public String queryLogByDep(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select o.*,CASE o.operationStyle WHEN 1 THEN '添加文档' WHEN 2 THEN '删除文档' WHEN 3 THEN '更改文档' WHEN 3 THEN '查阅文档' ELSE '其他' " +
                    "END AS operation from OperationLog  o  where o.fileId=#{fileId}");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName = #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND o.operationStyle = #{operationStyle} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("type"))) {
                if ("asc".equals(map.get("type"))) {
                    sql.append("order by a.entryDate asc");
                } else if ("desc".equals(map.get("type"))) {
                    sql.append("order by a.entryDate desc");
                }
            }
            sql.append(" limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String countLogByDep(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(*) from OperationLog  o  where o.fileId=#{fileId}");

            if (StringUtils.isNotEmpty((String) map.get("departmentName"))) {
                sql.append(" AND o.departmentName <= #{departmentName} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("operationStyle"))) {
                sql.append(" AND o.operationStyle = #{operationStyle} ");
            }
            sql.append(" limit #{startSize},#{limit} ");
            return sql.toString();
        }






    }





}
