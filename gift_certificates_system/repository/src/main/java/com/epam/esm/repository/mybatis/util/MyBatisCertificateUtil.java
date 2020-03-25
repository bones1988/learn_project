package com.epam.esm.repository.mybatis.util;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import org.apache.ibatis.jdbc.SQL;

/**
 * Util class for creation certificate sqls
 */
public class MyBatisCertificateUtil {
    private static final String CERTIFICATE_TABLE = "certificate";

    public String getCertificatesByParams(AbstractFilter filter) {
        GiftCertificateFilter giftCertificateFilter = (GiftCertificateFilter) filter;
        SQL query = new SQL();
        if (giftCertificateFilter.getTagNames() != null && !giftCertificateFilter.getTagNames().isEmpty()) {
            query.SELECT("*, count(certificate.id) as count");
            query.FROM(CERTIFICATE_TABLE);
            query.JOIN("assign on assign.certificate_id = certificate.id");
            query.JOIN("tag on tag_id = tag.id");
            StringBuilder where = new StringBuilder();
            for (int i = 0; i < giftCertificateFilter.getTagNames().size(); i++) {
                where.append("tag.name = '").append(giftCertificateFilter.getTagNames().get(i)).append("'");
                if (i < giftCertificateFilter.getTagNames().size() - 1) {
                    where.append(" or ");
                }
            }
            query.WHERE(where.toString());
            query.GROUP_BY("certificate.id");
            query.HAVING("count>=" + giftCertificateFilter.getTagNames().size());
        } else {
            query.SELECT("*");
            query.FROM(CERTIFICATE_TABLE);
        }
        query.WHERE("active = true");
        if (giftCertificateFilter.getId() != 0) {
            query.WHERE("id = (#{id})");
        }
        if (giftCertificateFilter.getName() != null) {
            query.WHERE("name like #{name} or description like #{name}");
        }
        if (giftCertificateFilter.getDescription() != null) {
            query.WHERE("description like #{description}");
        }
        if (giftCertificateFilter.getNameParts() != null && !giftCertificateFilter.getNameParts().isEmpty()) {
            giftCertificateFilter.getNameParts()
                    .forEach(s -> query.WHERE("(name like " + s + " or description like " + s + ")"));
        }
        if (giftCertificateFilter.getDescParts() != null && !giftCertificateFilter.getDescParts().isEmpty()) {
            giftCertificateFilter.getDescParts()
                    .forEach(s -> query.WHERE("description like " + s));
        }
        if (giftCertificateFilter.getSortParams() != null && !giftCertificateFilter.getSortParams().isEmpty()) {
            giftCertificateFilter.getSortParams()
                    .forEach(query::ORDER_BY);
        } else {
            query.ORDER_BY("create_date");
        }
        return query.toString();
    }

    public String updateCertificate(GiftCertificate giftCertificate) {
        SQL query = new SQL();
        query.UPDATE(CERTIFICATE_TABLE);
        if (giftCertificate.getName() != null) {
            query.SET("name = #{name}");
        }
        if (giftCertificate.getDescription() != null) {
            query.SET("description = #{description}");
        }
        if (giftCertificate.getPrice() != null) {
            query.SET("price = #{price}");
        }
        if (giftCertificate.getDuration() != 0) {
            query.SET("duration = #{duration}");
        }
        query.SET("active =  #{active}");
        query.SET("update_date = #{lastUpdateDate}");
        query.WHERE("id=" + giftCertificate.getId());
        return query.toString();
    }

    public String countCertificatesByParams(AbstractFilter filter) {
        GiftCertificateFilter giftCertificateFilter = (GiftCertificateFilter) filter;
        SQL query = new SQL();
        if (giftCertificateFilter.getTagNames() != null && !giftCertificateFilter.getTagNames().isEmpty()) {
            query.SELECT("count(*) from (select certificate.id, count(certificate.id) as count");
            query.FROM(CERTIFICATE_TABLE);
            query.JOIN("assign on assign.certificate_id = certificate.id");
            query.JOIN("tag on tag_id = tag.id");
            StringBuilder where = new StringBuilder();
            for (int i = 0; i < giftCertificateFilter.getTagNames().size(); i++) {
                where.append("tag.name = '").append(giftCertificateFilter.getTagNames().get(i)).append("'");
                if (i < giftCertificateFilter.getTagNames().size() - 1) {
                    where.append(" or ");
                }
            }
            query.WHERE(where.toString());
            query.GROUP_BY("certificate.id");
            query.HAVING("count>=" + giftCertificateFilter.getTagNames().size());
        } else {
            query.SELECT("count(*)");
            query.FROM(CERTIFICATE_TABLE);
        }
        query.WHERE("active = true");
        if (giftCertificateFilter.getId() != 0) {
            query.WHERE("id = (#{id})");
        }
        if (giftCertificateFilter.getName() != null) {
            query.WHERE("name like #{name} or description like #{name}");
        }
        if (giftCertificateFilter.getDescription() != null) {
            query.WHERE("description like #{description}");
        }
        if (giftCertificateFilter.getNameParts() != null && !giftCertificateFilter.getNameParts().isEmpty()) {
            giftCertificateFilter.getNameParts()
                    .forEach(s -> query.WHERE("(name like " + s + " or description like " + s + ")"));
        }
        if (giftCertificateFilter.getDescParts() != null && !giftCertificateFilter.getDescParts().isEmpty()) {
            giftCertificateFilter.getDescParts()
                    .forEach(s -> query.WHERE("description like " + s));
        }
        if (giftCertificateFilter.getSortParams() != null && !giftCertificateFilter.getSortParams().isEmpty()) {
            giftCertificateFilter.getSortParams()
                    .forEach(query::ORDER_BY);
        }
        if (giftCertificateFilter.getTagNames() != null && !giftCertificateFilter.getTagNames().isEmpty()) {
            query.ORDER_BY("certificate.id) as count");
        } else {
            query.ORDER_BY("create_date");
        }
        return query.toString();
    }
}
