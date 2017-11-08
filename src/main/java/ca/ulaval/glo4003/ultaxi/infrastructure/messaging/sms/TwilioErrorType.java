package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import java.util.HashMap;
import java.util.Map;

public enum TwilioErrorType {
    QUEUE_OVERFLOW(30001),
    HTTP_CONNECTION_FAILURE(11205),
    OTHER(-1);

    private static final Map<Integer, TwilioErrorType> typesByIntegerCode = new HashMap<>();
    private final int value;

    static {
        for (TwilioErrorType errorType : TwilioErrorType.values()) {
            typesByIntegerCode.put(errorType.value, errorType);
        }
    }

    TwilioErrorType(int value) {
        this.value = value;
    }

    public static TwilioErrorType valueOf(int value) {
        return typesByIntegerCode.getOrDefault(value, OTHER);
    }
}
