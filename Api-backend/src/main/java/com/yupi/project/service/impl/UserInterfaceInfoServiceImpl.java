package com.yupi.project.service.impl;

import com.api.apiCommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author HUAWEI
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-05-31 21:26:55
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余接口调用次数不能小于0");
        }
    }
    @Override
    public Boolean invokeCount(long interfaceId, long userId) {
        if(interfaceId <= 0 || userId <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
        }
        //根据接口和用户查询请求记录，包括总次数，和剩余调用次数（重点）
        LambdaQueryWrapper<UserInterfaceInfo> lqw = new LambdaQueryWrapper();
        lqw.eq(UserInterfaceInfo::getInterfaceId,interfaceId);
        lqw.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo info = this.getOne(lqw);
        //如果剩余次数为0，出错
        if(info.getLeftNum() == 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口调用剩余次数为0");
        }
        //更新调用记录，totalNum+1,leftNum-1
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceId",interfaceId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("totalNum = totalNum + 1,leftNum = leftNum -1");
        return this.update(updateWrapper);
    }
}




