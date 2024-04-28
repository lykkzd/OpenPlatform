package com.epicas.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.epicas.platform.domain.po.OrgInCarDay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyang
 * @date 2023年10月07日 17:34
 */
@Mapper
public interface OrgInCarDayMapper extends BaseMapper<OrgInCarDay> {

//    /**
//     * 批量插入
//     * @param orgInCarDayList
//     */
//    @Insert({
//            "INSERT INTO org_incar_day (orgId, dayIndex, carCount, oilFilterBase, updateType, updateUser, updateTime)",
//            "VALUES",
//            "<foreach collection='orgInCarDayList' item='orgInCarDay' separator=','>",
//            "(",
//            "#{orgInCarDay.orgId},",
//            "#{orgInCarDay.dayIndex},",
//            "#{orgInCarDay.carCount},",
//            "#{orgInCarDay.oilFilterBase},",
//            "#{orgInCarDay.updateType},",
//            "#{orgInCarDay.updateUser},",
//            "#{orgInCarDay.updateTime}",
//            ")",
//            "</foreach>"
//    })
//    void insertBatch(@Param("orgInCarDayList") List<OrgInCarDay> orgInCarDayList);
}

