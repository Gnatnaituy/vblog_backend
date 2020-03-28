package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.account.document.UserDoc;
import com.hasaker.account.entity.Block;
import com.hasaker.account.entity.User;
import com.hasaker.account.exception.enums.BlockExceptionEnums;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.BlockMapper;
import com.hasaker.account.service.BlockService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestBlockVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/2 10:47
 * @description BlockServiceImpl
 */
@Service
public class BlockServiceImpl extends BaseServiceImpl<BlockMapper, Block> implements BlockService {

    @Autowired
    private UserService userService;
    @Autowired
    private EsService esService;

    /**
     * 屏蔽用户
     * @param blockVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockUser(RequestBlockVo blockVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(blockVo.getUserId());
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(blockVo.getBlockUserId());

        User user = userService.getById(blockVo.getUserId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(user);
        User blockUser = userService.getById(blockVo.getBlockUserId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(blockUser);

        Block block = Convert.convert(Block.class, blockVo);
        this.save(block);

        // update user's blocks in es
        UserDoc userDoc = esService.getById(block.getUserId(), UserDoc.class);
        userDoc.getBlocks().add(block.getBlockUserId());
        esService.update(userDoc.getId(), UserDoc.class, new Pair<>(UserDoc.BLOCKS, userDoc.getBlocks()));
    }

    /**
     * 取消屏蔽用户
     * @param unblockVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unblockUser(RequestBlockVo unblockVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(unblockVo.getUserId());
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(unblockVo.getBlockUserId());

        QueryWrapper<Block> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Block.USER_ID, unblockVo.getUserId());
        queryWrapper.eq(Block.BLOCK_USER_ID, unblockVo.getBlockUserId());
        Block block = this.getOne(queryWrapper);
        BlockExceptionEnums.BLOCK_NOT_EXISTS.assertNotEmpty(block);
        this.removeById(block.getId());

        // update user's blocks in es
        UserDoc userDoc = esService.getById(block.getUserId(), UserDoc.class);
        userDoc.getBlocks().remove(block.getBlockUserId());
        esService.update(userDoc.getId(), UserDoc.class, new Pair<>(UserDoc.BLOCKS, userDoc.getBlocks()));
    }
}
