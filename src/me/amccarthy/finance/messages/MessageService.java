package me.amccarthy.finance.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class MessageService {
    private static MessageService instance;
    private static final String RESOURCE_NAME = "messages.properties";

    private Properties messages;

    private MessageService(String resourceName) {
        messages = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(resourceName)){
            messages.load(inputStream);
        } catch (IOException e) {
            // Application won't function properly without messages,
            // but it might still help to see the breakdown of groups.
            // So we won't exit, even though this would be pretty catastrophic.
            e.printStackTrace();
        }
    }

    /**
     * Look up a message from the properties file using a message code.
     * <p>
     *     If a message with the given code is not found, the message code is
     *     returned.
     * </p>
     * @param code
     *      The code for the message. Usually hierarchical with . as a delimiter
 *      @param args
     *      Any additional required strings. Will be given to String.format.
     * @return
     *      The
     */
    public String getMessage(String code, String... args) {
        String m = messages.getProperty(code, code);
        return String.format(m, (Object[])args);
    }

    public static synchronized MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService(RESOURCE_NAME);
        }
        return instance;
    }
}
