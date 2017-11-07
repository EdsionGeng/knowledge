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
    @Select("select id,username,password from SystemUser  where username = #{name} and password = #{password}")
    SystemUser findUser(@Param("name") String name, @Param("password") String password);

    /**
     * 获取个人信息
     *
     * @param id
     * @return
     */
    @Select("select username,department from SystemUser where id=#{id} ")
    SystemUser findInfo(@Param("id") int id);

    /**
     * 获取部门组织架构树形图
     * @param map
     * @return
     */
//  @Select("SELECT id,deptno,no,pid,deptno AS name FROM Department_new WHERE 1 = 1 ")
    @SelectProvider(type = DepartmentTree.class, method = "showDepTree")
    List<NewDepartment> findList(Map<String,Object>map);

    class DepartmentTree {
        public String showDepTree(Map<String, Object> map) {
            StringBuffer sql = new StringBuffer();
            sql.append("select  id,deptno,no,pid,deptno AS name from Department_new  where 1=1 ");
            if (StringUtils.isNotEmpty((String) map.get("pid"))) {
                sql.append(" AND pid = #{pid} ");
            }
            return sql.toString();
        }

    }

}
