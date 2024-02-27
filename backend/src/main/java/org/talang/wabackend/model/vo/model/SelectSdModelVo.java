package org.talang.wabackend.model.vo.model;

import lombok.Data;

import java.util.List;

@Data
public class SelectSdModelVo {

    private List<SdModelVo> models;

    private Long selectCount;
}
