package model.dao.sql;

public enum SQLScripts {
    INSTANCE;

    public static String FIND_CRUISE_BY_COUNTRY = "SELECT *\n" +
            "FROM cruise\n" +
            "INNER JOIN route ON cr_id=cruise_cr_id\n" +
            "INNER JOIN harbor ON hb_id=harbor_hb_id\n" +
            "INNER JOIN country ON co_id=country_co_id\n" +
            "WHERE country.name = ?;";
}
