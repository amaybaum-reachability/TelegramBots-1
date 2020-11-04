package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to receive incoming updates using long polling (wiki). An Array of Update
 * objects is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUpdates extends BotApiMethod<ArrayList<Update>>{
    public static final String PATH = "getupdates";

    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";
    private static final String TIMEOUT_FIELD = "timeout";
    private static final String ALLOWEDUPDATES_FIELD = "allowed_updates";

    /**
     * Optional. Identifier of the first update to be returned. Must be greater by one than the
     * highest among the identifiers of previously received updates. By default, updates starting
     * with the earliest unconfirmed update are returned. An update is considered confirmed as soon
     * as getUpdates is called with an offset higher than its update_id. The negative offset can be
     * specified to retrieve updates starting from -offset update from the end of the updates queue.
     * All previous updates will forgotten.
     */
    @JsonProperty(OFFSET_FIELD)
    private Integer offset;
    /**
     * Optional	Limits the number of updates to be retrieved. Values between 1—100 are accepted.
     * Defaults to 100
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;
    /**
     * Optional. Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling.
     * Should be positive, 0 should be used for testing purposes only.
     */
    @JsonProperty(TIMEOUT_FIELD)
    private Integer timeout;
    /**
     * List the types of updates you want your bot to receive.
     * For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive
     * updates of these types. Specify an empty list to receive all updates regardless of type (default).
     * If not specified, the previous setting will be used.
     *
     * Please note that this parameter doesn't affect updates created before the call to the setWebhook,
     * so unwanted updates may be received for a short period of time.
     */
    @JsonProperty(ALLOWEDUPDATES_FIELD)
    @Singular
    private List<String> allowedUpdates;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<Update> deserializeResponse(String answer) throws
            TelegramApiRequestException {
        try {
            ApiResponse<ArrayList<Update>> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ArrayList<Update>>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting updates", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
