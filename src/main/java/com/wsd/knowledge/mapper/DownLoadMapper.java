package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserDownload;
import org.apache.ibatis.annotations.Insert;

public interface DownLoadMapper {

    @Insert(" insert into UserDownload (downloadTime,fileId,userId) values (#{downloadTime},#{fileId},#{userId})")
    Integer insertUserDownloadRecord(UserDownload userDownload);

}
