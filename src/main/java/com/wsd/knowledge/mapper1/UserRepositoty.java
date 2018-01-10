package com.wsd.knowledge.mapper1;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;
import java.util.Map;
/**
 * @Author EdsionGeng
 * @Description 用户模块持久层
 * @Date:9:20 2017/10/31
 */

public interface UserRepositoty {
    /**
     * 登录
     *
     * @param name
     * @param password
     * @return
     */
    @Select("select id,username,UserGroupId,companyId from SystemUser  where username = #{name} and password = #{password}")
    Map findUser(@Param("name") String name, @Param("password") String password);

    /**
     * 获取个人信息
     *
     * @param id
     * @return
     */
    @Select("select username,department,UserGroupId,companyId from SystemUser where id=#{id} ")
    SystemUser findInfo(@Param("id") Integer id);

    /**
     * 获取个人信息
     *
     * @param name
     * @return
     */
    @Select("select id from Department_new where Deptno=#{name} ")
    Integer queryGroupIdByName(@Param("name") String name);

//    /**
//     * 查找同一组别下的人
//     *
//     * @param userGroupId
//     * @return
//     */
//    @Select("select id  from SystemUser where UserGroupId=#{UserGroupId} and job=1")
//    List<Integer > queryUserIdByGroup(@Param("UserGroupId")Integer userGroupId);

    /**
     * 查找同一组别下的人
     *
     * @param userGroupId
     * @return
     */
    @Select("select id,username,userGroupId  from SystemUser where UserGroupId=#{UserGroupId} and job=1")
    List<SystemUser> queryGroup(@Param("UserGroupId") Integer userGroupId);

    /**
     * 查找同一组别下的人
     *
     * @param
     * @return
     */
    @Select("select  id  as userId,userGroupId,username AS name  from SystemUser  where userGroupId=#{id} and job=1")
    List<NewDepartment> queryByGroupId(@Param("id") Integer id);

    @Select("select Uid from UserRoles where Rid=2013 ")
        //@Select("select Uid from UserRoles where Rid=3031 ")
    List<Integer> queryAdmin();

   // @Select("select id  from UserRoles where  Uid=#{userId} and Rid=3031")
    @Select("select id  from UserRoles where  Uid=#{userId} and Rid=2013")
    Integer ifAdmin(@Param("userId") Object userId);

    @Select("select id from Department_new where pid=1")
    List<Integer> queryCompanyIds();


    @Select("select  a.id,a.deptno,a.pid from Department_new a LEFT JOIN Department_new b ON b.id =a.Pid where b.pid=#{pid} OR b.id=#{pid}")
    List<Integer> showPerGroupId(@Param("pid") Integer pid);

    @Select("select id as departmentId,deptno,pid from Department_new where pid=3")
    List<Map> showSeniorDept();

    @Select("select id as departmentId,deptno,pid from Department_new where pid=#{pid}")
    List<Map> showSonDept(@Param("pid")Integer pid);

    @Select("select pid from Department_new where id=#{id}")
    Integer queryPid(@Param("id") Integer id);

    /**
     * 获取部门组织架构树形图
     *
     * @param map
     * @return
     */
    @SelectProvider(type = DepartmentTree.class, method = "showDepTree")
    List<NewDepartment> findList(Map<String, Object> map);

    class DepartmentTree {
        public String showDepTree(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select  id,deptno,no,pid,deptno AS name from Department_new  where 1=1 ");
            if (StringUtils.isNotEmpty((String) map.get("pid"))) {
                sql.append(" AND pid = #{pid} ");
            }
            return sql.toString();
        }

//        public String showUserTree(Map<String, Object> map) {
//            StringBuffer sql = new StringBuffer();
//            sql.append("select  id,userGroupId,username AS name from SystemUser  where 1=1 ");
//            if (StringUtils.isNotEmpty((String) map.get("pid"))) {
//                sql.append(" AND userGroupId = #{pid} ");
//            }
//            return sql.toString();
//       }
    }
}
