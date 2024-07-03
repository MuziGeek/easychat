package com.muzi.easychat.user.dao;

import com.muzi.easychat.user.domain.entity.Black;
import com.muzi.easychat.user.mapper.BlackMapper;
import com.muzi.easychat.user.service.IBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author muzi
 * @since 2023-09-10
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> implements IBlackService {

}
