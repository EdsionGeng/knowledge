package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
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
import java.util.UUID;

/**
 * @Author EdsionGeng
 * @Description 文件界面交互层
 * @Date:11:11 2017/11/2
 */
@Api(description = "文件视图层接口", value = "文件视图层接口")
@RestController
@EnableAutoConfiguration
public class FileController {
    @Autowired
    private FileService fileService;
    /**
     * 查询所有上传文件 组合查询 共用同一个接口
     * 个人
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "查询所有文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "departmentName", value = "部门名字"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileStyleId", value = "文件类型ID"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "文件标题"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "startDate", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "endDate", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "current", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "页面信息数量", required = true)
    })
    @RequestMapping(value = "show/allFile", method = RequestMethod.POST)
    public JsonResult showAllFile(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String title = String.valueOf(jsonObject.get("title"));
        String startDate = String.valueOf(jsonObject.get("startDate"));
        String endDate = String.valueOf(jsonObject.get("endDate"));
        String departmentName = String.valueOf(jsonObject.get("departmentName"));
        String fileStyleId = String.valueOf(jsonObject.get("fileStyleId"));
        Integer current = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        return fileService.showAllFile(departmentName, fileStyleId, title, startDate, endDate, current, pageSize);
    }
    /**
     * 用户添加文件
     *
     * @param object
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
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "filesize", value = "文件大小", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileSpecies", value = "文件种类", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "describle", value = "文件描述", required = true),
    })
    @RequestMapping(value = "insertFile.htmls", method = RequestMethod.POST)
    public JsonResult insertFile(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String title = String.valueOf(jsonObject.get("title"));
        String content = String.valueOf(jsonObject.get("content"));
        String photourl = String.valueOf(jsonObject.get("photourl"));
        String filesize = String.valueOf(jsonObject.get("filesize"));
        String fileurl = String.valueOf(jsonObject.get("fileurl"));
        String describle = String.valueOf(jsonObject.get("describle"));
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        Integer fileSpecies = Integer.parseInt(String.valueOf(jsonObject.get("fileSpecies")));
        return fileService.insertFile(title, content, photourl, fileurl, userId, fileStyleId, filesize, describle,fileSpecies);
    }
    /**
     * 批量删除文件
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "批量删除文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileIds ", value = "文件ID 数组", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value = "deletefile.htmls", method = RequestMethod.POST)
    public JsonResult deleteFile(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String ids = String.valueOf(jsonObject.get("fileIds"));
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        return fileService.deleteFile(ids, userId);
    }

    /**
     * 查阅文件执行相应操作
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "阅读文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value = "readfile.htmls", method = RequestMethod.POST)
    public JsonResult readFile(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer  fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        return fileService.readFile(fileId, userId);
    }

//    /**
//     * 点击附件后执行逻辑操作
//     *
//     * @param fileId
//     * @param userId
//     * @return
//     */
//    @ApiOperation(value = "下载文件接口", notes = "传递必要参数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = true),
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
//    })
//    @RequestMapping(value = "download.htmls", method = RequestMethod.PUT)
//    public JsonResult downloadFile(Integer fileId, Integer userId) {
//        return fileService.downloadFile(fileId, userId);
//    }

