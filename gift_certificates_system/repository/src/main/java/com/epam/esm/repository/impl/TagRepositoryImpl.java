package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.mybatis.mapper.TagMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * implementation of model repository interface for tags
 */
@Repository
public class TagRepositoryImpl implements ModelRepository<Tag> {
    private TagMapper tagMapper;


    @Autowired
    public TagRepositoryImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * @see ModelRepository
     */
    @Override
    public List<Tag> query(AbstractFilter tagFilter) {
        RowBounds rowBounds = new RowBounds(tagFilter.getOffset(), tagFilter.getLimit());
        return tagMapper.query(tagFilter, rowBounds);
    }

    /**
     * @see ModelRepository
     */
    @Override
    public long add(Tag tag) {
        tagMapper.save(tag);
        return tag.getId();
    }

    /**
     * @see ModelRepository
     */
    @Override
    public int remove(long id) {
        return tagMapper.delete(id);
    }

    /**
     * @see ModelRepository
     */
    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Forbidden method update tag");
    }

    /**
     * @see ModelRepository
     */
    @Override
    public void assignTagToCertificate(long certificateId, long tagId) {
        throw new UnsupportedOperationException("Error call assign tag to tag repository");
    }

    @Override
    public void unAssignTags(long certificateId) {
        throw new UnsupportedOperationException("Error call unAssign tag to tag repository");
    }
}
