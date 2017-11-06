package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Random;

/**
 * @Author EdsionGeng
 * @Description 文件界面交互层
 * @Date:11:11 2017/11/2
 */
@RestController
@EnableAutoConfiguration
public class FileController {
    @Autowired
    private FileService fileService;


    /**
     * 查询所有上传文件 组合查询 共用同一个接口
     * 个人
     * @param departmentName
     * @param fileStyleId
     * @param downType
     * @param fileTimeType
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查询所有文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "departmentName", value = "部门名字"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileStyleId", value = "文件类型ID"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "downType", value = "下载量"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileTimeType", value = "上传时间顺序"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "页面信息数量", required = true)

    })
    @RequestMapping(value="show/allFile",method=RequestMethod.GET)
    public JsonResult showAllFile(String departmentName, String  fileStyleId, String downType, String fileTimeType, Integer page, Integer limit) {
        return fileService.showAllFile(departmentName, fileStyleId, downType, fileTimeType, page, limit);

    }

    /**
     * 用户添加文件
     *
     * @param title
     * @param content
     * @param photourl
     * @param fileurl
     * @param userId
     * @return
     */
    @ApiOperation(value = "添加文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "标题", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "content", value = "内容", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "photourl", value = "封面URL", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileurl", value = "附件URL", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileStyleId", value = "文件类型ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "filesize", value = "文件大小", required = true)


    })
    @RequestMapping(value="insertFile.htmls",method = RequestMethod.GET)
    public JsonResult insertFile(@RequestParam(value="title") String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize) {
        return fileService.insertFile(title, content, photourl, fileurl, userId, fileStyleId, filesize);
    }

    /**
     * 批量删除文件
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id ", value = "文件ID 数组", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value="deletefile.htmls",method =RequestMethod.DELETE)
    public JsonResult deleteFile(Integer[]  id,Integer userId) {
        return fileService.deleteFile(id,userId);
    }

    /**
     * 查阅文件执行相应操作
     *
     * @param id
     * @param userId
     * @return
     */
    @ApiOperation(value = "阅读文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value="readfile.htmls",method=RequestMethod.PUT)
    public JsonResult readFile(Integer id, Integer userId) {
        return fileService.readFile(id, userId);
    }

    /**
     * 点击附件后执行逻辑操作
     * @param id
     * @param userId
     * @return
     */
    @ApiOperation(value = "下载文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value="download.htmls",method=RequestMethod.PUT)
    public JsonResult downloadFile(Integer id, Integer userId) {
        return fileService.downloadFile(id, userId);
    }

    /**
     * @param file
     * @return
     */
    //上传简历
    @RequestMapping(value = "file/upload.htmls", method = RequestMethod.POST)
    public JsonResult upload(@RequestParam("file") MultipartFile file) {
        String resumeurl = null;
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                //重新生成文件名，避免乱码问题
                String filename = file.getOriginalFilename();
                String fName = null;
                if (filename.indexOf(".") >= 0) {
                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
                }
                String path = String.valueOf(new Random().nextInt(100)).concat((fName));//拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(path);
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream("static//" + new File(resumeurl)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new JsonResult(2, 0, "上传失败," + e.getMessage(), 0);
            } catch (IOException e) {
                e.printStackTrace();
                return new JsonResult(2, 0, "上传失败," + e.getMessage(), 0);
            }
            //resumeService.updateUrl(phone, resumeurl);
            return new JsonResult(2, resumeurl, "上传成功", 0);
        } else {
            return new JsonResult(2, "", "上传失败，因为文件是空的.", 0);
        }
    }

}
