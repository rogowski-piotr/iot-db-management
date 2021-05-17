package pl.piotr.iotdbmanagement.utils;

import java.util.Objects;
import java.util.function.Function;

public interface QuadriFunction<A, B, C, D, E> {

    E apply(A a, B b, C c, D d);

    default <V> QuadriFunction<A, B, C, D, V> andThen(Function<? super E, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c, D d) -> after.apply(apply(a, b, c, d));
    }
    
}
