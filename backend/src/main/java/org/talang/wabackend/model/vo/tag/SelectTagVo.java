package org.talang.wabackend.model.vo.tag;

import lombok.Data;
import org.talang.wabackend.model.generator.ImageTag;

import java.util.List;

@Data
public class SelectTagVo {

    private List<ImageTag> imageTags;

    private Long selectCount;
}
