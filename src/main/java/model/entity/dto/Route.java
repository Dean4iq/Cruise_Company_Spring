package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "route")
public class Route {
    @TableField(name = "cruise_cr_id", primaryKey = true)
    private int cruiseId;
    @TableField(name = "harbor_hb_id", primaryKey = true)
    private int harborId;

    private Cruise cruise;
    private Harbor harbor;

    public int getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }

    public int getHarborId() {
        return harborId;
    }

    public void setHarborId(int harborId) {
        this.harborId = harborId;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }
}
