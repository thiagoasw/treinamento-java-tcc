package com.totvs.tjc.infra;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "from")
public final class CommandFailure<T> implements Failure {

    private final Collection<ConstraintViolation<T>> violations;
    
}
