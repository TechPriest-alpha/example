package io.example.business.extension;

import io.example.core.entities.metadata.CommonItemMetadataService;
import io.example.core.entities.metadata.MetadataId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomItemMetadataService extends CommonItemMetadataService {

    @Override
    protected MetadataId[][] getAdditionalIds() {
        return new MetadataId[][]{CustomItemMetadata.values()};
    }
}
