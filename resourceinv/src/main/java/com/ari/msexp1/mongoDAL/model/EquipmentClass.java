package com.ari.msexp1.mongoDAL.model;

/**
 * Basic equipment class - similar to iana-hardware hardware-class - should look at ONF physical model
 */

public enum EquipmentClass {
    backplane(1),
    chassis(2),
    cpu(3),
    fan(4),
    module(5),
    port(6),
    powersupply(7),
    sensor(8),
    stack(9),;

    private final int equipmentClass;

    private EquipmentClass(int equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public int getEquipmentClass() {
        return this.equipmentClass;
    }

}
