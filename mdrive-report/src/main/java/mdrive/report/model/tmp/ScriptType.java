package mdrive.report.model.tmp;

/**
 * User: andrey.osipov
 */

public enum ScriptType {
    SYSTEM_REPORT((short) 0, "Jasper Report"),
    SQL((short) 1, "SQL Script"),
    BLT((short) 2, "Business Logic Template"),
    ENDEAVOR_SCRIPT((short) 3, "Endeavor Script"),
    PROCESS_CONFIG((short) 4, "Process Configuration"),
    USER_REPORT((short) 5, "User Report"),
    ETL((short) 6, "ETL Script"),
    REPORTS_NAVIGATION((short) 7, "Reports Navigation Script");

    private short id;
    private String title;

    private ScriptType(short id, String title) {
        this.id = id;
        this.title = title;
    }

    public short getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static ScriptType getById(short id) {
        for (ScriptType value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        throw new IllegalArgumentException("ScriptType is not supported: ID[" + id + "]");
    }

}
