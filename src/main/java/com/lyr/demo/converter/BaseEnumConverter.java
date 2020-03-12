package com.lyr.demo.converter;

import com.lyr.demo.utils.EnumCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: lyr
 * @Description: jpa和mybatis对枚举的转换操作
 * @Date: 2020/03/12 9:32 下午
 * @Version: 1.0
 **/
@Slf4j
public abstract class BaseEnumConverter<E extends Enum, O> extends BaseTypeHandler<E> implements AttributeConverter<E,O> {


    /**
     * jpa转换为数据库存放的值
     * @param e
     * @return
     */
    @Override
    public O convertToDatabaseColumn(E e) {
        if(e == null) {
            return null;
        }
        //查找是否存在getValue()方法，存在就返回getValue()的返回值，不存在返回枚举名称
        try {
            Method method = e.getClass().getMethod("getCode");
            if(method != null){
                return (O)method.invoke(e);
            }
        } catch (Exception e1) {
            log.error(e1.getMessage());
        }
        return (O)((Integer)e.ordinal());
    }

    /**
     * jpa转换为实体类属性
     * @param dbValue
     * @return
     */
    @Override
    public E convertToEntityAttribute(O dbValue) {
        return EnumCacheUtils.get(getClazz(), dbValue);
    }


    /**
     * 下面是对MyBatis进行转换
     */


    /**
     * mybatis不处理保存操作，故这边不处理
     * @param preparedStatement
     * @param i
     * @param e
     * @param jdbcType
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) {

    }

    /**
     * 返回的结果为null时的处理
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return EnumCacheUtils.get(getClazz(), resultSet.getObject(s));
    }

    /**
     * 返回的结果为null时的处理
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return EnumCacheUtils.get(getClazz(), resultSet.getObject(i));
    }

    /**
     * 返回的结果为null时的处理
     * @return
     * @throws SQLException
     */
    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return EnumCacheUtils.get(getClazz(), callableStatement.getObject(i));
    }

    /**
     * 获取类参数的clazz
     * @return
     * @throws SQLException
     */
    private Class<E> getClazz(){
        return (Class<E>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}