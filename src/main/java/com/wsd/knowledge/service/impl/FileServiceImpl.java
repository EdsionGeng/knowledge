package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.*;
import com.wsd.knowledge.mapper.FileKindMapper;
import com.wsd.knowledge.mapper.FileMapper;
import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.mapper.UserPermissionMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author EdsionGeng
 * @Description 文件操作业务逻辑层实现类
 * @Date:11:31 2017/11/2
 */
@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserRepositoty userRepositoty;
    @Autowired
    private FileKindMapper fileKindMapper;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

//
//    private static final Base64 BASE64 = new Base64();
//    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 组合查询和全部查询
     *
     * @param fileStyleId
     * @param title
     * @param startDate
     * @param endDate
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showAllFile(String fileStyleId, String title, String startDate, String endDate, String sortType, String companyId, Integer current, Integer pageSize) {

        if (fileStyleId.equals("")) {
            fileStyleId = "";
        }
        if (title.equals("")) {
            title = "";
        }
        if (startDate.equals("")) {
            startDate = "2017-11-01 13:30";
        }
        if (endDate.equals("")) {
            endDate = "";
        }
        if (fileStyleId.equals("0")) {
            fileStyleId = "";
        }
        if (!sortType.equals("desc") && !sortType.equals("asc")) {
            sortType = "desc";
        }
        if (current == null || pageSize == null) {
            current = 1;
            pageSize = 20;
        }
        int startSize = (current - 1) * pageSize;
        if (companyId.equals("1")) {
            companyId = getCompanyIds();
        }
        if (fileStyleId.equals("") && title.equals("") && startDate.equals("2017-11-01 13:30") && endDate.equals("")) {
            Map<String, Object> maps = new HashMap<>();
            maps.put("sortType", sortType);
            maps.put("companyId", companyId);
            maps.put("startSize", startSize);
            maps.put("limit", pageSize);
            List<Map> map = fileMapper.showAllFile(maps);
            Integer sum = fileMapper.countFile(companyId);
            if (sum == null) {
                sum = 0;
            }
            RdPage page = new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, map, "查询结果", page);
        } else {
            Map<String, Object> map = new HashMap<>();
            if (!endDate.equals("")) {
                endDate = DateUtil.getAfterDate(endDate, 1);
            }
            map.put("fileStyleId", fileStyleId);
            map.put("title", title);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("sortType", sortType);
            map.put("companyId", companyId);
            map.put("startSize", startSize);
            map.put("limit", pageSize);
            List<Map> maps = fileMapper.queryFileByIf(map);
            Integer sum = fileMapper.countFilePcs(map);
            if (sum == null) {
                sum = 0;
            }
            RdPage page = new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, maps, "查询结果", page);
        }
    }

    /**
     * 添加文件
     *
     * @param title
     * @param content
     * @param photourl
     * @param fileurl
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public synchronized JsonResult insertFile(String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize, String describe, Integer fileSpecies, String companyId) {
        String fileNo = String.valueOf(new Date().getTime()).concat("888888");
        SystemUser systemUser = userRepositoty.findInfo(userId);
        FileKind fileKind = fileKindMapper.selectFileKind(fileStyleId);
        //生成实体类
        FileDetail fileDetail = new FileDetail(systemUser.getDepartment(), systemUser.getUsername(), userId, fileStyleId, fileNo, title
                , fileKind.getFileKindName(), content, fileurl, photourl, 0, 0, 0, filesize, 1,
                describe, new DateUtil().getTime(), fileSpecies, systemUser.getUserGroupId(), companyId);
        String re = new DateUtil().cacheExist(fileNo);
        if (re.equals("full")) {
            return new JsonResult(2, 0, "并发问题", 0);
        }
        if (fileMapper.insertFileDetail(fileDetail) != null) {
            //添加文件成功 ，获得此文件ID返回前台，执行权限添加操作
            Integer fileId = fileMapper.selectIdByIf(fileNo);

            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, fileId, 1, new DateUtil().getTime(), systemUser.getCompanyId(), 0);
            operationMapper.insertOperationLog(operationLog);
            return new JsonResult(0, fileId, "添加成功", 0);
        }
        return new JsonResult(0, 0, "添加失败", 0);
    }

    /**
     * 批量删除文件
     *
     * @param ids
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteFile(String ids, Integer userId) {
        if (ids == "null" || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        Integer j = null;
        for (String id : ids.split(",")) {
            j = fileMapper.updateFileShow(id);
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, Integer.parseInt(id), 2, new DateUtil().getTime(), systemUser.getCompanyId(), 0);
            String re = new DateUtil().cacheExist(id);
            if (re.equals("full")) {
                return new JsonResult(2, 0, "网络异常", 0);
            }
            operationMapper.insertOperationLog(operationLog);//添加操作日志
            userPermissionMapper.deletePerByFileId(Integer.parseInt(id));
        }
//        for (int i = 0; i < lengths; i++) {
//            j = fileMapper.updateFileShow(id[i]);//更改文件属性
//            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id[i], 2, new DateUtil().getSystemTime());
//            operationMapper.insertOperationLog(operationLog);//添加操作日志
//        }
        if (j != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 查看文件
     *
     * @param fileId
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult readFile(Integer fileId, Integer userId) {
        if (fileId == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        if (fileMapper.lookPcs(fileId) != 0) {
            //添加操作日志
            if (operationMapper.queryLookLog(userId, fileId) == null) {
                SystemUser systemUser = userRepositoty.findInfo(userId);
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, fileId, 4, new DateUtil().getTime(), systemUser.getCompanyId(), 0);
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                operationMapper.updateFileNewStatus(userId, fileId);
                return new JsonResult(0, 0, "已查阅过日志", 0);
            }
        }
        if (j != null) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 下载文件执行相应的业务逻辑
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult downloadFile(Integer id, Integer userId) {
        if (id == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        if (fileMapper.updateDownPcs(id) != 0) {
            if (operationMapper.queryDownLog(userId, id) == null) {
                SystemUser systemUser = userRepositoty.findInfo(userId);
                //添加操作日志
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 5, new DateUtil().getSystemTime(), systemUser.getCompanyId(), 0);
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                return new JsonResult(0, 0, "已下载过日志", 0);
            }
        }
        if (j != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 修改文件
     *
     * @param id
     * @param content
     * @param fileurl
     * @param fileStyleId
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult updateFileDetail(Integer id, String content, String fileurl, Integer fileStyleId, Integer userId, String chooseUser, String fileSize, String photourl, String describle, String fileStyleName, Integer fileSpecies) {
        if (id == null || content.equals("") || fileStyleId == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer result = fileMapper.updateFileContentUrl(id, content, fileurl, fileStyleId, fileSize, photourl, describle, fileStyleName, fileSpecies, new DateUtil().getTime());
        if (result != 0) {
            //修改操作日志文件状态

            SystemUser systemUser = userRepositoty.findInfo(userId);
            //添加操作日志
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 3, new DateUtil().getTime(), systemUser.getCompanyId(), 1);
            String str = new DateUtil().cacheExist(String.valueOf(id));
            if (str.equals("full")) {
                return new JsonResult(2, 0, "并发", 0);
            }
            Integer k = operationMapper.insertOperationLog(operationLog);
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(id));
            operationMapper.updateFileStatus(map);
            if (chooseUser.equals("1")) {
                userPermissionMapper.deletePerByFileId(id);
            }
            if (k != 0) {
                return new JsonResult(0, 0, "操作成功", 0);
            }
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    public JsonResult showUserLookFile(Integer userId, Integer current, Integer pageSize, String fileStyleId, String departmentId, Integer userGroupId, String sortType, String companyId, String sortmethod) {
        if (current == null || pageSize == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        if (!sortType.equals("asc") && !sortType.equals("desc")) {
            sortType = "desc";
        }

        if (departmentId == "null") {
            departmentId = "";
        }
        if (fileStyleId == "null") {
            fileStyleId = "";
        }
        if (companyId.equals("1")) {
            companyId = getCompanyIds();
        }
        RdPage page = new RdPage();
        int startSize = (current - 1) * pageSize;
        List<Map> map = new ArrayList<>();
        String userGroupIds = getGroupArray(String.valueOf(userGroupId));
        if (departmentId.equals("") && fileStyleId.equals("")) {
            Integer sum = 0;
            if ("0".equals(sortmethod)) {
                map = fileMapper.showUserLookFile(userId, companyId, userGroupIds, sortType, startSize, pageSize);
            } else if ("1".equals(sortmethod)) {
                map = fileMapper.showUserLookFile1(userId, companyId, userGroupIds, sortType, startSize, pageSize);
            }
            sum = fileMapper.countUserLookFile(userId, companyId, userGroupIds);
//            List<Map> newList = new ArrayList(new HashSet(map));
//            System.out.println(newList);
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, map, "查询结果", page);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            if (fileStyleId.equals("0")) {
                fileStyleId = "";
            }
            if (!"".equals(fileStyleId)) {
                String getStyleList = getFileStyleIdList(fileStyleId);
                params.put("fileStyleId", getStyleList);
            } else {
                params.put("fileStyleId", fileStyleId);
            }
            params.put("companyId", companyId);
            if (!departmentId.equals("")) {
                //   Integer groupId = userRepositoty.queryGroupIdByName(departmentName);
                String results = getGroupArray(departmentId);
                params.put("groupId", results);
                params.put("useGro", "1");
            } else {
                params.put("useGro", "");
                params.put("groupId", userGroupIds);
            }
            Integer result = 0;
            List<Map> searchmap = fileMapper.showUserIfLookFile(params);
            result += searchmap.size();
            if (searchmap != null) {
                List<Map> mapa = fileMapper.showUserLookCompanyFile(params);
                searchmap.addAll(mapa);
                result += mapa.size();
                List<Map> groupFileList = fileMapper.showUserLookGroupFile(params);
                searchmap.addAll(groupFileList);
                result += groupFileList.size();
                List<Map> upFileList = fileMapper.showUserUpFile1(params);
                searchmap.addAll(upFileList);
                result += upFileList.size();
            }
            List<Map> newList = new ArrayList(new HashSet(searchmap));
            List<Map> listResult = listSplit3(current, pageSize, newList);
            page.setTotal(result);
            page.setPages(result % pageSize == 0 ? result / pageSize : result / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, listResult, "查询结果", page);
        }
    }

    /**
     * 展示搜寻结果
     *
     * @param object
     * @return
     */
    @Override
    public JsonResult showSearchFile(String object) {

        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        String userId = String.valueOf(jsonObject.get("userId"));
        if (userId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        Integer current = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        String searchContent = String.valueOf(jsonObject.get("searchContent"));
        String userGroupId = String.valueOf(jsonObject.get("userGroupId"));
        String companyId = String.valueOf(jsonObject.get("companyId"));
        if (companyId.equals("1")) {
            companyId = getCompanyIds();
        }
        if (userId == null || current == null || pageSize == null || searchContent.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        RdPage rdPage = new RdPage();
        List<Map> map = fileMapper.showSearchFile1(Integer.parseInt(userId), searchContent);
        List<Map> companyFileList = fileMapper.searchCompanyFile1(searchContent, companyId);
        String groupIds = getGroupArray(userGroupId);
        List<Map> groupFileList = fileMapper.searchGroupFile1(searchContent, groupIds);
        if (map != null) {
            map.addAll(companyFileList);
            map.addAll(groupFileList);
            List<Map> upFileList = fileMapper.searchUpFile(searchContent, userId);
            map.addAll(upFileList);
            List<Map> newList = new ArrayList(new HashSet(map));
            int sum = newList.size();
            List<Map> pageMap = listSplit3(current, pageSize, newList);
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
            return new JsonResult(0, pageMap, "查询结果", rdPage);
        }
        return new JsonResult(2, 0, "查无结果", 0);
    }

    /**
     * 批量更新文件类型
     *
     * @param object
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult updateFileStyle(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        String userId = String.valueOf(jsonObject.get("userId"));
        if (userId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        String fileIds = String.valueOf(jsonObject.get("fileIds"));
        if (userId == null || fileStyleId == null || fileIds.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(Integer.parseInt(userId));
        FileKind fileKind = fileKindMapper.selectFileKind(fileStyleId);
        Integer result = null;
        for (String id : fileIds.split(",")) {
//            String str = new DateUtil().cacheExist(id);
//            if (str.equals("full")) {
//                return new JsonResult(2, 0, "并发情况", 0);
//            }
            result = fileMapper.updateFileStyle(Integer.parseInt(id), fileStyleId, fileKind.getFileKindName());
            String str = new DateUtil().cacheExist(id);
            if (str.equals("full")) {
                return new JsonResult(2, 0, "出现并发", 0);
            }
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), Integer.parseInt(userId), Integer.parseInt(id), 3, new DateUtil().getTime(), systemUser.getCompanyId(), 0);
            operationMapper.insertOperationLog(operationLog);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 文件类型对应文件数量
     *
     * @param object
     * @return
     */
    @Override
    public JsonResult searchFileStyleId(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        Integer sum = fileMapper.countStylePcs(fileStyleId);
        if (sum == null) {
            sum = 0;
        }
        return new JsonResult(0, sum, "查询结果", 0);
    }

    /**
     * 删除单个文件
     *
     * @param object
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteFileStyle(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        String fileId = String.valueOf(jsonObject.get("fileId"));
        String userId = String.valueOf(jsonObject.get("userId"));
        if (userId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        Integer result = fileMapper.updateFileShow(fileId);
        if (result != 0) {
            SystemUser systemUser = userRepositoty.findInfo(Integer.parseInt(userId));
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), Integer.parseInt(userId), Integer.parseInt(fileId), 2, new DateUtil().getTime(), systemUser.getCompanyId(), 0);
            String str = new DateUtil().cacheExist(String.valueOf(userId));
            if (str.equals("full")) {
                return new JsonResult(2, 0, "出现并发", 0);
            }
            operationMapper.insertOperationLog(operationLog);//添加操作日志
            userPermissionMapper.deletePerByFileId(Integer.parseInt(fileId)
            );
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作异常", 0);
    }

    /**
     * 查找单个文件
     *
     * @param object
     * @return
     */
    @Override
    public JsonResult searchSingleFile(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Map map = fileMapper.showSingleFile(fileId);
        try {
            map.put("fileContent", new String((byte[]) map.get("fileContent"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (map != null) {
            return new JsonResult(0, map, "查询结果", 0);
        }
        return new JsonResult(2, 0, "查询失败", 0);
    }
//

    /**
     * 用户历史上传业务逻辑层处理
     *
     * @param userId
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showUserUp(Integer userId, String sortType, Integer current, Integer pageSize, String departmentId, String fileStyleId) {
        if (current == null || pageSize == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (current - 1) * pageSize;
        if (!sortType.equals("asc") && !sortType.equals("desc")) {
            sortType = "desc";
        }
        if (!"".equals(departmentId) && !"null".equals(departmentId)) {
            departmentId = getGroupArray(departmentId);
        } else {
            departmentId = "";
        }
        if (!"".equals(fileStyleId) && !"null".equals(fileStyleId)) {
            fileStyleId = getFileStyleIdList(fileStyleId);
        } else {
            fileStyleId = "";
        }
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", userId);
        mapParams.put("sortType", sortType);
        mapParams.put("startSize", startSize);
        mapParams.put("pageSize", pageSize);
        mapParams.put("departmentId", departmentId);
        mapParams.put("fileStyleId", fileStyleId);
        List<Map> map = fileMapper.showUserUpFile(mapParams);
        Integer sum = fileMapper.countUpFilePcs(mapParams);

        if (sum != null) {
            RdPage page = new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, map, "查询结果", page);
        }
        return new JsonResult(0, 0, "无数据", 0);

    }


    //集合分页
    public static List<Map> listSplit3(int page, int limit, List<Map> list) {
        List<Map> result = new ArrayList<Map>();
        if (list != null && list.size() > 0) {
            int allCount = list.size();
            int pageCount = (allCount + limit - 1) / limit;
            if (page >= pageCount) {
                page = pageCount;
            }
            int start = (page - 1) * limit;
            int end = page * limit;
            if (end >= allCount) {
                end = allCount;
            }
            for (int i = start; i < end; i++) {
                result.add(list.get(i));
            }
        }
        return (result != null && result.size() > 0) ? result : new ArrayList<>();
    }


    String getCompanyIds() {
        List<Integer> companyList = userRepositoty.queryCompanyIds();
        String ss = "";
        if (companyList.size() != 0) {
            for (int i = 0, len = companyList.size(); i < len; i++) {
                if (i > 0) {
                    ss += ",";
                }
                ss += "'" + companyList.get(i) + "'";
            }

        }
        return ss;
    }


    String getGroupArray(String groupId) {
        List<Integer> groupList = userRepositoty.showPerGroupId(Integer.parseInt(groupId));
        String ss = "";
        if (groupList.size() != 0) {
            for (int i = 0, len = groupList.size(); i < len; i++) {
                if (i > 0) {
                    ss += ",";
                }
                ss += "'" + groupList.get(i) + "'";
            }
            ss = ss + "," + "'" + groupId + "'";
        } else {
            Integer result = userRepositoty.queryPid(Integer.parseInt(groupId));
            ss = "'" + result + "'" + "," + "'" + groupId + "'";
        }
        return ss;
    }

    String getFileStyleIdList(String fileStyleId) {
        List<Integer> styleList = fileKindMapper.queryAllSonId(Integer.parseInt(fileStyleId));
        String ss = "";
        if (styleList.size() != 0) {
            for (int i = 0, len = styleList.size(); i < len; i++) {
                if (i > 0) {
                    ss += ",";
                }
                ss += "'" + styleList.get(i) + "'";
            }
            ss = ss + "," + "'" + fileStyleId + "'";
        } else {

            ss = "'" + fileStyleId + "'";
        }
        return ss;

    }
//    public static String encode(String source) {
//        if (!isEmpty(source)) {new Base64();
//
//            byte[] bytes = BASE64.encode(source.getBytes(DEFAULT_CHARSET));
//            return new String(bytes, DEFAULT_CHARSET);
//        }
//        return source;
//    }
//
//
//    private static boolean isEmpty(String str) {
//        return str == null || str.length() == 0;
//    }
}
