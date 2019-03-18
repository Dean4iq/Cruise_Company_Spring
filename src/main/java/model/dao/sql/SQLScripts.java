package model.dao.sql;

public enum SQLScripts {
    INSTANCE;

    public static String FIND_CRUISE_FULL_INFO = "SELECT * FROM cruise INNER JOIN ship ON sh_id=ship_sh_id INNER JOIN route ON cr_id=cruise_cr_id INNER JOIN harbor ON hb_id=harbor_hb_id INNER JOIN country ON co_id=country_co_id ORDER BY route.departure;";
}
