package com.epam.esm.repository.mybatis.util;

import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.TagFilter;
import org.apache.ibatis.jdbc.SQL;

/**
 * Util class for creation tag sqls
 */
public class MyBatisTagUtil {
    public String getTagsByParams(AbstractFilter filter) {
        TagFilter tagFilter = (TagFilter) filter;
        if (tagFilter.isPopularTagTrigger()) {
            return "select tag.id, tag.name from " +
                    "( select purchase.user_id as userId, sum(purchase.price) as orderPrice " +
                    " from purchase group by purchase.user_id order by orderPrice desc limit 1) as tagPrice" +
                    " join purchase  on purchase.user_id = userId" +
                    " join assign on purchase.certificate_id = assign.certificate_id" +
                    " join tag  on tag.id = assign.tag_id" +
                    " group by tag.id, tag.name" +
                    " order by sum(orderPrice) desc limit 1";
        }
        SQL query = new SQL();
        query.SELECT("*");
        query.FROM("tag t");
        if (tagFilter.getId() != 0) {
            query.WHERE("id = #{id}");
        }
        if (tagFilter.getName() != null) {
            query.WHERE("name = #{name}");
        }
        if (tagFilter.getCertificateId() != 0) {
            query.JOIN("join assign a on a.certificate_id = (#{certificateId}) where t.id = a.tag_id");
        }
        query.ORDER_BY("id");
        return query.toString();
    }
}
