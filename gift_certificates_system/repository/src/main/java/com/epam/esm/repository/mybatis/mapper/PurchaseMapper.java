package com.epam.esm.repository.mybatis.mapper;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

/**
 * mapper for mybatis mapping purchases
 */
@Mapper
public interface PurchaseMapper {
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "price", column = "price"),
            @Result(property = "buyTime", column = "buyTime"),
            @Result(property = "giftCertificate", column = "certificate_id", javaType = GiftCertificate.class,
                    one = @One(select = "com.epam.esm.repository.mybatis.mapper.CertificateMapper.getById"))}
    )
    @Select("select * from purchase where user_id = #{id} order by certificate_id")
    List<Purchase> getUserPurchases(long id, RowBounds rowBounds);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "lastUpdateDate", column = "update_date"),
            @Result(property = "tags", column = "id", javaType = Set.class, many = @Many(select = "com.epam.esm.repository.mybatis.mapper.TagMapper.getCertificateTags"))}
    )
    @Select("select * from certificate, purchase where certificate_id = certificate.id and user_id = #{id} order by certificate.id")
    List<GiftCertificate> getUserCertificates(long id, RowBounds rowBounds);

    @Insert("insert into purchase (user_id, certificate_id, price, buyTime) values (#{userId}, #{giftCertificate.id}, #{price}, #{buyTime})")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    void savePurchase(Purchase purchase);
}
