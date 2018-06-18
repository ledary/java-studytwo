package dubbo.wk.dao.config;


import dubbo.wk.model.domain.enmus.SexEnums;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public class SexEnumHandler implements TypeHandler<SexEnums> {
    @Override
    public void setParameter(PreparedStatement ps, int i, SexEnums sexEnums, JdbcType jdbcType) throws SQLException {
        ps.setByte(i,sexEnums.getCode());
    }

    @Override
    public SexEnums getResult(ResultSet resultSet, String s) throws SQLException {
        return SexEnums.getSex(resultSet.getByte(s));
    }

    @Override
    public SexEnums getResult(ResultSet resultSet, int i) throws SQLException {
      return SexEnums.getSex(resultSet.getByte(i));

    }

    @Override
    public SexEnums getResult(CallableStatement callableStatement, int i) throws SQLException {
        return SexEnums.getSex(callableStatement.getByte(i));

    }
}
