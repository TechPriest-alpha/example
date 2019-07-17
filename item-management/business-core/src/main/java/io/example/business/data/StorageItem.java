package io.example.business.data;

import io.example.core.entities.dictionary.MeasureUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class StorageItem {
    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    private final long quantity;
    @NonNull
    private final MeasureUnit measureUnit;

    @NonNull
    private final Long orderNumber;
    @NonNull
    private final String customerName;
    @NonNull
    private final Integer code;
}
