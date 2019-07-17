package io.example.business.extension;

import io.example.core.entities.metadata.MetadataId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CustomItemMetadata implements MetadataId {
    CUSTOM_ITEM_METADATA_1(Integer.class);
    @Getter
    private final Class<?> dataClass;
}
