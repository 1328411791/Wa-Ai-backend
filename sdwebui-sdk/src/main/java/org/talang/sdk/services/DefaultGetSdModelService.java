package org.talang.sdk.services;

import org.talang.sdk.GetSdModels;
import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.models.results.SdModel;

import java.util.Arrays;
import java.util.List;

public class DefaultGetSdModelService implements GetSdModels {

    private final SdWebuiBeanContainer container;

    public DefaultGetSdModelService(SdWebuiBeanContainer container) {
        this.container = container;
    }

    @Override
    public List<SdModel> getSdModels() {
        return Arrays.asList(this.container.getBean(CommonGetService.class).getData("/sdapi/v1/sd-models", SdModel[].class));
    }

}
