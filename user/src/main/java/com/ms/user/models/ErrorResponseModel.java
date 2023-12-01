package com.ms.user.models;

import java.time.LocalDateTime;

public record ErrorResponseModel(Integer status, LocalDateTime date, String message, String requestURI) {

}
