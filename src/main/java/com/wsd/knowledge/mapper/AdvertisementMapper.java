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
    @Insert("insert into CommonAdvertisement(adContent,adTitle,addUser,departmentName,sendObject,sendTime,userId,adStyle,companyId) " +
            "values(#{adContent},#{adTitle},#{addUser},#{departmentName},#{sendObject},#{sendTime},#{userId},#{adStyle},#{companyId})")
    Integer insertAd(CommonAdvertisement commonAdvertisement);


    @Select("select adTitle,sendTime,addUser,departmentName,adContent,adStyle from CommonAdvertisement where id=#{id}")
    CommonAdvertisement queryComAd(@Param("id") int id);


    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @Delete("delete from CommonAdvertisement where id=#{id}")
    Integer deleteAd(@Param("id") String id);


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
     * @return
     */

    @SelectProvider(type = CommonAd.class, method = "queryAllAds")
    List<Map> showAllCommon(Map<String, Object> map);

    /**
     * 统计展示所有公告数量
     *
     * @return
     */
    @SelectProvider(type = CommonAd.class, method = "countAllAds")
    Integer countAdPcs(Map<String, Object> map);


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

    @Select("select departmentName,username,recAdTime from UserRecAdvertisement where commonId=#{commonId} and ifRead=1 ")
    List<Map> showAdPerInfo(@Param("commonId") Integer commonId);

    @Select("select departmentName,username,recAdTime from UserRecAdvertisement where commonId=#{commonId} and ifRead=0 ")
    List<Map> showAdPerNoInfo(@Param("commonId") Integer commonId);

    class CommonAd {
        public String queryAdByIf(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select distinct  o.id,o.adTitle,o.departmentName,o.addUser,o.sendTime,o.sendObject,o.adStyle from CommonAdvertisement o  ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" where o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("date1"))) {
                sql.append(" AND o.sendtime >= #{date1} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("date2"))) {
                sql.append(" AND o.sendtime <= #{date2} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.adtitle like concat('%',#{title},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("adStyle"))) {
                sql.append(" AND o.adStyle =#{adStyle} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("sortType"))) {
                if (map.get("sortType").equals("desc")) {
                    sql.append("  order by o.sendtime desc ");
                }
                if (map.get("sortType").equals("asc")) {
                    sql.append("  order by o.sendtime asc ");
                }
            }
            sql.append("  limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String countAdByIf(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(o.id)  from CommonAdvertisement  o   ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" where o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("date1"))) {
                sql.append(" AND o.sendtime >= #{date1} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("date2"))) {
                sql.append(" AND o.sendtime <= #{date2} ");
            }
            if (StringUtils.isNotEmpty((String) map.get("title"))) {
                sql.append(" AND o.adtitle like concat('%',#{title},'%') ");
            }
            if (StringUtils.isNotEmpty((String) map.get("adStyle"))) {
                sql.append(" AND o.adStyle= #{adStyle} ");
            }
            return sql.toString();
        }

        public String queryAllAds(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select distinct  o.id,o.adTitle,o.departmentName,o.addUser,o.sendTime,o.sendObject,o.adStyle,o.adContent  from CommonAdvertisement  o   ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" where o.companyId in (" + (String) map.get("companyId") + ")");
            }
            if (StringUtils.isNotEmpty((String) map.get("sortType"))) {
                if (map.get("sortType").equals("desc")) {
                    sql.append("  order by o.sendtime desc ");
                }
                if (map.get("sortType").equals("asc")) {
                    sql.append("  order by o.sendtime asc ");
                }
            }
            sql.append("  limit #{startSize},#{limit} ");
            return sql.toString();
        }

        public String countAllAds(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(o.id)  from CommonAdvertisement  o ");
            if (StringUtils.isNotEmpty((String) map.get("companyId"))) {
                sql.append(" where o.companyId in (" + (String) map.get("companyId") + ")");
            }
            return sql.toString();
        }
    }
}
