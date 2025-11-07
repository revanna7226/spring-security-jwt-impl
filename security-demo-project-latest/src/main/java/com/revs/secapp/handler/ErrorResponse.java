package com.revs.secapp.handler;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    private String message;
    private String code;
    private List<ValidationError> validationErrors;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class ValidationError {
        private String field;
        private String code;
        private String message;
    }

}
