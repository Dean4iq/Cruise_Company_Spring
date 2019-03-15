package model.dao.util;

public enum SQLOperation {
    SELECT(new String[]{"SELECT * FROM "}),
    INSERT(new String[]{"INSERT INTO ", " (", ") VALUES (", ");"}),
    DELETE(new String[]{"DELETE FROM ", " WHERE "}),
    UPDATE(new String[]{"UPDATE ",  " SET ", " WHERE "}),
    FIND_BY_ID(new String[]{"SELECT * FROM ", " WHERE "});

    String[] sources;

    SQLOperation(String[] sources) {
        this.sources = sources;
    }
}
