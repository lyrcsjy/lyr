package com.lyr.demo.service;

import com.lyr.demo.domain.IdentityDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: lyr
 * @Description: service基础类
 * @Date: 2020/03/12 9:08 下午
 * @Version: 1.0
 **/
public interface BaseService<E extends IdentityDomain, K extends Serializable> {

    /**
     * 根据K查找某个Entity（建议使用）
     *
     * @param id
     * @return
     */
    E find(K id);

    /**
     * 获取所有的Entity列表
     *
     * @return
     */
    List<E> getAll();

    /**
     * 获取Entity的总数
     *
     * @return
     */
    Long getTotalCount();

    /**
     * 保存Entity
     *
     * @param entity
     * @return
     */
    E save(E entity);

    /**
     * 修改Entity
     *
     * @param entity
     * @return
     */
    E update(E entity);

    /**
     * 删除Entity
     *
     * @param entity
     */
    void delete(E entity);

    /**
     * 根据Id删除某个Entity
     *
     * @param id
     */
    void delete(K id);

    /**
     * 删除Entity的集合类
     *
     * @param entities
     */
    void delete(Collection<E> entities);

    /**
     * 清空缓存，提交持久化
     */
    void flush();

    /**
     * 获取全部信息
     */
    List<E> findAll();

    /**
     * 根据查询信息获取某个Entity的列表
     *
     * @param spec
     * @return
     */
    List<E> findAll(Specification<E> spec);

    /**
     * 根据排序条件获取某个结果集列表
     *
     * @param sort
     * @return
     */
    List<E> findAll(Sort sort);

    /**
     * 获取Entity的分页信息
     *
     * @param pageable
     * @return
     */
    Page<E> findAll(Pageable pageable);

    /**
     * 根据查询条件和分页信息获取某个结果的分页信息
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<E> findAll(Specification<E> spec, Pageable pageable);

    /**
     * 根据查询条件和排序条件获取某个结果集列表
     *
     * @param spec
     * @param sort
     * @return
     */
    List<E> findAll(Specification<E> spec, Sort sort);

    /**
     * 查询某个条件的结果数集
     *
     * @param spec
     * @return
     */
    long count(Specification<E> spec);

}