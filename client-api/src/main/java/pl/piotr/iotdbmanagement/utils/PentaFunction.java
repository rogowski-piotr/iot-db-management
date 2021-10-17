package pl.piotr.iotdbmanagement.utils;

import java.util.Objects;
import java.util.function.Function;

public interface PentaFunction<A, B, C, D, E, F> {

    F apply(A a, B b, C c, D d, E e);

    default <V> PentaFunction<A, B, C, D, E, V> andThen(Function<? super F, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c, D d, E e) -> after.apply(apply(a, b, c, d, e));
    }

}
