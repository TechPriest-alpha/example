package io.example.core.entities;

import io.example.core.entities.commons.BaseEntity;
import io.example.core.entities.dictionary.MeasureUnit;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {
    private final String id;
    private final String name;
    private final long quantity;
    private final MeasureUnit measureUnit;
}
