package model.entity;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "route")
public class Route {
    @TableField(name = "cruise_cr_id", primaryKey = true)
    private int cruiseId;
    @TableField(name = "harbor_hb_id", primaryKey = true)
    private int harborId;

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
}
