package com.epam.esm.repository.mybatis.util;

import com.epam.esm.model.ShopUser;
import org.apache.ibatis.jdbc.SQL;

/**
 * Util class for creation shopuser sqls
 */
public class MyBatisShopUserUtil {
    public String updateUser(ShopUser shopUser) {
        SQL query = new SQL();
        query.UPDATE("usr");
        if (shopUser.getName() != null) {
            query.SET("name = #{name}");
        }
        if (shopUser.getLogin() != null) {
            query.SET("login = #{login}");
        }
        if (shopUser.getRole() != null) {
            query.SET("role = #{role}");
        }
        return query.toString();
    }
}
