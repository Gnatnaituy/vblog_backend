package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.account.entity.Block;
import com.hasaker.account.exception.enums.BlockExceptionEnums;
import com.hasaker.account.mapper.BlockMapper;
import com.hasaker.account.service.BlockService;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.utils.IdUtils;
import com.hasaker.vo.account.request.RequestBlockVo;
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

    /**
     * 屏蔽用户
     * @param blockVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean blockUser(RequestBlockVo blockVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(blockVo.getUserId());
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(blockVo.getBlockUserId());

        Block block = Convert.convert(Block.class, blockVo);
        block.setId(IdUtils.nextId());

        return this.save(block);
    }

    /**
     * 取消屏蔽用户
     * @param unblockVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unblockUser(RequestBlockVo unblockVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(unblockVo.getUserId());
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(unblockVo.getBlockUserId());

        QueryWrapper<Block> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Block.USER_ID, unblockVo.getUserId());
        queryWrapper.eq(Block.BLOCK_USER_ID, unblockVo.getBlockUserId());
        queryWrapper.eq(Consts.IS_DELETED, Consts.NO);
        Block block = this.getOne(queryWrapper);
        BlockExceptionEnums.BLOCK_NOT_EXISTS.assertNotEmpty(block);

        block.setIsDeleted(Consts.YES);

        return this.updateById(block);
    }
}
