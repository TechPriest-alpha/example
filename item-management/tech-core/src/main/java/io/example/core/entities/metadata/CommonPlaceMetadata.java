package io.example.core.entities.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonPlaceMetadata implements MetadataId {
    EXAMPLE_PLACE_METADATA_1(String.class),
    EXAMPLE_PLACE_METADATA_2(Long.class);
    @Getter
    private final Class<?> dataClass;
}
