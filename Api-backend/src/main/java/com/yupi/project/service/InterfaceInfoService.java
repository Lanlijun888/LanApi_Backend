package com.yupi.project.service;

import com.api.apiCommon.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-05-29 12:27:07
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceinfo, boolean add);

    /**
     * 调用测试接口
     * @param interfaceInfoInvokeRequest 接口信息
     * @param request 用户请求
     * @return 远程接口返回值
     */
    Object invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request);
}
