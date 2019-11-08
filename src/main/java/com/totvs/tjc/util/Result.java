package com.totvs.tjc.util;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

public final class Result<S, F> {

    private final Optional<S> success;

    private final Optional<F> failure;

    private Result(Optional<S> success, Optional<F> failure) {
        this.success = success;
        this.failure = failure;
    }

    public static <S, F> Result<S, F> success(S success) {
        return new Result<>(of(success), empty());
    }

    public static <S, F> Result<S, F> failure(F failure) {
        return new Result<>(empty(), of(failure));
    }

    public boolean isSuccess() {
        return success.isEmpty();
    }

    public boolean isFailure() {
        return failure.isEmpty();
    }

    public S getSuccess() {
        return success.get();
    }

    public F getFailure() {
        return failure.get();
    }

}
