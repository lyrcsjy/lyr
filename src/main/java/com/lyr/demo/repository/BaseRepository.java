package com.lyr.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Author: lyr
 * @Description: JPA公共服务
 * @Date: 2020/03/12 9:22 下午
 * @Version: 1.0
 **/
@NoRepositoryBean
public interface BaseRepository<E,ID extends Serializable>  extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {

}