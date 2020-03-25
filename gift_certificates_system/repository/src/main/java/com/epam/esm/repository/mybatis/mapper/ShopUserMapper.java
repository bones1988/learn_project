package com.epam.esm.repository.mybatis.mapper;

import com.epam.esm.model.ShopUser;
import com.epam.esm.repository.mybatis.util.MyBatisShopUserUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * mapper for mybatis mapping users
 */
@Mapper
public interface ShopUserMapper {
    @Select("select * from shopUser where login = #{login}")
    ShopUser findUserByLogin(String login);

    @Select("select * from shopUser order by id")
    List<ShopUser> getAll(RowBounds rowBounds);

    @Update("update shopUser set active = false where user id = #{id}")
    void deleteUser(@Param("id") long id);

    @UpdateProvider(type = MyBatisShopUserUtil.class, method = "updateUser")
    void update(ShopUser shopUser);

    @Insert("insert into shopUser (login, password, name, role) values (#{login}, #{password}, #{name}, #{role})")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    void save(ShopUser shopUser);

    @Select("select count(*) from shopUser where login = #{login}")
    int checkIfLoginExist(String login);
}
