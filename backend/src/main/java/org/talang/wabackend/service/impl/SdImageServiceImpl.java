package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.constant.SdImageConstant;
import org.talang.wabackend.model.generator.SdImage;
import org.talang.wabackend.model.generator.SdImageUserFavour;
import org.talang.wabackend.model.vo.sdImage.MySdImageVo;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.service.*;
import org.talang.wabackend.mapper.SdImageMapper;
import org.springframework.stereotype.Service;
import org.talang.wabackend.util.SdImageLikeComponent;

import java.util.*;

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

    @Resource
    private ImageComponent imageComponent;

    @Resource
    private SdImageUserFavourService sdImageUserFavourService;

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

        vo.setImageUrl(staticImageService.getStaticImagePathById(id));
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

        LambdaQueryWrapper<SdImage> wrapper = buildDateWrapper(startTimeStamp, endTimeStamp);

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

        long total = sdImagePage.getTotal();
        List<MySdImageVo> mySdImageVos = this.buildMySdImageVoList(sdImagePage.getRecords(),
                loginId.intValue(), username);

        return Result.success(new ListResult(mySdImageVos, total));
    }

    @Override
    public Result upLoadSdImageByUser(MultipartFile img) {

        // 获取登录的账户
        int loginId = StpUtil.getLoginIdAsInt();
        String staticId = null;
        try {
            staticId = imageComponent.saveImage(img.getBytes(), loginId);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }

        if (Objects.nonNull(staticId)) {
            SdImage sdImage = new SdImage();
            sdImage.setId(staticId);
            sdImage.setType(SdImageConstant.SDIMAGE_TYPE_UPLOAD);
            sdImage.setUserId(loginId);
            sdImage.setStaticImageId(staticId);

            this.save(sdImage);
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("SdImageId", sdImage.getId());
            return Result.success(resultMap);
        }

        return Result.fail("保存图片失败");
    }

    @Override
    public Result deleteSdImageById(String id) {
        // 先获取静态Image对象
        SdImage image = this.getById(id);
        // 验证是否图片的user
        Integer loginId = StpUtil.getLoginIdAsInt();
        if (Objects.isNull(image) || !loginId.equals(image.getUserId())) {
            return Result.fail("删除失败");
        }
        // 删除StaticImage对象
        if (StringUtils.hasText(image.getStaticImageId())) {
            imageComponent.removeImage(id);
        }
        this.removeById(id);
        return Result.success();
    }

    @Override
    public Result getFavourImage(String startTimeStamp, String endTimeStamp,
                                 Integer page, Integer pageSize) {

        // 获取登录信息
        Integer loginId = StpUtil.getLoginIdAsInt();
        String username = userService.getUsernameById(loginId);

        // 从收藏关联表中获取用户关联的图片ID
        LambdaQueryWrapper<SdImageUserFavour> sdImageUserFavourWrapper = new LambdaQueryWrapper<>();
        sdImageUserFavourWrapper.eq(SdImageUserFavour::getUserId, loginId);
        List<String> userFavourIds = sdImageUserFavourService.list(sdImageUserFavourWrapper)
                .stream().map(SdImageUserFavour::getSdImageId)
                .toList();

        // 没有就不用查了，会导致MBP SQL语法错误
        if(userFavourIds.isEmpty()) {
            return Result.success(new ListResult(userFavourIds, 0L));
        }

        LambdaQueryWrapper<SdImage> wrapper = buildDateWrapper(startTimeStamp, endTimeStamp);
        wrapper.in(SdImage::getId, userFavourIds);
        Page<SdImage> sdImagePage = new Page<>(page, pageSize);
        this.page(sdImagePage, wrapper);

        long total = sdImagePage.getTotal();
        List<MySdImageVo> mySdImageVos = this.buildMySdImageVoList(sdImagePage.getRecords(),
                                                            loginId, username);

        return Result.success(new ListResult(mySdImageVos, total));
    }

    private LambdaQueryWrapper<SdImage> buildDateWrapper(String startTimeStamp,
                                                         String endTimeStamp) {

        // 构建查找条件
        LambdaQueryWrapper<SdImage> wrapper = new LambdaQueryWrapper<>();

        // 日期查询
        Date startTime = new Date((Long.parseLong(startTimeStamp) * 1000));
        Date endTime = new Date((Long.parseLong(endTimeStamp) * 1000));
        wrapper.ge(startTime.getTime() != 0L, SdImage::getCreateTime, startTime)
                .lt(endTime.getTime() != 0L, SdImage::getCreateTime, endTime)
                .orderByDesc(SdImage::getCreateTime);

        return wrapper;
    }

    private List<MySdImageVo> buildMySdImageVoList(List<SdImage> list, Integer loginId, String username) {

        //Vo，填充数据
        List<MySdImageVo> mySdImageVos = BeanUtil.copyToList(list, MySdImageVo.class);
        mySdImageVos.forEach(sdImage ->
                sdImage.setUserNickName(loginId.equals(sdImage.getUserId()) ?
                                username : userService.getUserNickNameById(sdImage.getUserId().intValue()))
                .setStatus(taskService.getTaskStatusBySDImageId(sdImage.getId()))
                .setIsLiked(sdImageLikeComponent.isLiked(sdImage.getId(), loginId))
                .setIsFavours(sdImageUserFavourService.getIsFavour(sdImage.getId(), loginId))
        );

        return mySdImageVos;
    }
}




