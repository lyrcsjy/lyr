package com.lyr.demo.service.impl;

import com.lyr.demo.domain.IdentityDomain;
import com.lyr.demo.repository.BaseRepository;
import com.lyr.demo.service.BaseService;
import com.lyr.demo.utils.ObjectUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: lyr
 * @Description: TODO
 * @Date: 2020/03/12 9:21 下午
 * @Version: 1.0
 **/
@Setter
@Getter
public abstract class BaseServiceImpl<E extends IdentityDomain, K extends Serializable> implements BaseService<E, K> {

    @Autowired
    private BaseRepository<E, K> baseRepository;


    /**
     * 根据K查找某个Entity（建议使用）
     *
     * @param id
     * @return
     */

    @Override
    public E find(K id) {
        ObjectUtils.checkNullThrow(id, "type of id should not be NULL.");
        return baseRepository.findOne(id);
    }

    /**
     * 获取所有的Entity列表
     *
     * @return
     */
    @Override
    public List<E> getAll() {
        return baseRepository.findAll();
    }

    /**
     * 获取Entity的总数
     *
     * @return
     */
    @Override
    public Long getTotalCount() {
        return baseRepository.count();
    }

    /**
     * 保存Entity
     *
     * @param entity
     * @return
     */
    @Override
    public E save(E entity) {
        ObjectUtils.checkNullThrow(entity, "persisting domain should not be NULL");
        initializeOffsetDateTime(entity);
        return baseRepository.save(entity);
    }

    /**
     * 修改Entity
     *
     * @param entity
     * @return
     */
    @Override
    public E update(E entity) {
        ObjectUtils.checkNullThrow(entity, "domain in updating should not be NULL");
        entity.setUpdateTime(LocalDateTime.now());
        return baseRepository.save(entity);
    }

    /**
     * 删除Entity
     *
     * @param entity
     */
    @Override
    public void delete(E entity) {
        baseRepository.delete(entity);
    }

    /**
     * 根据Id删除某个Entity
     *
     * @param id
     */
    @Override
    public void delete(K id) {
        ObjectUtils.checkNullThrow(id, "type of id should not be NULL");
        E e = find(id);
        ObjectUtils.checkNullThrow(e, "entry should not be NULL");
        delete(e);
    }

    /**
     * 删除Entity的集合类
     *
     * @param entities
     */
    @Override
    public void delete(Collection<E> entities) {
        baseRepository.delete(entities);
    }

    /**
     * 清空缓存，提交持久化
     */
    @Override
    public void flush() {
        baseRepository.flush();
    }


    /**
     * 获取全部信息
     */
    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }

    /**
     * 根据排序条件查询某个Entity的列表
     *
     * @param sort
     * @return
     */
    @Override
    public List<E> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    /**
     * 根据查询信息获取某个Entity的列表
     *
     * @param spec
     * @return
     */
    @Override
    public List<E> findAll(Specification<E> spec) {
        return baseRepository.findAll(spec);
    }

    /**
     * 获取Entity的分页信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<E> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    /**
     * 根据查询条件和分页信息获取某个结果的分页信息
     *
     * @param spec
     * @param pageable
     * @return
     */
    @Override
    public Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    /**
     * 根据查询条件和排序条件获取某个结果集列表
     *
     * @param spec
     * @param sort
     * @return
     */
    @Override
    public List<E> findAll(Specification<E> spec, Sort sort) {
        return baseRepository.findAll(spec);
    }

    /**
     * 查询某个条件的结果数集
     *
     * @param spec
     * @return
     */
    @Override
    public long count(Specification<E> spec) {
        return baseRepository.count(spec);
    }

    private void initializeOffsetDateTime(E entity) {
        entity.setCreateTime(LocalDateTime.now());
        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(IdentityDomain.DEFAULT_DATE_TIME);
        }
    }
}