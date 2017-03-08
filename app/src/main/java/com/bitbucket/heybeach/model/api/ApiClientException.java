package com.bitbucket.heybeach.model.api;

public class ApiClientException extends Exception {

  ApiClientException(String message) {
    super(message);
  }

  ApiClientException(Throwable cause) {
    super(cause);
  }

}
