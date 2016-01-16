package com.mmrath.sapp.web.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transfering error message with a list of field errors.
 */
public class ErrorData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorData> fieldErrors;

    ErrorData(String message) {
        this(message, null);
    }

    ErrorData(String message, String description) {
        this.message = message;
        this.description = description;
    }

    ErrorData(String message, String description, List<FieldErrorData> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorData(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorData> getFieldErrors() {
        return fieldErrors;
    }
}
