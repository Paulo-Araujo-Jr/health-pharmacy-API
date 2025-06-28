package com.healthPharmacy.demo.infra.exception;

public class NoExistentAttributeException extends Throwable {
    public NoExistentAttributeException(String invalidAttributeName) {
        super(invalidAttributeName);
    }
}
