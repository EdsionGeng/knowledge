package com.wsd.knowledge.mapper1;

import com.wsd.knowledge.entity.Menu;
import com.wsd.knowledge.util.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;
/**
*@Author EdsionGeng
*@Description  权限持久层
*@Date:10:04 2017/11/8
*/
public interface MenuMapper {

    @SelectProvider(type = MenuProvider.class, method = "getMenus")
    List<Menu> getMenus(Map param);

    @SelectProvider(type = MenuProvider.class, method = "getRoleIds")
    List<String> getRoleIds(Map param);


    class MenuProvider {
        public String getRoleIds(Map param) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT a.Rid FROM UserRoles a");
            sql.append(" LEFT JOIN Role b ON b.id=a.Rid");
            sql.append(" WHERE b.SystemId=#{systemId} AND a.Uid=#{id}");
            return sql.toString();
        }

        public String getMenus(Map param) {
            List<String> rids = null;
            if (param != null) {
                rids = (List<String>) param.get("rids");
            }
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT a.id,a.name,a.url as menuurl,a.parentId,a.systemId,a.isButton  FROM Menu a");
            sql.append(" LEFT JOIN RoleMenus b ON b.Mid=a.id");
            sql.append(" WHERE a.systemId=#{systemId} AND a.delFlag='0'");
            if (StringUtils.isNotEmpty((String) param.get("pid"))) {
                sql.append(" AND a.parentId = #{pid}");
            }
            if (rids != null && rids.size() > 0) {
                sql.append(" AND b.Rid IN (");
                for (int i = 0; i < rids.size(); i++) {
                    if (i > 0) {
                        sql.append(",");
                    }
                    sql.append(" #{rids[" + i + "]}");
                }
                sql.append(" ) ");
            }
            sql.append(" GROUP BY a.id,a.name,a.url,a.parentId,a.systemId,a.isButton,a.scriptid ORDER BY a.id");
            return sql.toString();
        }
    }
}
