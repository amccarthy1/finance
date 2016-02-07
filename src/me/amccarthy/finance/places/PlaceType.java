package me.amccarthy.finance.places;

import me.amccarthy.finance.messages.MessageService;
import sun.plugin2.message.Message;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public enum PlaceType {
    RESTAURANT("restaurant", "finance.groups.restaurant"),
    CAFE("cafe", "finance.groups.restaurant"),
    FOOD("food", "finance.groups.restaurant"),
    HOSPITAL("hospital", "finance.groups.hospital"),
    UNKNOWN("unknown", "finance.groups.unknown")
    ;

    private String apiType;
    private String msgCode;

    PlaceType(String apiType, String msgCode) {
        this.apiType = apiType;
        this.msgCode = msgCode;
    }

    public static PlaceType reverse(String apiType) {
        try {
            return valueOf(apiType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    public String getMessage() {
        return MessageService.getInstance().getMessage(msgCode);
    }
}
