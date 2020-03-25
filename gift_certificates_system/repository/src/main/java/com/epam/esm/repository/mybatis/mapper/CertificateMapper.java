package com.epam.esm.repository.mybatis.mapper;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.mybatis.util.MyBatisCertificateUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

/**
 * mapper for mybatis mapping certificates
 */
@Mapper
public interface CertificateMapper {
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "lastUpdateDate", column = "update_date"),
            @Result(property = "tags", column = "id", javaType = Set.class, many = @Many(select = "com.epam.esm.repository.mybatis.mapper.TagMapper.getCertificateTags"))}
    )
    @SelectProvider(type = MyBatisCertificateUtil.class, method = "getCertificatesByParams")
    List<GiftCertificate> query(AbstractFilter filter, RowBounds rowBounds);

    @Update("update certificate set active = false where id = #{id}")
    int delete(long id);

    @Insert("insert into certificate(name, description, price, create_date, update_date, duration, active) values (#{name}, #{description}, #{price}, #{createDate},  #{lastUpdateDate},  #{duration}, #{active})")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    void save(GiftCertificate certificate);

    @Insert("insert into assign (tag_id, certificate_id) values (#{tagId},#{certificateId})")
    void assignTag(@Param("certificateId") long certificateId, @Param("tagId") long tagId);

    @Delete("delete from assign where certificate_id = #{id}")
    void unassignTags(long id);

    @UpdateProvider(type = MyBatisCertificateUtil.class, method = "updateCertificate")
    void updateCertificate(GiftCertificate giftCertificate);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "lastUpdateDate", column = "update_date"),
            @Result(property = "tags", column = "id", javaType = Set.class, many = @Many(select = "com.epam.esm.repository.mybatis.mapper.TagMapper.getCertificateTags"))}
    )
    @Select("select * from certificate where id = (#{id})")
    GiftCertificate getById(long id);

    @SelectProvider(type = MyBatisCertificateUtil.class, method = "countCertificatesByParams")
    long count(AbstractFilter filter);
}
