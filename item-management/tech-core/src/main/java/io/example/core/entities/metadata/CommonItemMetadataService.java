package io.example.core.entities.metadata;

import io.example.core.api.errors.GeneralInternalError;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Extension point for metadata providers. Inheritors may provide additional means to find proper id for given type.
 */
@Service
public class CommonItemMetadataService implements MetadataService {

    public Metadata makeItemMetadata(final Object o) {
        return new Metadata(findIdForType(o.getClass()), o);
    }

    protected MetadataId[][] getAdditionalIds() {
        return new MetadataId[][]{{}};
    }

    private MetadataId findIdForType(final Class<?> type) {
        final var idPool = Stream.concat(
            Arrays.stream(CommonItemMetadata.values()),
            Arrays.stream(getAdditionalIds()).flatMap(Arrays::stream)
        );

        return idPool
            .filter(id -> id.matches(type))
            .findFirst()
            .orElseThrow(() -> new GeneralInternalError("Unsupported metadata type: " + type));
    }
}
