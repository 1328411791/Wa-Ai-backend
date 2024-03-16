package org.talang.wabackend.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.results.LatentUpscaleModesResult;
import org.talang.sdk.models.results.SamplersResult;
import org.talang.sdk.models.results.UpscalersResult;
import org.talang.wabackend.model.result.HiresUpscalerResult;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

import java.util.List;

@Slf4j
@Component
public class SdModelComponent {

    @Resource
    private MultiSdWebUiConnect multiSdWebUiConnect;


    public List<SamplersResult> getSdSampler() {
        // TODO: 待添加缓存
        SdWebui webui = null;
        List<SamplersResult> sdSamplers = null;
        try {
            webui = multiSdWebUiConnect.getAvailableSdWebui();
            sdSamplers = webui.getSdSamplers();
        }catch (Exception e){
            log.error("getSdSampler error", e);
        }finally {
            multiSdWebUiConnect.returnSdWebui(webui);
        }

        return sdSamplers;

    }

    public HiresUpscalerResult getHiresUpscaler() {
        // TODO: 待添加缓存
        SdWebui webui = null;
        List<UpscalersResult> sdSamplers = null;
        List<LatentUpscaleModesResult> latentUpscaleModes = null;
        try {
            webui = multiSdWebUiConnect.getAvailableSdWebui();
            sdSamplers = webui.getUpScalers();
            latentUpscaleModes = webui.getLatentUpscaleModes();
        }catch (Exception e){
            log.error("getHiresUpscaler error", e);
        }finally {
            multiSdWebUiConnect.returnSdWebui(webui);
        }

        return new HiresUpscalerResult(sdSamplers, latentUpscaleModes);
    }

    public List<UpscalersResult> getExtraUpscaler() {
        // TODO: 待添加缓存
        SdWebui webui = null;
        List<UpscalersResult> sdSamplers = null;
        try {
            webui = multiSdWebUiConnect.getAvailableSdWebui();
            sdSamplers = webui.getUpScalers();
        }catch (Exception e){
            log.error("getExtraUpscaler error", e);
        }finally {
            multiSdWebUiConnect.returnSdWebui(webui);
        }
        return sdSamplers;
    }
}
