package com.yupi.project.mapper;

import com.api.apiCommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author HUAWEI
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-05-31 21:26:55
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    /**
     * 统计分析接口调用情况
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

    /**
     * 统计调用接口次数最多的人
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> topHumanInvokeInterfaceInfo(int limit);
}