    /**
     * 更改文件
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "更改文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "content", value = "文档内容", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileurl", value = "文件上传路径", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileStyleId", value = "文件类型ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "chooseUser", value = "是否选人", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileSize", value = "文件大小", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileStyleName", value = "文档类型名称", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "photourl", value = "封面url", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "describle", value = "附件描述", required = true),
    })
    @RequestMapping(value = "updateFile.htmls", method = RequestMethod.POST)
    public JsonResult updateFileDetail(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        String  fileStyleName = String.valueOf(jsonObject.get("fileStyleName"));
        String  content = String.valueOf(jsonObject.get("content"));
        String  fileurl = String.valueOf(jsonObject.get("fileurl"));
        String  chooseUser = String.valueOf(jsonObject.get("chooseUser"));
        String  fileSize = String.valueOf(jsonObject.get("fileSize"));
        String  photourl = String.valueOf(jsonObject.get("photourl"));
        String  describle = String.valueOf(jsonObject.get("describle"));
        return fileService.updateFileDetail(fileId, content, fileurl, fileStyleId, userId,chooseUser,fileSize,photourl,describle,fileStyleName);
    }

    /**
     * 个人全部文件显示页面
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "个人全部文件显示接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Intege", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Intege", name = "current", value = "页数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "departmentName", value = "部门名字"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileStyleId", value = "文件类型ID"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userGroupId", value = "个人组别ID"),
    })
    @RequestMapping(value = "show/userlookfile", method = RequestMethod.POST)
    public JsonResult showUserLookFile( @RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userId   =Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer current  = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        String fileStyleId = String.valueOf(jsonObject.get("fileStyleId"));
        String departmentName = String.valueOf(jsonObject.get("departmentName"));
        Integer  userGroupId =Integer.parseInt(String.valueOf(jsonObject.get("userGroupId")));
        return fileService.showUserLookFile(userId, current, pageSize, fileStyleId, departmentName,userGroupId);
    }

    /**
     * 个人全部文件显示页面
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "全部搜索显示接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "searchContent", value = "搜索关键词", required = true),
            @ApiImplicitParam(paramType = "query", dataType = " Integer", name = "current", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "departmentName", value = "部门名字"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileStyleId", value = "文件类型ID"),
    })
    @RequestMapping(value = "show/searchresult", method = RequestMethod.POST)
    public JsonResult showSearchFile(@RequestBody String object) {
        return fileService.showSearchFile(object);
    }

    /**
     * 修改文件类型
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "修改文件类型接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileIds", value = "拼接文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = " Integer", name = "fileStyleId", value = "文档类型d", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户Id", required = true),
    })
    @RequestMapping(value = "update/filestyle", method = RequestMethod.POST)
    public JsonResult updateFileStyle(@RequestBody String object) {
        return fileService.updateFileStyle(object);
    }


    /**
     * 删除文件
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "删除单个文件类型接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户Id", required = true),
    })
    @RequestMapping(value = "delete/singlefile", method = RequestMethod.POST)
    public JsonResult deleteFileStyle(@RequestBody String object) {
        return fileService.deleteFileStyle(object);
    }

    /**
     * 修改文件类型
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "根据文件类型查对应的文件数量", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "fileIds", value = "拼接文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = " Integer", name = "fileStyleId", value = "文档类型d", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户Id", required = true),
    })
    @RequestMapping(value = "search/filestyleid", method = RequestMethod.POST)
    public JsonResult searchFile(@RequestBody String object) {
        return fileService.searchFileStyleId(object);
    }



    /**
     * 修改文件类型
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "单个文件详情", notes = "传递必要参数")
    @ApiImplicitParams({
    @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
    })
    @RequestMapping(value = "singledetail", method = RequestMethod.POST)
    public JsonResult showSingleFile( @RequestBody String object) {
        return fileService.searchSingleFile(object);
    }
//     /**
//     * 查看单个文件详情
//     *
//     * @param object
//     * @return
//     */
//    @ApiOperation(value = "单个文件详情", notes = "传递必要参数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "拼接文件ID", required = true),
//
//    })
//    @RequestMapping(value = "querysimplefile", method = RequestMethod.GET)
//    public JsonResult showSingleFile( String object) {
//        return fileService.searchSingleFile(object);
//    }
    /**
     * @param file
     * @return
     */

    @RequestMapping(value = "photo/upload.htmls", method = RequestMethod.POST)
    @ApiOperation(value = "上传封面", notes = "传递必要参数")
    @ApiImplicitParams({
    })
    public JsonResult uploadImg(@RequestParam("file") MultipartFile file) {
        String resumeurl = null;
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                //重新生成文件名，避免乱码问题
                String filename = file.getOriginalFilename();
//                String fName = null;
//                if (filename.indexOf(".") >= 0) {
//                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
//               }
                //拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(filename);
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
            return new JsonResult(0, resumeurl, "上传成功", 0);
        } else {
            return new JsonResult(2, 0, "上传失败，因为文件是空的.", 0);
        }
    }

    /**
     * @param file
     * @return
     */
    //上传文件
    @RequestMapping(value = "file/upload.htmls", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "传递必要参数")
    @ApiImplicitParams({
    })
    @ResponseBody
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file) {
        String resumeurl = null;
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                //重新生成文件名，避免乱码问题
                String filename = file.getOriginalFilename();
//                String fName = null;
//                if (filename.indexOf(".") >= 0) {
//                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
//                }
//              String path = String.valueOf(new Random().nextInt(100)).concat((fName));//拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(String.valueOf(new Random().nextInt(100))).concat(filename);
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
            return new JsonResult(0, resumeurl, "上传成功", 0);
        } else {
            return new JsonResult(2, "", "上传失败，因为文件是空的.", 0);
        }
    }


}
