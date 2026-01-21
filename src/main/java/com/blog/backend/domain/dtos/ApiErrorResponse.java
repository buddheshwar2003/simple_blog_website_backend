package com.blog.backend.domain.dtos;

import java.util.List;
import java.util.Objects;

public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldError> errors;

    // ---- Constructors ----
    public ApiErrorResponse(int status, String message, List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    private ApiErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.errors = builder.errors;
    }

    // ---- Getters & Setters ----
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    // ---- Builder ----
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String message;
        private List<FieldError> errors;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder errors(List<FieldError> errors) {
            this.errors = errors;
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this);
        }
    }

    // ---- Overrides ----
    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiErrorResponse that = (ApiErrorResponse) o;
        return status == that.status &&
                Objects.equals(message, that.message) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, errors);
    }

    // ---- Inner FieldError class ----
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "FieldError{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FieldError that = (FieldError) o;
            return Objects.equals(field, that.field) &&
                    Objects.equals(message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(field, message);
        }
    }
}
