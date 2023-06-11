package com.yupi.project.service.inner;

import com.api.apiCommon.model.entity.UserInterfaceInfo;
import com.api.apiCommon.service.InnerUserInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceId, long userId) {

        return userInterfaceInfoService.invokeCount(interfaceId,userId);
    }

    @Override
    public UserInterfaceInfo getUserInterfaceIfo(long interfaceInfoId, long userId) {
        LambdaQueryWrapper<UserInterfaceInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInterfaceInfo::getInterfaceId,interfaceInfoId);
        lqw.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(lqw);
        return userInterfaceInfo;
    }
}
