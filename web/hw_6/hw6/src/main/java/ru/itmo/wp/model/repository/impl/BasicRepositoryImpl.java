package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public abstract class BasicRepositoryImpl<T> {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

//    public T find(Object obj, String param, String table) {
//        try (Connection connection = DATA_SOURCE.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table+ " WHERE " + param + "=?")) {
//                StringBuilder sb = new StringBuilder(param);
//                sb.replace(0, 1, String.valueOf(param.charAt(0)).toUpperCase());
//                param = sb.toString();
//                statement.setLong(1, (Long) obj.getClass().getDeclaredMethod("get" + param).invoke(obj));
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    return toObj(statement.getMetaData(), resultSet);
//                }
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException("Can't find ", e);
//        }
//    }
//
//    private T toObj(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
//        if (!resultSet.next()) {
//            return null;
//        }
//
//        T obj;
//        for (int i = 1; i <= metaData.getColumnCount(); i++) {
//            obj.getClass().getDeclaredMethod()
//            switch (metaData.getColumnName(i)) {
//                case "id":
//                    message.setId(resultSet.getLong(i));
//                    break;
//                case "sourceUserId":
//                    message.setSourceUserId(resultSet.getLong(i));
//                    break;
//                case "targetUserId":
//                    message.setTargetUserId(resultSet.getLong(i));
//                    break;
//                case "text":
//                    message.setText(resultSet.getString(i));
//                    break;
//                case "creationTime":
//                    message.setCreationTime(resultSet.getTimestamp(i));
//                    break;
//                default:
//                    // No operations.
//            }
//        }
//
//        return message;
//    }

    private String buildSql(String tableName, Map<String, String> values) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `");
        sql.append(tableName);
        sql.append("` (");
        sql.append(String.join(", ", values.keySet()));
        sql.append(") VALUES (");
        for (String key : values.keySet()) {
            sql.append(values.get(key));
            sql.append(", ");
        }
        sql.delete(sql.length() - 2, sql.length() - 1);
        sql.append(")");

        return sql.toString();
    }

    private Method getMethod(List<Method> methods, String methodName) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public void save(Object obj, String tableName, Map<String, String> values) {
        String sql = buildSql(tableName, values);
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            )) {
                List<Method> methods = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredMethods()));
                int index = 1;
                for (String key : values.keySet()) {
                    Object res;
                    try {
                        int j = 0;
                        for (; j < methods.size(); j++) {
                            if (("get" + key.substring(1, key.length() - 1).toLowerCase()).equals(methods.get(j).getName().toLowerCase())) {
                                break;
                            }
                        }
                        res = methods.get(j).invoke(obj);
                    } catch (Exception e) {
                        throw new ServletException("Can't invoke method");
                    }
                    switch (res.getClass().getSimpleName()) {
                        case "Integer":
                            statement.setInt(index, (Integer) res);
                            break;
                        case "Long":
                            statement.setLong(index, (Long) res);
                            break;
                        case "String":
                            statement.setString(index, (String) res);
                            break;
                        case "Types":
                            statement.setInt(index, ((Event.Types) res).getNum());
                            break;
                        default:
                            throw new ServletException("Unknow Variable");
                    }
                    index++;
                }
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + obj.getClass().getSimpleName() + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        Method setId = getMethod(methods, "setId");
                        if (setId != null) {
                            setId.invoke(obj, generatedKeys.getInt(1));
                        }
                    } else {
                        throw new RepositoryException("Can't save User [no autogenerated fields].");
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ServletException e) {
            throw new RepositoryException("Can't save " + obj.getClass().getSimpleName() + ".", e);
        }
    }
}
