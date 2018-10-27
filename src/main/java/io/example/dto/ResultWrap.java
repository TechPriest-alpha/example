package io.example.dto;

import lombok.Value;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Value
public class ResultWrap<T> implements DtoMarker {
    private final T data;
}
