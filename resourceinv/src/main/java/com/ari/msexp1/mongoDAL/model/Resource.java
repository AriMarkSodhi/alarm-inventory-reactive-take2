package com.ari.msexp1.mongoDAL.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * fairly simple resource model
 * - does not handle distinction between configured/actual
 * - does not handle physical constraints - how many slots, which are occupied, technology and mfg specific details of equipment etc...
 *   much could be encoded into a meta model for mfg equipment and would not have to be discovered from devices
 * - does not model cards completely
 * - does not model remote subtending equipment
 */

@Document(collection = "resource")
public class Resource {

    @Id
    private String id;

    private String name;
    private String parentId;
    private List<String> childIds;
    private Date timeCreated;
    private String description;
    private EquipmentClass eqType;
    private long physicalIndex;
    private String hwRev;
    private String firmwareRev;
    private String swRev;
    private String mfgName;
    private String modelName;
    private Date mfgDate;
    private Boolean isFRU;
    private OperatorState operStatus;
    private UsageState usageStatus;
    private AlarmState alarmStatus;

    // isContainer, isOccupied,

    public Resource() {
    }

    @PersistenceConstructor
    public Resource(String id, String name, String parentId, List<String> childIds, Date timeCreated, String description,
                    EquipmentClass eqType, long physicalIndex, String hwRev, String firmwareRev, String swRev,
                    String mfgName, String modelName, Date mfgDate, Boolean isFRU, OperatorState operStatus,
                    UsageState usageStatus, AlarmState alarmStatus) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.childIds = childIds;
        this.timeCreated = timeCreated;
        this.description = description;
        this.eqType = eqType;
        this.physicalIndex = physicalIndex;
        this.hwRev = hwRev;
        this.firmwareRev = firmwareRev;
        this.swRev = swRev;
        this.mfgName = mfgName;
        this.modelName = modelName;
        this.mfgDate = mfgDate;
        this.isFRU = isFRU;
        this.operStatus = operStatus;
        this.usageStatus = usageStatus;
        this.alarmStatus = alarmStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
    }

    public List<String> getChildIds() {
        return childIds;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public String getDescription() {
        return description;
    }

    public EquipmentClass getEqType() {
        return eqType;
    }

    public long getPhysicalIndex() {
        return physicalIndex;
    }

    public String getHwRev() {
        return hwRev;
    }

    public String getFirmwareRev() {
        return firmwareRev;
    }

    public String getSwRev() {
        return swRev;
    }

    public String getMfgName() {
        return mfgName;
    }

    public String getModelName() {
        return modelName;
    }

    public Date getMfgDate() {
        return mfgDate;
    }

    public Boolean getFRU() {
        return isFRU;
    }

    public OperatorState getOperStatus() {
        return operStatus;
    }

    public UsageState getUsageStatus() {
        return usageStatus;
    }

    public AlarmState getAlarmStatus() {
        return alarmStatus;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", childIds=" + childIds +
                ", timeCreated=" + timeCreated +
                ", description='" + description + '\'' +
                ", eqType=" + eqType +
                ", physicalIndex=" + physicalIndex +
                ", hwRev='" + hwRev + '\'' +
                ", firmwareRev='" + firmwareRev + '\'' +
                ", swRev='" + swRev + '\'' +
                ", mfgName='" + mfgName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", mfgDate=" + mfgDate +
                ", isFRU=" + isFRU +
                ", operStatus=" + operStatus +
                ", usageStatus=" + usageStatus +
                ", alarmStatus=" + alarmStatus +
                '}';
    }
}