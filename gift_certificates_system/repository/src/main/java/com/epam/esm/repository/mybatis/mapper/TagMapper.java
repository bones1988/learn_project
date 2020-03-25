package com.epam.esm.repository.mybatis.mapper;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.mybatis.util.MyBatisTagUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

/**
 * mapper for mybatis mapping tags
 */
@Mapper
public interface TagMapper {
    @SelectProvider(type = MyBatisTagUtil.class, method = "getTagsByParams")
    List<Tag> query(AbstractFilter filter, RowBounds rowBounds);

    @Insert("insert into tag (name) value (#{name})")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    void save(Tag tag);

    @Delete("delete from tag where id = (#{id})")
    int delete(@Param("id") long id);

    @Select("SELECT * from assign a join tag t on id = tag_id where certificate_id = (#{id})")
    Set<Tag> getCertificateTags(long id);
}
