package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.constant.CommentConstant;
import org.talang.wabackend.mapper.CommentMapper;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.comment.CommentAddDto;
import org.talang.wabackend.model.generator.Comment;
import org.talang.wabackend.model.vo.comment.CommentVo;
import org.talang.wabackend.model.vo.comment.ToCommentVo;
import org.talang.wabackend.service.CommentService;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Polister
 * @since 2024-03-07 19:08:48
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result addComment(CommentAddDto commentAddDto) {

        // 先检查登录，获取登录的id
        if (!StpUtil.isLogin()) {
            return Result.fail("当前未登录");
        }

        long loginId = StpUtil.getLoginIdAsLong();

        // 拷贝到实体类
        Comment comment = BeanUtil.toBean(commentAddDto, Comment.class);
        comment.setCommentUserId(loginId);

        // 保存到数据库
        this.save(comment);

        return Result.success(comment.getId());
    }

    @Override
    public Result deleteComment(Long commentId) {

        // 先检查登录，获取登录的id
        if (!StpUtil.isLogin()) {
            return Result.fail("当前未登录");
        }

        long loginId = StpUtil.getLoginIdAsLong();

        // 检查是否有权限删除
        Comment comment = this.getById(commentId);
        if (!comment.getCommentUserId().equals(loginId)) {
            return Result.fail("没有删除权限");
        }

        // 执行删除
        this.removeById(commentId);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, commentId);
        this.remove(wrapper);

        return Result.success();
    }

    @Override
    public Result getCommentsByPage(String type, Long id, Integer pageNum, Integer pageSize) {

        // 构造分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        // 构建查询条件：先查询根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, CommentConstant.ROOT_COMMENT)
                .eq(Comment::getType, type)
                .eq(Comment::getArticleId, id);
        page(page, wrapper);

        long total = page.getTotal();
        // 优化成Vo
        List<CommentVo> commentVos = BeanUtil.copyToList(page.getRecords(), CommentVo.class);

        // 补全信息
        commentVos.forEach(comment ->
            comment.setCommentUserName(userMapper.selectById(comment.getCommentUserId()).getNickName())
                    .setReply(buildTreeOfComments(comment))
                    .setNumReply(comment.getReply().size())
                // TODO 获取点赞数
        );

        return Result.success(new ListResult(commentVos, total));
    }

    private List<ToCommentVo> buildTreeOfComments(CommentVo commentVo) {
        // 获取子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, commentVo.getId());
        List<Comment> comments = this.getBaseMapper().selectList(wrapper);

        // Vo优化
        List<ToCommentVo> toCommentVos = BeanUtil.copyToList(comments, ToCommentVo.class);
        // 补全Vo信息
        toCommentVos.forEach(comment ->
                comment.setCommentUserName(userMapper.selectById(comment.getCommentUserId()).getNickName())
                        .setToCommentUserName(userMapper.selectById(comment.getToCommentUserId()).getNickName())
        );

        return toCommentVos;
    }

}
