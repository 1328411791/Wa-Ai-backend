package org.talang.wabackend.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.talang.sdk.models.results.LatentUpscaleModesResult;
import org.talang.sdk.models.results.UpscalersResult;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HiresUpscalerResult {

    List<UpscalersResult> upscalersResultList;

    List<LatentUpscaleModesResult> latentUpscaleModesResultList;
}
