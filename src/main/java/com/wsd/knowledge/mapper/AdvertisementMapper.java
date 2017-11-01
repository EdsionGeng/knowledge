package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.CommonAdvertisement;
import org.apache.ibatis.annotations.Insert;

public interface AdvertisementMapper {

    @Insert("insert into CommonAdvertisement(adContent,adTitle,addUser,departmentName,sendObject,sendTime,userId) " +
            "values(#{adContent},#{adTitle},#{addUset},#{departmentName},#{sendObject},#{sendTime},#{userId})")
    Integer insertAd(CommonAdvertisement commonAdvertisement);
}
