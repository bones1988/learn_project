package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.mybatis.mapper.CertificateMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * implementation of model repository interface for certificates
 */
@Repository
public class GiftCertificateRepositoryImpl implements ModelRepository<GiftCertificate> {
    private CertificateMapper certificateMapper;

    @Autowired
    public GiftCertificateRepositoryImpl(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;

    }

    /**
     * @see ModelRepository
     */
    @Override
    public List<GiftCertificate> query(AbstractFilter filter) {
        RowBounds rowBounds = new RowBounds(filter.getOffset(), filter.getLimit());
        return certificateMapper.query(filter, rowBounds);
    }

    /**
     * @see ModelRepository
     */
    @Override
    public long add(GiftCertificate giftCertificate) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        certificateMapper.save(giftCertificate);
        giftCertificate.getTags()
                .forEach(tag -> assignTagToCertificate(giftCertificate.getId(), tag.getId()));
        return giftCertificate.getId();
    }

    /**
     * @see ModelRepository
     */
    @Override
    public int remove(long id) {
        return certificateMapper.delete(id);
    }

    /**
     * @see ModelRepository
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        giftCertificate.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        certificateMapper.updateCertificate(giftCertificate);
        unAssignTags(id);
        giftCertificate.getTags()
                .forEach(tag -> assignTagToCertificate(id, tag.getId()));
        return giftCertificate;
    }

    /**
     * @see ModelRepository
     */
    @Override
    public void assignTagToCertificate(long certificateId, long tagId) {
        certificateMapper.assignTag(certificateId, tagId);
    }

    /**
     * @see ModelRepository
     */
    @Override
    public void unAssignTags(long certificateId) {
        certificateMapper.unassignTags(certificateId);
    }

    public long countCertificateByParams(AbstractFilter abstractFilter) {
        return certificateMapper.count(abstractFilter);
    }
}
