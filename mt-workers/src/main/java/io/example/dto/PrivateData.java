package io.example.dto;

import lombok.Value;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Value
public class PrivateData implements DtoMarker {
    public static final PrivateData NULL = new PrivateData();
}
