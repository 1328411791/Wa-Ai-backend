package org.talang.wabackend.sd.impl;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.ExtraImageResult;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.sd.MultiSdWebUiConnect;
import org.talang.wabackend.sd.SdDrawFinshHandle;
import org.talang.wabackend.service.SdImageService;
import org.talang.wabackend.service.TaskService;

import java.util.Base64;

@Slf4j
@Component
public class DrawImageComponentImpl implements DrawImageComponent {

    // 如果sayWay = local 注入LocalFileSaveComponentImpl, qiniu 注入QiniuSaveImageComponentImpl
    @Resource
    private ImageComponent imageComponent;

    @Resource
    private TaskService taskService;


    @Autowired
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @Autowired
    private SdImageService sdImageService;

    @Autowired
    private SdDrawFinshHandle sdDrawFinshHandle;


    //@Autowired
    //private SdWebui sdWebui;


    @Async("threadPoolTaskExecutor")
    @Override
    public void text2Image(String taskId, Integer userId, Txt2ImageOptions options) {
        log.info("text2Image taskId:{}", taskId);
        taskService.setStartDrawStatus(taskId);
        SdWebui sdWebui = null;
        try {
            sdWebui = multiSdWebUiConnect.getAvailableSdWebui();

            Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(options);

            byte[] decode = Base64.getDecoder().decode(txt2ImgResult.getImages().get(0));

            String imageId = imageComponent.saveImage(decode, userId);
            sdImageService.saveSdImage(imageId,txt2ImgResult,userId);
            String imageParams = JSONUtil.toJsonStr(txt2ImgResult.getParameters());

            taskService.setFinishDrawStatus(taskId, imageId, imageParams);

            sdDrawFinshHandle.drawFinishHandle(taskId);
        } catch (Exception e) {
            log.error("text2Image error", e);
        } finally {
            // 释放资源
            multiSdWebUiConnect.returnSdWebui(sdWebui);
        }
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void extraImage(String taskId, Integer userId, Txt2ImageOptions txt2ImageOptions
            , ExtraImageOptions extraImageOptions) {
        log.info("extraImage taskId:{}", taskId);
        taskService.setStartDrawStatus(taskId);
        SdWebui sdWebui = null;
        try {
            sdWebui = multiSdWebUiConnect.getAvailableSdWebui();
            Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(txt2ImageOptions);

            extraImageOptions.setImage(txt2ImgResult.getImages().get(0));

            ExtraImageResult extraImageResult = sdWebui.extraImage(extraImageOptions);

            byte[] decode = Base64.getDecoder().decode(extraImageResult.getImage());

            String imageId = imageComponent.saveImage(decode, userId);


            String imageParams = extraImageResult.getHtmlInfo();

            taskService.setFinishDrawStatus(taskId, imageId, imageParams);
        } catch (Exception e) {
            log.error("extraImage error", e);
        } finally {
            // 释放资源
            multiSdWebUiConnect.returnSdWebui(sdWebui);
        }
    }


}
