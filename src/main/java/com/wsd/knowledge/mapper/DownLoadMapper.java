package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.UserDownload;
import org.apache.ibatis.annotations.Insert;
/**
*@Author EdsionGeng
*@Description用户下载文件持久层
*@Date:9:05 2017/11/2
*/
public interface DownLoadMapper {

    /**
     * 添加用户下载记录
     * @param userDownload
     * @return
     */
    @Insert(" insert into UserDownload (downloadTime,fileId,userId) values (#{downloadTime},#{fileId},#{userId})")
    Integer insertUserDownloadRecord(UserDownload userDownload);

}
