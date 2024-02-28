package org.talang.sdk;

import org.talang.sdk.models.results.LatentUpscaleModesResult;
import org.talang.sdk.models.results.SamplersResult;
import org.talang.sdk.models.results.SdModel;
import org.talang.sdk.models.results.UpscalersResult;

import java.util.List;

public interface GetSdModels {

  List<SdModel> getSdModels();

  List<SamplersResult> getSdSamplers();

  List<UpscalersResult> getUpScalers();

  List<LatentUpscaleModesResult> getLatentUpscaleModes();

}
