package io.example.core.entities.metadata;

public interface MetadataId {

    default boolean matches(final Class<?> type) {
        return getDataClass().equals(type);
    }

    Class<?> getDataClass();

    default <T> T cast(final Object o) {
        //noinspection unchecked
        return (T) getDataClass().cast(o);
    }
}
