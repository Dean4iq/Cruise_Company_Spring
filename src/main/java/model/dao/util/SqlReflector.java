package model.dao.util;

import annotation.TableField;
import annotation.TableName;
import exception.AnnotationAbsenceException;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Field;

import static model.dao.util.SQLOperation.*;

public class SqlReflector {
    public <T> String process(Class<T> clazz, SQLOperation sqlOperation)
            throws OperationNotSupportedException, AnnotationAbsenceException {
        if (clazz.isAnnotationPresent(TableName.class)) {
            if (sqlOperation.equals(INSERT)) {
                return insertion(clazz);
            } else if (sqlOperation.equals(UPDATE)) {
                return update(clazz);
            } else if (sqlOperation.equals(SELECT)) {
                return selection(clazz);
            } else if (sqlOperation.equals(DELETE)) {
                return delete(clazz);
            } else if (sqlOperation.equals(FIND_BY_ID)) {
                return findById(clazz);
            }

            throw new OperationNotSupportedException(sqlOperation.name());
        } else {
            throw new AnnotationAbsenceException(TableName.class.getName());
        }
    }

    private <T> String insertion(Class<T> clazz) throws AnnotationAbsenceException {
        int declaredFieldsNumber = 0;

        StringBuilder stringBuilder = new StringBuilder().append(INSERT.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(INSERT.sources[1]);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && !field.getAnnotation(TableField.class).autoincremented()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(", ");
                declaredFieldsNumber++;
            }
        }

        if (declaredFieldsNumber == 0) {
            throw new AnnotationAbsenceException(TableField.class.getName());
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        stringBuilder.append(INSERT.sources[2]);

        for (int i = 0; i < declaredFieldsNumber; i++) {
            stringBuilder.append("?, ");
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        stringBuilder.append(INSERT.sources[3]);

        return stringBuilder.toString();
    }

    private <T> String update(Class<T> clazz) throws AnnotationAbsenceException {
        int declaredFieldsNumber = 0;

        StringBuilder stringBuilder = new StringBuilder().append(UPDATE.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(UPDATE.sources[1]);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && !field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ?, ");
                declaredFieldsNumber++;
            }
        }

        if (declaredFieldsNumber == 0) {
            throw new AnnotationAbsenceException(TableField.class.getName());
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        stringBuilder.append(UPDATE.sources[2]);

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ? AND ");

            }
        }

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), "");
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    private <T> String selection(Class<T> clazz) {
        return new StringBuilder().append(SELECT.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(";").toString();
    }

    private <T> String delete(Class<T> clazz) throws AnnotationAbsenceException{
        int declaredFieldsNumber = 0;

        StringBuilder stringBuilder = new StringBuilder().append(DELETE.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(DELETE.sources[1]);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ? AND ");
                declaredFieldsNumber++;
            }
        }

        if (declaredFieldsNumber == 0) {
            throw new AnnotationAbsenceException(TableField.class.getName());
        }

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), "");
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    private <T> String findById(Class<T> clazz) throws AnnotationAbsenceException{
        int declaredFieldsNumber = 0;

        StringBuilder stringBuilder = new StringBuilder().append(FIND_BY_ID.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(FIND_BY_ID.sources[1]);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ? AND ");
                declaredFieldsNumber++;
            }
        }

        if (declaredFieldsNumber == 0) {
            throw new AnnotationAbsenceException(TableField.class.getName());
        }

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), "");
        stringBuilder.append(";");

        return stringBuilder.toString();
    }
}
