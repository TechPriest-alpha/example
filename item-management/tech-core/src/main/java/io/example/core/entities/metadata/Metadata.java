package io.example.core.entities.metadata;

import lombok.Value;

@Value
public class Metadata {
    private final MetadataId metadataId;
    private final Object data;

    public <T> T getCastData() {
        return metadataId.cast(data);
    }
}
