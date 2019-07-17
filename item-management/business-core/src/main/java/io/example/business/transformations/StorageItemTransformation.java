package io.example.business.transformations;

import io.example.business.data.StorageItem;
import io.example.business.extension.CustomItemMetadata;
import io.example.core.entities.Item;
import io.example.core.entities.metadata.CommonItemMetadata;
import io.example.core.entities.metadata.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StorageItemTransformation {
    private final MetadataService metadataService;

    public StorageItem fromItem(final Item item) {
        return StorageItem.builder()
            .id(item.getId())
            .quantity(item.getQuantity())
            .measureUnit(item.getMeasureUnit())
            .name(item.getName())
            .code(item.getMetadata(CustomItemMetadata.CUSTOM_ITEM_METADATA_1))
            .customerName(item.getMetadata(CommonItemMetadata.EXAMPLE_ITEM_METADATA_1))
            .orderNumber(item.getMetadata(CommonItemMetadata.EXAMPLE_ITEM_METADATA_2))
            .build();
    }

    public Item fromStorageItem(final StorageItem item) {
        final var result = Item.builder()
            .id(item.getId())
            .measureUnit(item.getMeasureUnit())
            .quantity(item.getQuantity())
            .name(item.getName())
            .build();
        result.updateMetadata(metadataService.makeItemMetadata(item.getCode()));
        result.updateMetadata(metadataService.makeItemMetadata(item.getCustomerName()));
        result.updateMetadata(metadataService.makeItemMetadata(item.getOrderNumber()));
        return result;
    }
}
