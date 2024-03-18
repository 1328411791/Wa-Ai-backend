package org.talang.wabackend.sd.impl;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.ExtraImageResult;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.exception.TaskFailException;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.ImageComponent;
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
    private SdImageService sdImageService;

    @Autowired
    private SdDrawFinshHandle sdDrawFinshHandle;


    //@Autowired
    //private SdWebui sdWebui;

    @Override
    public void text2Image(String taskId, Integer userId, String options, SdWebui sdWebui) throws TaskFailException {
        log.info("text2Image taskId:{}", taskId);
        // 生成任务
        Txt2ImageOptions txt2ImageOptions = JSONUtil.toBean(options, Txt2ImageOptions.class);
        taskService.setStartDrawStatus(taskId);

        // 等待任务跑完
        Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(txt2ImageOptions);

        // 判断任务是都生成成功
        if(txt2ImgResult.getImages().isEmpty()){
            throw new TaskFailException(taskId, "txt2ImgResult.getImages() == null");
        }

        // 生成成功
        byte[] decode = Base64.getDecoder().decode(txt2ImgResult.getImages().get(0));

        // 上传图片到OSS
        String imageId = imageComponent.saveImage(decode, userId);
        // 保存到库
        sdImageService.saveSdImage(imageId,txt2ImgResult,userId);

        // 设置状态
        taskService.setFinishDrawStatus(taskId, imageId);

        // 完成后处理
        sdDrawFinshHandle.drawFinishHandle(taskId);

    }

    @Override
    public void extraImage(String taskId, Integer userId, String options, SdWebui sdWebui) throws TaskFailException {
        log.info("extraImage taskId:{}", taskId);
        ExtraImageOptions extraImageOptions = JSONUtil.toBean(options, ExtraImageOptions.class);
        taskService.setStartDrawStatus(taskId);

        ExtraImageResult extraImageResult = sdWebui.extraImage(extraImageOptions);

        if (extraImageResult.getImage().isEmpty()) {
            throw new TaskFailException(taskId, "extraImageResult.getImage() == null");
        }

        byte[] decode = Base64.getDecoder().decode(extraImageResult.getImage());

        String imageId = imageComponent.saveImage(decode, userId);

        taskService.setFinishDrawStatus(taskId, imageId);
    }
}
