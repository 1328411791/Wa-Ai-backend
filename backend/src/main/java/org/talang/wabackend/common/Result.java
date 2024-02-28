package org.talang.wabackend.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;

    public static Result success() {
        return new Result(true, null, null, null);
    }

    public static Result success(Object data) {
        return new Result(true, null, data, null);
    }

    public static Result success(List<?> data) {

        return new Result(true, null, data, (long) data.size());
    }

    public static Result success(List<?> data, Long selectTotal) {
        ListResult listResult = new ListResult();
        listResult.setList(data);
        listResult.setSelectTotal(selectTotal);
        return new Result(true, null, listResult, (long) data.size());
    }

    public static Result fail(String errorMsg) {
        return new Result(false, errorMsg, null, null);
    }

}

