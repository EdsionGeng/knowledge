package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserRecAdvertisement;
import org.apache.ibatis.annotations.Insert;

public interface UserRecAdMapper {
    @Insert("insert into UserRecAdvertisement (commonId,userId,ifRead,recAdTime) " +
            "values(#{commonId},#{ifRead},#{recAdTime},#{userId})")
    Integer insertUserPermission(UserRecAdvertisement userRecAdvertisement);
}
