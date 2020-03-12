package com.lyr.demo.repository.impl;

import com.lyr.demo.repository.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @Author: lyr
 * @Description: JPA公共服务实现
 * @Date: 2020/03/12 9:41 下午
 * @Version: 1.0
 **/
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation entityInformation;
    private final Class<T> clazz;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
        this.clazz = entityInformation.getJavaType();
    }

}