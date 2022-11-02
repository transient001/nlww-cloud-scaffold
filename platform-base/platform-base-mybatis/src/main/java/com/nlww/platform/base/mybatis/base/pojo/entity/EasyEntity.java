package com.nlww.platform.base.mybatis.base.pojo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Mybatis-Plus AR模式，继承Model，并且有对应Mapper继承BaseMapper。
 * 实体类对应数据库的一张表，可以直接通过操作实体类CRUD。
 * 构建Wrapper时，数据库字段部分不用手动填写，实体类名::get属性名就可以。
 *
 * @author 渔火江渚上
 * @since 2022/5/23 11:26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
public class EasyEntity<T extends Model<?>> extends Model<T> {

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;
}
