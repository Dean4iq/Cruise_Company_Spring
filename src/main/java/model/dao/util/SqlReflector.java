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
        StringBuilder stringBuilder = addTableName(clazz, INSERT);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && !field.getAnnotation(TableField.class).autoincremented()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(", ");
                declaredFieldsNumber++;
            }
        }

        checkOnFieldsNumber(declaredFieldsNumber);

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
        StringBuilder stringBuilder = addTableName(clazz, UPDATE);
        int declaredFieldsNumber = addFields(clazz, stringBuilder);

        checkOnFieldsNumber(declaredFieldsNumber);

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        stringBuilder.append(UPDATE.sources[2]);

        declaredFieldsNumber = addPrimaryKeyFields(clazz, stringBuilder);
        checkOnFieldsNumber(declaredFieldsNumber);

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), ";");

        return stringBuilder.toString();
    }

    private <T> String selection(Class<T> clazz) {
        return new StringBuilder().append(SELECT.sources[0])
                .append(clazz.getAnnotation(TableName.class).name()).append(";").toString();
    }


    private <T> String delete(Class<T> clazz) throws AnnotationAbsenceException {
        StringBuilder stringBuilder = addTableName(clazz, DELETE);
        int declaredFieldsNumber = addPrimaryKeyFields(clazz, stringBuilder);

        checkOnFieldsNumber(declaredFieldsNumber);

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), ";");

        return stringBuilder.toString();
    }

    private <T> String findById(Class<T> clazz) throws AnnotationAbsenceException {
        StringBuilder stringBuilder = addTableName(clazz, FIND_BY_ID);
        int declaredFieldsNumber = addPrimaryKeyFields(clazz, stringBuilder);

        checkOnFieldsNumber(declaredFieldsNumber);

        stringBuilder.replace(stringBuilder.length() - 5, stringBuilder.length(), ";");

        return stringBuilder.toString();
    }

    private <T> StringBuilder addTableName(Class<T> clazz, SQLOperation sqlOperation) {
        return new StringBuilder().append(sqlOperation.sources[0])
                .append(clazz.getAnnotation(TableName.class).name())
                .append(sqlOperation.sources[1]);
    }

    private <T> int addFields(Class<T> clazz, StringBuilder stringBuilder) {
        int declaredFieldsNumber = 0;

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && !field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ?, ");
                declaredFieldsNumber++;
            }
        }

        return declaredFieldsNumber;
    }

    private <T> int addPrimaryKeyFields(Class<T> clazz, StringBuilder stringBuilder) {
        int declaredFieldsNumber = 0;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)
                    && field.getAnnotation(TableField.class).primaryKey()) {
                stringBuilder.append(field.getAnnotation(TableField.class).name()).append(" = ? AND ");
                declaredFieldsNumber++;
            }
        }

        return declaredFieldsNumber;
    }

    private void checkOnFieldsNumber(int declaredFieldsNumber) throws AnnotationAbsenceException {
        if (declaredFieldsNumber == 0) {
            throw new AnnotationAbsenceException(TableField.class.getName());
        }
    }
}
