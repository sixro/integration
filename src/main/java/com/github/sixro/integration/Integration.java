package com.github.sixro.integration;

public interface Integration<I, O> {

    O run(I input);

}
