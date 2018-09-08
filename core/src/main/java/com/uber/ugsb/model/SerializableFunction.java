package com.uber.ugsb.model;

import java.io.Serializable;
import java.util.function.Function;

public interface SerializableFunction<D, R> extends Function<D, R>, Serializable {
}
