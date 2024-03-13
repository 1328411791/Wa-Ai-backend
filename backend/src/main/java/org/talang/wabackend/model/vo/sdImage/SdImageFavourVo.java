package org.talang.wabackend.model.vo.sdImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SdImageFavourVo {
    private Boolean favoured;
    private Integer favourNum;
}
