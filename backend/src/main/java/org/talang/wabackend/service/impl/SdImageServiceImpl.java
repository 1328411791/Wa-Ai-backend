package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.constant.SdImageConstant;
import org.talang.wabackend.model.generator.SdImage;
import org.talang.wabackend.model.vo.sdImage.MySdImageVo;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.service.*;
import org.talang.wabackend.mapper.SdImageMapper;
import org.springframework.stereotype.Service;
import org.talang.wabackend.util.SdImageLikeComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.talang.wabackend.util.SdImageLikeComponent.SD_IMAGE_LIKE;

/**
 * @author lihan
 * @description 针对表【sd_image】的数据库操作Service实现
 * @createDate 2024-03-01 01:16:15
 */
@Service
public class SdImageServiceImpl extends ServiceImpl<SdImageMapper, SdImage> implements SdImageService {

    @Resource
    private StaticImageService staticImageService;

    @Resource
    private ModelService modelService;

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TaskService taskService;

    @Resource
    private SdImageLikeComponent sdImageLikeComponent;

    @Override
    public void saveSdImage(String imageId, Txt2ImgResult txt2ImgResult, Integer userId) {
        SdImage sdImage = new SdImage();
        sdImage.setId(imageId);
        sdImage.setUserId(userId);

        String imageInfo = txt2ImgResult.getInfo();
        String imageParams = "";

        // 处理 imageInfo
        try {
            // 将 imageInfo 字符串解析为对象
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(imageInfo);

            // 创建一个新的 ObjectNode 用于存储过滤后的键值对
            ObjectNode filteredNode = mapper.createObjectNode();

            // 遍历原对象
            jsonNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                if (!(key.startsWith("all_") || key.equals("infotexts") || key.equals("styles") || key.equals("is_using_inpainting_conditioning") || key.equals("index_of_first_image"))) {
                    filteredNode.set(key, entry.getValue());
                }
            });

            imageParams = filteredNode.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sdImage.setParams(imageParams);

        String modelName = txt2ImgResult.getParameters().getOverrideSettings().get("sd_model_checkpoint").toString();
        String vaeModelName = txt2ImgResult.getParameters().getOverrideSettings().get("sd_vae").toString();

        sdImage.setStaticImageId(imageId);

        sdImage.setCheckpointModelId(modelService.getModelIdByModelName(modelName));
        sdImage.setVaeModelId(modelService.getModelIdByModelName(vaeModelName));

        // 用户生成的SDImage，如有上传调用此API，记得在这改
        sdImage.setType(SdImageConstant.SDIMAGE_TYPE_GENERATE);

        save(sdImage);
    }

    @Override
    public SdImageVo getImageById(Integer userId, String id) {
        SdImage sdImage = getById(id);
        if (sdImage == null) {
            throw new RuntimeException("不存在Sd图像");
        }
        SdImageVo vo = BeanUtil.toBean(sdImage, SdImageVo.class);

        vo.setImageUrl(staticImageService.getSaticImagePathById(id));
        vo.setUser(userService.getUserVo(sdImage.getUserId()));
        String key = SD_IMAGE_LIKE + sdImage.getId();
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId));
        vo.setIsLiked(isMember);


        return vo;
    }

    @Override
    public Result getMyAllList(String startTimeStamp, String endTimeStamp,
                               boolean myGenerate, boolean myUpload,
                               Integer page, Integer pageSize) {

        // 构建查找条件
        LambdaQueryWrapper<SdImage> wrapper = new LambdaQueryWrapper<>();

        // 日期查询
        Date startTime = new Date((Long.parseLong(startTimeStamp) * 1000));
        Date endTime = new Date((Long.parseLong(endTimeStamp) * 1000));
        wrapper.ge(startTime.getTime() != 0L, SdImage::getCreateTime, startTime)
                .lt(endTime.getTime() != 0L, SdImage::getCreateTime, endTime)
                .orderByDesc(SdImage::getCreateTime);

        // 图片类型
        List<String> typeList = new ArrayList<>();
        if (myGenerate) typeList.add(SdImageConstant.SDIMAGE_TYPE_GENERATE);
        if (myUpload) typeList.add(SdImageConstant.SDIMAGE_TYPE_UPLOAD);
        if (!typeList.isEmpty()) wrapper.in(SdImage::getType, typeList);

        // 获取id
        Long loginId = StpUtil.getLoginIdAsLong();
        String username = userService.getUsernameById(loginId.intValue());
        wrapper.eq(SdImage::getUserId, loginId);

        Page<SdImage> sdImagePage = new Page<>(page, pageSize);
        this.page(sdImagePage, wrapper);

        List<SdImage> images = sdImagePage.getRecords();

        //Vo，填充数据
        List<MySdImageVo> mySdImageVos = BeanUtil.copyToList(images, MySdImageVo.class);
        mySdImageVos.forEach(sdImage -> sdImage.setUserId(loginId).setUserNickName(username).
                        setStatus(taskService.getTaskStatusBySDImageId(sdImage.getId())).
                        setIsLiked(sdImageLikeComponent.isLiked(sdImage.getId(), loginId.intValue()))
                // TODO 查询收藏
        );

        return Result.success(new ListResult(mySdImageVos, (long) mySdImageVos.size()));
    }
}




