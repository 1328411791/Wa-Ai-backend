package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.mapper.*;
import org.talang.wabackend.model.dto.model.ModelFavoriteDto;
import org.talang.wabackend.model.generator.*;
import org.talang.wabackend.model.vo.model.ModelFavoriteVo;
import org.talang.wabackend.service.ModelFavoriteService;
import org.talang.wabackend.util.SdModelLikeComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (ModelFavoriteVo)表服务实现类
 *
 * @author ECAMT
 * @since 2024-03-13 15:38:43
 */
@Service
public class ModelFavoriteServiceImpl extends ServiceImpl<ModelFavoriteMapper, ModelFavorite> implements ModelFavoriteService {
    @Autowired
    ModelFavoriteMapper modelFavoriteMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SdModelLikeComponent sdModelLikeComponent;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    TaskMapper taskMapper;

    @Override
    public boolean addOrCancelFavoriteModel(Integer modelId) {
        Integer userId = StpUtil.getLoginIdAsInt();

        LambdaQueryWrapper<ModelFavorite> judgeWrapper = new LambdaQueryWrapper<>();

        judgeWrapper.eq(ModelFavorite::getModelId, modelId)
                .eq(ModelFavorite::getUserId, userId);

        Integer ifFavorite = modelFavoriteMapper.ifFavorite(modelId, userId);//是否有收藏
        ModelFavorite modelFavorite = modelFavoriteMapper.selectOne(judgeWrapper);//是否取消了收藏

        if (ifFavorite != 0 && modelFavorite == null) { //有,但取消了->再收藏
            modelFavoriteMapper.recoverFavorite(modelId, userId);
            return true;
        } else if (modelFavorite == null) { //无->添加记录,收藏
            modelFavorite = new ModelFavorite();
            modelFavorite.setModelId(modelId);
            modelFavorite.setUserId(userId);
            modelFavoriteMapper.insert(modelFavorite);
            return true;
        } else {
            modelFavoriteMapper.delete(judgeWrapper);
            return false;
        }
    }

    @Override
    public ListResult getMyFavoriteModel(ModelFavoriteDto modelFavoriteDto) {
        LambdaQueryWrapper<ModelFavorite> favoriteModelWrapper = new LambdaQueryWrapper<>();
        Integer userId = StpUtil.getLoginIdAsInt();
        favoriteModelWrapper.eq(ModelFavorite::getUserId, userId);

        //日期、分页查MF表获取到收藏模型
        Date startTime = modelFavoriteDto.getStartTimestamp();
        Date endTime = modelFavoriteDto.getEndTimestamp();
        this.wrapperDate(favoriteModelWrapper, startTime, endTime);

        Integer page = modelFavoriteDto.getPage();
        Integer pageSize = modelFavoriteDto.getPageSize();
        List<ModelFavorite> modelFavorites = this.pageExecution(favoriteModelWrapper, page, pageSize).getRecords();

        //获得modelId
        List<Integer> modelIdList = new ArrayList<>();
        for (ModelFavorite mf : modelFavorites) {
            modelIdList.add(mf.getModelId());
        }

        //在model表查，type,title限制
        LambdaQueryWrapper<Model> modelWrapper = new LambdaQueryWrapper<>();
        //type
        modelWrapper.eq(Model::getType, modelFavoriteDto.getType());
        //只取收藏的
        modelWrapper.in(Model::getId, modelIdList);
        //标题
        String title = modelFavoriteDto.getSearchQuery();
        this.wrapperTitle(modelWrapper, title);

        List<ModelFavoriteVo> modelFavoriteVos = this.listHandle(modelMapper.selectList(modelWrapper), userId);

        ListResult listResult = new ListResult();
        listResult.setList(modelFavoriteVos);
        listResult.setSelectTotal((long) modelFavoriteVos.size());

        return listResult;
    }

    private void wrapperDate(LambdaQueryWrapper<ModelFavorite> favoriteModelWrapper, Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            favoriteModelWrapper.ge(ModelFavorite::getUpdateTime, startTime)
                    .le(ModelFavorite::getUpdateTime, endTime);
        } else if (startTime != null) {
            favoriteModelWrapper.ge(ModelFavorite::getUpdateTime, startTime);
        } else if (endTime != null) {
            favoriteModelWrapper.le(ModelFavorite::getUpdateTime, endTime);
        }
    }

    private void wrapperTitle(LambdaQueryWrapper<Model> modelWrapper, String title) {
        if (title != null && !title.isEmpty()) {
            modelWrapper.like(Model::getTitle, title);
        }
    }

    private Page<ModelFavorite> pageExecution(LambdaQueryWrapper<ModelFavorite> favoriteModelWrapper, Integer page, Integer pageSize) {
        Page<ModelFavorite> modelFavoritePage = new Page<>(page, pageSize);
        return modelFavoriteMapper.selectPage(modelFavoritePage, favoriteModelWrapper);
    }

    private List<ModelFavoriteVo> listHandle(List<Model> modelList, Integer userId) {
        return modelList.stream().map(model -> {
            ModelFavoriteVo modelFavoriteVo = BeanUtil.toBean(model, ModelFavoriteVo.class);
            modelFavoriteVo.setNumLiked(model.getLiked());
            modelFavoriteVo.setFileName(model.getFilename());

            //authorNickName
            User user = userMapper.selectById(model.getAuthorId());
            modelFavoriteVo.setAuthorNickName(user.getNickName());

            //numRun
            //获取本次model标题用wrapper设置like配合标题查询
            LambdaQueryWrapper<Task> taskWrapper = new LambdaQueryWrapper<>();
            taskWrapper.eq(Task::getStatus, 2);
            ArrayList<Integer> statusList = new ArrayList<>();
            statusList.add(0);
            statusList.add(1);
            taskWrapper.in(Task::getStatus, statusList);
            taskWrapper.like(Task::getTxt2imageOptions, model.getTitle());
            modelFavoriteVo.setNumRuns(taskMapper.selectCount(taskWrapper));

            //numFavours
            LambdaQueryWrapper<ModelFavorite> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ModelFavorite::getModelId, model.getId());
            modelFavoriteVo.setNumFavours(modelFavoriteMapper.selectCount(wrapper));
            //is_like
            modelFavoriteVo.setLike(sdModelLikeComponent.isLiked(model.getId(), userId));
            //numComment
            LambdaQueryWrapper<Comment> wrapperComment = new LambdaQueryWrapper<>();
            wrapperComment.eq(Comment::getType, "1");
            wrapperComment.eq(Comment::getArticleId, model.getId());
            modelFavoriteVo.setNumComment(commentMapper.selectCount(wrapperComment));

            return modelFavoriteVo;
        }).toList();
    }
}

