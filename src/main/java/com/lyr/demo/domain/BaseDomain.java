package com.lyr.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @Author: lyr
 * @Description: 基础domain方法重写
 * @Date: 2020/03/12 7:53 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public abstract class BaseDomain extends IdentityDomain {

    private static final long serialVersionUID = 6404939125344686735L;

    /**
     * 创建时间
     *
     * @return
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Override
    public LocalDateTime getCreateTime() {
        return super.getCreateTime();
    }


    /**
     * 修改时间
     *
     * @return
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Override
    public LocalDateTime getUpdateTime() {
        return super.getUpdateTime();
    }

    /**
     * 创建人
     *
     * @return
     */
    @Override
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    /**
     * 修改人
     *
     * @return
     */
    @Override
    public String getUpdatedBy() {
        return super.getUpdatedBy();
    }
}
