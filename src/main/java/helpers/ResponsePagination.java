package helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePagination<T>{
    private boolean success;
    private T data;
    private String message;
    private int code;
    private Map<String, Object> meta;
}
