package org.talang.sdk.models.results;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SamplersResult {
    /**
     * Aliases
     */
    private List<String> aliases;
    /**
     * Name
     */
    private String name;
    /**
     * Options
     */
    private Map<String, String> options;
}
