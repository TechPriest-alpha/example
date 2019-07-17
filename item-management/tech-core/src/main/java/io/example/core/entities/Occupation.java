package io.example.core.entities;

import io.example.core.entities.commons.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Occupation extends BaseEntity {
    private final String id;
    private final Item item;
    private final Place place;
}
