package com.hbt.QuanLyGiaoHangBackend.exception;

import java.util.Map;

public class DuplicateFieldException extends RuntimeException {
    private Map<String, String> errors;

    public DuplicateFieldException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}