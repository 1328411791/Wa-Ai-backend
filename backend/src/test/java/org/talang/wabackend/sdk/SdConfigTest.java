package org.talang.wabackend.sdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.results.LatentUpscaleModesResult;
import org.talang.sdk.models.results.SamplersResult;
import org.talang.sdk.models.results.UpscalersResult;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

import java.util.List;

@Slf4j
@SpringBootTest
public class SdConfigTest {

    @Autowired
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @Test
    public void testGetSdModels() {
        SdWebui webui = multiSdWebUiConnect.getAvailableSdWebui();
        List<SamplersResult> sdSamplers = webui.getSdSamplers();
        log.info("sdSamplers: {}", sdSamplers);
        multiSdWebUiConnect.returnSdWebui(webui);
    }

    @Test
    public void testGetUpscalers() {
        SdWebui webui = multiSdWebUiConnect.getAvailableSdWebui();
        List<UpscalersResult> upScalers = webui.getUpScalers();
        log.info("sdSamplers: {}", upScalers);
        multiSdWebUiConnect.returnSdWebui(webui);
    }

    @Test
    public void testGetLatentUpscaleModels() {
        SdWebui webui = multiSdWebUiConnect.getAvailableSdWebui();
        List<LatentUpscaleModesResult> latentUpscaleModes = webui.getLatentUpscaleModes();
        log.info("sdSamplers: {}", latentUpscaleModes);
        multiSdWebUiConnect.returnSdWebui(webui);
    }
}
