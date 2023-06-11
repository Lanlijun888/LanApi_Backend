package com.yupi.project.service.impl;


import com.alibaba.nacos.shaded.com.google.gson.JsonSyntaxException;
import com.api.apiCommon.model.entity.InterfaceInfo;
import com.api.apiCommon.model.entity.User;
import com.api.client.ApiClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.yupi.project.model.enums.InterfaceInfoEnum;
import com.yupi.project.service.InterfaceInfoService;

import com.yupi.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2023-05-29 12:27:07
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Resource
    private UserService userService;
    @Resource
    private ApiClient apiClient;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceinfo, boolean add) {
        if (interfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceinfo.getName();

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }

    @Override
    public Object invokeInterfaceInfo(InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {
        ImmutablePair<Integer,String> res = null;
        if(interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        Long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        //判断接口是否存在
        InterfaceInfo oldInterfaceInfo = this.getById(id);
        if(oldInterfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        //判断接口是否开启
        if(oldInterfaceInfo.getStatus() != InterfaceInfoEnum.ONLINE.getValue()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口已关闭");
        }

        User loginUser = userService.getLoginUser(request);
        try {
            if(oldInterfaceInfo.getUrl().contains("randomMessage")){
                res = apiClient.randomMessage(userRequestParams);
            }
            if(oldInterfaceInfo.getUrl().contains("/api/name/user")){
                res = apiClient.postNameByBody(userRequestParams);
            }
            if(oldInterfaceInfo.getUrl().contains("randomACGPictures")){
                res = apiClient.randomACGPictures();
            }
            if(oldInterfaceInfo.getUrl().contains("randomIan")){
                res = apiClient.randomIan();
            }
            if(oldInterfaceInfo.getUrl().contains("randomJoke")){
                res = apiClient.randomJoke();
            }
            if(oldInterfaceInfo.getUrl().contains("randomImage")){
                res = apiClient.randomImage();
            }
            if(oldInterfaceInfo.getUrl().contains("getQQImage")){
                res = apiClient.getQQImage(userRequestParams);
            }
            if(oldInterfaceInfo.getUrl().contains("getWebTitle")){
                res = apiClient.getWebTitle(userRequestParams);
            }
            if(oldInterfaceInfo.getUrl().contains("translate")){
                res = apiClient.translate(userRequestParams);
            }

        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不为Json格式");
        }

        if(res == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口请求错误，请稍后再试");
        }
        if(res.getLeft() != 200){
            throw new BusinessException(res.getLeft(),res.getRight());
        }
        return res.getRight();
    }
}




