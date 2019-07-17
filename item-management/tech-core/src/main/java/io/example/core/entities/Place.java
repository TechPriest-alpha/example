package io.example.core.entities;

import io.example.core.entities.commons.BaseEntity;
import io.example.core.entities.commons.Position;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;


@Value
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Place extends BaseEntity {
    private final String id;
    private final Position position;
}
