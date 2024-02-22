package org.talang.wabackend.sd.impl;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.ExtraImageResult;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.sd.SdThreadPool;
import org.talang.wabackend.service.TaskService;

import java.util.Base64;

@Component
public class DrawImageComponentImpl implements DrawImageComponent {

    // 如果sayWay = local 注入LocalFileSaveComponentImpl, qiniu 注入QiniuSaveImageComponentImpl
    @Value("${sdwebui.image.save-way}")
    private String saveWay;

    @Resource
    private ImageComponent imageComponent;

    @Resource
    private TaskService taskService;

    @Resource
    private SdThreadPool sdThreadPool;


    @Override
    public String text2Image(Txt2ImageOptions options, SdWebui sdWebui) {

        Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(options);

        byte[] decode = Base64.getDecoder().decode(txt2ImgResult.getImages().get(0));

        String imagePath = imageComponent.saveImage(decode);

        return switch (saveWay) {

            case "local" -> "file://" + imagePath;

            case "qiniu" -> "http://qiniu.com/" + imagePath;

            default -> throw new RuntimeException("不支持的保存方式");
        };
    }

    @Override
    public String extraImage(Txt2ImageOptions txt2ImageOptions
            , ExtraImageOptions extraImageOptions, SdWebui sdWebui) {
        Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(txt2ImageOptions);

        extraImageOptions.setImage(txt2ImgResult.getImages().get(0));

        ExtraImageResult extraImageResult = sdWebui.extraImage(extraImageOptions);

        byte[] decode = Base64.getDecoder().decode(extraImageResult.getImage());

        String imagePath = imageComponent.saveImage(decode);

        return switch (saveWay) {

            case "local" -> "file://" + imagePath;

            case "qiniu" -> "http://qiniu.com/" + imagePath;

            default -> throw new RuntimeException("不支持的保存方式");
        };
    }

    @Override
    public boolean threadDraw(SdWebui sdWebui, String taskId) {
        Task task = taskService.getById(taskId);
        taskService.setStartDrawStatus(taskId);
        switch (task.getType()) {
            case "txt2image":
                this.text2Image(JSONUtil.toBean(task.getTxt2image_options(),
                        Txt2ImageOptions.class), sdWebui);
            case "extraimage":
                this.extraImage(JSONUtil.toBean(task.getTxt2image_options(),
                        Txt2ImageOptions.class), JSONUtil.toBean(task.getExtraimage_options(),
                        ExtraImageOptions.class), sdWebui);
        }
        taskService.setFinishDrawStatus(taskId, "");

        return true;
    }





}
