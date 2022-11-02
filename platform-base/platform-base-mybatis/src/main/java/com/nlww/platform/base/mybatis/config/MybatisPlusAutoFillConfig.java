package com.nlww.platform.base.mybatis.config;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * mybatis-plus数据库字段自动填充配置
 *
 * @author 渔火江渚上
 * @since 2022/3/24 22:30
 */
public class MybatisPlusAutoFillConfig implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        initValue(metaObject, "createTime", LocalDateTime.now(), LocalDateTime.class);
        initValue(metaObject, "createBy", getLoginUsername(), String.class);
        initValue(metaObject, "updateTime", LocalDateTime.now(), LocalDateTime.class);
        initValue(metaObject, "updateBy", getLoginUsername(), String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        initValue(metaObject, "updateTime", LocalDateTime.now(), LocalDateTime.class);
        initValue(metaObject, "updateBy", getLoginUsername(), String.class);
    }

    private <T> void initValue(MetaObject metaObject, String fieldName, T defaultValue, Class<T> tClass) {
        // 1. 没有 get 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        if(userSetValue != null){
            T value = tClass.cast(metaObject.getValue(fieldName));
            if (value != null) {//默认值不为空，不进行自动配置
               return;
            }
            //该字段类型是String或其子类型，并且值不为空时，不进行自动配置
            if (tClass.isAssignableFrom(String.class) && StrUtil.isNotBlank((CharSequence) value)) {
               return;
            }
        }
        setFieldValByName(fieldName, defaultValue, metaObject);
    }

    private String getLoginUsername(){
        // Sa-Toekn获取当前会话账号Id，如未登录，返回参数设置的默认值
        return StpUtil.getLoginId("admin");
    }
}
