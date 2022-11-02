package com.nlww.platform.base.mybatis.typeHandler;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * json转字符串数组handler
 *
 * @author 渔火江渚上
 * @since 2022/5/10 14:04
 */
public class JsonStringArrayTypeHandler extends BaseTypeHandler<String[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObject(rs.getString(columnName));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex));
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex));
    }

    private String toJson(String[] params) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:params){
            stringBuilder.append(s);
        }
        String res = stringBuilder.toString();
        return StringUtils.isBlank(res) ? "[]" : res;
    }

    private String[] toObject(String params) {
        if (StringUtils.isNotBlank(params)) {
            String[] array = params.split(",");
            return array;
        } else {
            return null;
        }
    }
}
