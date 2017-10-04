package net.genin.christophe.spring.boot.sharedmap;

import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;

import java.io.IOException;

public class CallClient {

    interface Cli {

        class Value {
            Integer nb;
        }

        @RequestLine("GET /api")
        @Headers("X-Session: {token}")
        Value get(@Param("token") String token);

    }

    static class CliError extends RuntimeException {
        private String message; // parsed from json

        @Override
        public String getMessage() {
            return message;
        }
    }


    static class CliDecoder implements ErrorDecoder {

        final Decoder decoder;
        final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

        CliDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                return (Exception) decoder.decode(response, CliError.class);
            } catch (IOException fallbackToDefault) {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}
