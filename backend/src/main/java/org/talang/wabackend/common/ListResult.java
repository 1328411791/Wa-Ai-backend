package org.talang.wabackend.common;

import lombok.Data;

import java.util.List;

@Data
public class ListResult {

    private List<?> data;

    private Long selectTotal;
}