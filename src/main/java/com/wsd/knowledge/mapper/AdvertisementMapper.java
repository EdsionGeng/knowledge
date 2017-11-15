package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.CommonAdvertisement;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 公告持久层
 * @Date:8:47 2017/11/2
 */

public interface AdvertisementMapper {

    /**
     * 添加公告
     *
     * @param commonAdvertisement
     * @return
     */
    @Insert("insert into CommonAdvertisement(adContent,adTitle,addUser,departmentName,sendObject,sendTime,userId) " +
            "values(#{adContent},#{adTitle},#{addUser},#{departmentName},#{sendObject},#{sendTime},#{userId})")
    Integer insertAd(CommonAdvertisement commonAdvertisement);

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @Delete("delete from CommonAdvertisement where id=#{id}")
    Integer deleteAd(@Param("id") String  id);


    /**
     * 删除接收的公告
     *
     * @param id
     * @return
     */
    @Delete("delete from UserRecAdvertisement where commonId=#{id}")
    Integer deleteRecAd(@Param("id") String id);

    /**
     * 更新公告已读状态
     *
     * @param userId
     * @param commonId
     * @return
     */
    @Update("update UserRecAdvertisement set ifRead =1 where userId=#{userId} and commonId=#{commonId}")
    Integer updateAdStatus(@Param("userId") Integer userId, @Param("commonId") Integer commonId);


    /**
     * 取刚添加的公告ID
     *
     * @param title
     * @param userId
     * @return
     */
    @Select("select id  from CommonAdvertisement where adTitle=#{title} and userId=#{userId}  order by sendTime Desc limit 1")
    Integer queryCommonID(@Param("title") String title, @Param("userId") Integer userId);


    /**
     * 展示所有公告
     *
     * @param startSize
     * @param limit
     * @return
     */
    @Select(" select id ,adContent,adTitle,addUser,departmentName,sendObject,sendTime,userId from CommonAdvertisement limit #{startSize},#{limit}")
    List<Map> showAllCommon(@Param("startSize") Integer startSize, @Param("limit") Integer limit);

    /**
     * 统计展示所有公告数量
     *
     *
     * @return
     */
    @Select("select count(id) from  CommonAdvertisement ")
    Integer countAdPcs();

    /**
     * 组合查询公告
     *
     * @param map
     * @return
     */
    @SelectProvider(type = CommonAd.class, method = "queryAdByIf")
    List<Map> showAllCommonByIf(Map<String, Object> map);

    /**
     * 统计组合查询公告数量
     *
     * @param map
     * @return
     */
    @SelectProvider(type = CommonAd.class, method = "countAdByIf")
    Integer countCommonByIf(Map<String, Object> map);

    /**
     * 统计一条广告发送的总人数
     *
     * @param commonId
     * @return
     */
    @Select("select count(id) from UserRecAdvertisement where commonId=#{commonId}")
    Integer countAdRead(@Param("commonId") Integer commonId);

    /**
     * 统计一条广告发送的已读人数
     *
     * @param commonId
     * @return
     */
    @Select("select count(id) from UserRecAdvertisement where commonId=#{commonId} and ifRead=1")
    Integer countAdNoRead(@Param("commonId") Integer commonId);

    class CommonAd {
        public String queryAdByIf(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select o.* from CommonAdvertisement o  ");
            if (StringUtils.isNotEmpty((String) map.get("date1"))) {
                sql.append(" where o.sendtime >= #{date1} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("date2"))) {
                sql.append(" AND o.sendtime <= #{date2} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.adtitle like concat('%',#{title},'%') ");
            }

            sql.append(" limit #{startSize},#{limit} ");
            return sql.toString();

        }

        public String countAdByIf(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(id)  from CommonAdvertisement  o   ");

            if (StringUtils.isNotEmpty((String) map.get("date1"))) {
                sql.append(" where o.sendtime >= #{date1} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("date2"))) {
                sql.append(" AND o.sendtime <= #{date2} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.adtitle like concat('%',#{title},'%') ");
            }

            return sql.toString();

        }

    }
}
