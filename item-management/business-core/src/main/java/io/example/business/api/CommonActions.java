package io.example.business.api;

import io.example.business.data.StorageItem;
import io.example.business.transformations.StorageItemTransformation;
import io.example.core.api.services.ItemCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonActions implements CommonBusinessActions {
    private final StorageItemTransformation storageItemTransformation;
    private final ItemCrud itemCrud;

    @Transactional
    public void createItem(final StorageItem storageItem) {
        itemCrud.saveItem(storageItemTransformation.fromStorageItem(storageItem));
    }

    public StorageItem loadItem(final String id) {
        return storageItemTransformation.fromItem(itemCrud.loadItem(id));
    }
}
