package io.example.core.entities.commons;

import io.example.core.entities.metadata.Metadata;
import io.example.core.entities.metadata.MetadataId;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public abstract class BaseEntity {
    private final Map<MetadataId, Metadata> metadata = new ConcurrentHashMap<>();

    public abstract String getId();

    public <T> T getMetadata(final MetadataId metadataId) {
        return metadata.getOrDefault(metadataId, null).getCastData();
    }

    public void updateMetadata(final Metadata metadataValue) {
        this.metadata.put(metadataValue.getMetadataId(), metadataValue);
    }
}
