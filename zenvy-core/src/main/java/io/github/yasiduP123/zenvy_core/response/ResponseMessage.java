package io.github.yasiduP123.zenvy_core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage<T>{
    private String message;
    private T response;
}
