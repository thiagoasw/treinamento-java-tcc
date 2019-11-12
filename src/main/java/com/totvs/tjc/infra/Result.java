package com.totvs.tjc.infra;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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
        return failure.isEmpty();
    }

    public boolean isFailure() {
        return success.isEmpty();
    }

    public S getSuccess() {
        return success.get();
    }

    public F getFailure() {
        return failure.get();
    }
    
    public void ifSuccess(Consumer<? super S> action) {
        if (isSuccess()) {
            action.accept(getSuccess());
        }
    }

    public void ifFailure(Consumer<? super F> action) {
        if (isFailure()) {
            action.accept(getFailure());
        }
    }
    
    public void ifSuccessOrElse(Consumer<? super S> action, Runnable emptyAction) {
        if (isSuccess()) {
            action.accept(getSuccess());
        } else {
            emptyAction.run();
        }
    }
    
    public void ifFailureOrElse(Consumer<? super F> action, Runnable emptyAction) {
        if (isFailure()) {
            action.accept(getFailure());
        } else {
            emptyAction.run();
        }
    }
    
    public void ifSuccessOrFailure(Consumer<? super S> successAction, Consumer<? super F> failureAction) {
        if (isSuccess()) {
            successAction.accept(getSuccess());
        } else {
            failureAction.accept(getFailure());
        }
    }
    
    public <U> Optional<U> mapSuccess(Function<? super S, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isFailure()) {
            return empty();
        } else {
            return ofNullable(mapper.apply(getSuccess()));
        }
    }
    
    public <U> Optional<U> mapFailure(Function<? super F, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isSuccess()) {
            return empty();
        } else {
            return ofNullable(mapper.apply(getFailure()));
        }
    }
    
    public <U> Optional<U> mapSuccessOrFailure(
        Function<? super S, ? extends U> successMapper, 
        Function<? super F, ? extends U> failureMapper
    ) {
        requireNonNull(successMapper);
        requireNonNull(failureMapper);
        if (isSuccess()) {
            return mapSuccess(successMapper);
        } else {
            return mapFailure(failureMapper);
        }
    }
    
    public <U> Optional<U> flatMapSuccess(Function<? super S, ? extends Optional<? extends U>> mapper) {
        requireNonNull(mapper);
        if (isFailure()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            Optional<U> r = (Optional<U>) mapper.apply(getSuccess());
            return requireNonNull(r);
        }
    }
    
    public <U> Optional<U> flatMapFailure(Function<? super F, ? extends Optional<? extends U>> mapper) {
        requireNonNull(mapper);
        if (isSuccess()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            Optional<U> r = (Optional<U>) mapper.apply(getFailure());
            return requireNonNull(r);
        }
    }
    
    public <U> Optional<U> flatMapSuccessOrFailure(
        Function<? super S, ? extends Optional<? extends U>> successMapper, 
        Function<? super F, ? extends Optional<? extends U>> failureMapper
    ) {
        requireNonNull(successMapper);
        requireNonNull(failureMapper);
        if (isSuccess()) {
            return flatMapSuccess(successMapper);
        } else {
            return flatMapFailure(failureMapper);
        }
    }
}
