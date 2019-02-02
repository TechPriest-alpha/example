package io.example.infrastructure;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.util.List;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@AllArgsConstructor
@ToString(exclude = {"handle"})
public final class HandlerContainer {
    public final String methodName;
    public final MethodHandle handle;
    public final List<Class<? extends Annotation>> handlerAnnotations;

    public boolean has(final Class<? extends Annotation> annotation) {
        return handlerAnnotations.contains(annotation);
    }
}
