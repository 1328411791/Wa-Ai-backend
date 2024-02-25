package org.talang.wabackend.model.vo.model;

import lombok.Data;
import org.talang.wabackend.model.generator.Model;

import java.util.List;

@Data
public class SelectSdModelVo {

    private List<Model> models;

    private Long selectCount;
}
