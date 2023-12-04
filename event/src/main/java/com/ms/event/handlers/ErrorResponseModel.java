package com.ms.event.handlers;

import java.time.LocalDateTime;

public record ErrorResponseModel(Integer status, LocalDateTime date, String message, String requestURI) {

}
