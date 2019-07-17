package io.example.core.entities.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonItemMetadata implements MetadataId {
    EXAMPLE_ITEM_METADATA_1(String.class),
    EXAMPLE_ITEM_METADATA_2(Long.class);
    @Getter
    private final Class<?> dataClass;
}
