package common.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ItemCategory {
    @JsonProperty("NOTEBOOK")
    NOTEBOOK,
    @JsonProperty("DESKTOP")
    DESKTOP,
    @JsonProperty("SERVER")
    SERVER
}
