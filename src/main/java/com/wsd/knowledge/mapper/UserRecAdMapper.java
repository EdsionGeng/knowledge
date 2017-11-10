package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserRecAdvertisement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description用户接收公告持久层
 * @Date:8:46 2017/11/2
 */
public interface UserRecAdMapper {
    /**
     * 添加接收到的公告
     * @param userRecAdvertisement
     * @return
     */
    @Insert("insert into UserRecAdvertisement (commonId,userId,ifRead,recAdTime) " +
            "values(#{commonId},#{ifRead},#{recAdTime},#{userId})")
    Integer insertUserRecAd(UserRecAdvertisement userRecAdvertisement);

    /**
     * 展示用户收到的公告数量
     * @param userId
     * @param startSize
     * @param limit
     * @return
     */
    @Select("select c.adContent ad ,c.adTitle at,u.recAdtime re,u.commonId from UserRecAdvertisement u left join CommonAdvertisement c on u.commonId=c.id order by u.recAdtime DESC limit #{startSize},#{limit}")
    List<Map>  showUserRecAd(@Param("userId") int userId,@Param("startSize")int startSize,@Param("limit")int limit);

    /**
     * 统计总数量
     * @return
     */
    @Select("select count(*) from UserRecAdvertisement")
    Integer countUserAdPcs();


    /**
     * 更新已读公告的状态
     * @param commonId
     * @param userId
     * @return
     */
    @Update("update  UserRecAdvertisement set ifRead=1 where userId=#{userId} and commonId=#{commonId} ")
    Integer updateCommonStatus(@Param("commonId")int commonId,@Param("userId")int userId);




}
