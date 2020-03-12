package com.lyr.demo.domain;

import com.lyr.demo.converter.JpaEnumType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: lyr
 * @Description: 定义基础domain
 * @Date: 2020/03/12 7:55 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@TypeDefs(value = {@TypeDef(name = "JpaEnumType", typeClass = JpaEnumType.class)})
public abstract class IdentityDomain implements Serializable {

    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();

    private static final long serialVersionUID = -8919085701102122581L;


    /**
     * ID，32位的UUID
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(columnDefinition = "varchar(32)")
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Size(max = 60)
    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    /**
     * 创建人
     */
    @CreatedBy
    @Size(max = 60)
    @Column(name = "created_by", length = 50)
    private String createdBy;

}

