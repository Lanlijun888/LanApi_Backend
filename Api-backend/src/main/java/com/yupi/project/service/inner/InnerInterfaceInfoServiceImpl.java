package com.yupi.project.service.inner;

import com.api.apiCommon.model.entity.InterfaceInfo;
import com.api.apiCommon.service.InnerInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
* @author HUAWEI
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-05-29 12:27:07
*/
@DubboService
public class InnerInterfaceInfoServiceImpl  implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if(StringUtils.isAnyBlank(path,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //查询数据库中是否存在该接口
        LambdaQueryWrapper<InterfaceInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InterfaceInfo::getUrl,path);
        lqw.eq(InterfaceInfo::getMethod,method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(lqw);
        return interfaceInfo;
    }
}
