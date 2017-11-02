package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.CommonAdvertisement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

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
    Integer deleteAd(@Param("id") int id);
}
