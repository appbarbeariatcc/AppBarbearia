package br.com.appbarbearia.util;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class JdbcRepository<T> {
	
    protected void setNullSafe(PreparedStatement stmt, Integer value, int index) throws SQLException {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(index, value);
        }
    }
//(value == null || value == 0)
    
    protected void setNullSafe(PreparedStatement stmt, Long value, int index) throws SQLException {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.BIGINT);
        } else {
            stmt.setLong(index, value);
        }
    }
    //(value == null || value == 0)

    protected void setNullSafe(PreparedStatement stmt, String value, int index) throws SQLException {
        if (isNull(value)) {
            stmt.setNull(index, java.sql.Types.VARCHAR);
        } else {
            stmt.setString(index, value);
        }
    }

    protected void setNullSafe(PreparedStatement stmt, Double value, int index) throws SQLException {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.DOUBLE);
        } else {
            stmt.setDouble(index, value);
        }
    }

    protected void setNullSafe(PreparedStatement stmt, Float value, int index) throws SQLException {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.DOUBLE);
        } else {
            stmt.setDouble(index, value);
        }
    }

    protected void setNullSafe(PreparedStatement stmt, java.util.Date date, int idx) throws SQLException {
        if (date == null) {
            stmt.setNull(idx++, java.sql.Types.TIMESTAMP);
        } else {
            stmt.setTimestamp(idx++, new java.sql.Timestamp(date.getTime()));
        }
    }

    protected void setNullSafe(PreparedStatement stmt, Boolean value, int index) throws SQLException {
        if (value != null) {
            stmt.setBoolean(index, value);
        } else {
            stmt.setNull(index, java.sql.Types.BOOLEAN);
        }
    }

    protected void setNullSafe(PreparedStatement stmt, byte[] value, int index) throws SQLException {
        if (value != null) {
            stmt.setBlob(index, new SerialBlob(value));
        } else {
            stmt.setNull(index, java.sql.Types.BLOB);
        }
    }

    protected Integer getIntOrNull(ResultSet rs, String string) throws SQLException {
        int value = rs.getInt(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Long getLongOrNull(ResultSet rs, String string) throws SQLException {
        long value = rs.getLong(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Float getFloatOrNull(ResultSet rs, String string) throws SQLException {
        Float value = rs.getFloat(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Double getDoubleOrNull(ResultSet rs, String string) throws SQLException {
        Double value = rs.getDouble(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected String getStringOrNull(ResultSet rs, String string) throws SQLException {
        String value = rs.getString(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Date getDateOrNull(ResultSet rs, String string) throws SQLException {
        Date value = rs.getDate(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Date getTimeOrNull(ResultSet rs, String string) throws SQLException {
        Date value = rs.getTime(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Date getTimestampOrNull(ResultSet rs, String string) throws SQLException {
        Date value = rs.getTimestamp(string);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    protected Boolean getBooleanOrNull(ResultSet rs, String string) throws SQLException {
        Boolean value = rs.getBoolean(string);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }

    protected byte[] getBlobOrNull(ResultSet rs, String string) throws SQLException {
        Blob blob = rs.getBlob(string);
        byte[] value = null;
        if (!rs.wasNull()) {
            long length = blob.length();
            value = (blob.getBytes(1, (int) length));
        }
        return value;
    }

    private boolean isNull(String value) {
        return (value == null || "".equals(value.trim()));
    }
    
    protected Set<ConstraintViolation<T>> validate(T entity) {
    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    	Validator validator = factory.getValidator();
    	
    	Set<ConstraintViolation<T>> violations = validator.validate(entity);
    	return violations;    	
    }

//    protected void atribuiAlteradoPor(T t) {
//    	t.setAlteradoPor("login");
//		
//	}
//    
//    protected void atribuiCriadoPor(T t) {
//    	t.setCriadoPor("login");
//    	t.setAlteradoPor("login");
//	}
    
}
