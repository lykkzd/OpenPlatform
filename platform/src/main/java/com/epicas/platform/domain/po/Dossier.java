package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 档案表
 * @author liuyang
 * @date 2023年10月07日 16:43
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("x_wvid_dossier_00_dossier")
public class Dossier {

    /**
     * 主键，档案ID
     */
    @TableId(value = "dossierId", type = IdType.AUTO)
    private Long dossierId;

    /**
     * 车辆ID
     */
    @TableField("carId")
    private Long carId;

    /**
     * 站点ID
     */
    @TableField("orgId")
    private Long orgId;


    /**
     * 保养计划ID
     */
    @TableField("planId")
    private Long planId;

    /**
     * 检测日期 2021-05-15
     */
    @TableField("checkDay")
    private String checkDay;

    /**
     * 跟车SA
     */
    @TableField("saId")
    private Long saId;

    /**
     * 车牌照片id
     */
    @TableField("carNumberImageId")
    private Long carNumberImageId;

    /**
     * 里程
     */
    @TableField("mile")
    private Long mile;

    /**
     * 进入场景时间
     */
    @TableField("inputTime")
    private Long inputTime;

    /**
     * 方案状态 0待处理 10处理中 1000处理完成
     */
    @TableField("workStatus")
    private Integer workStatus;

    /**
     * 微信分享超链接
     */
    @TableField("shareUrl")
    private String shareUrl;

    /**
     * 微信分享过期时间
     */
    @TableField("expireTime")
    private Long expireTime;

    /**
     * 报价状态 0不需要报价 1需要报价未报价 100完成报价
     */
    @TableField("quoteStatus")
    private Integer quoteStatus;

    /**
     * 报价确认 0不需要 1等待 100确认完成
     */
    @TableField("quoteMakeSure")
    private Integer quoteMakeSure;

    /**
     * 1正常版本 2重庆宝马版本
     */
    @TableField("showVersion")
    private Integer showVersion;

    /**
     * 车主是否在现场 0未录入 1在现场 2不在现场
     */
    @TableField("inSceneStatus")
    private Integer inSceneStatus;

    /**
     * 报价状态 0不需要报价 100需要报价 200需要重新报价  1000报价完成
     */
    @TableField("quoteV2Status")
    private Integer quoteV2Status;

    /**
     * 商谈状态 0默认 100SA打开过 1000 SA 商谈点击完成 1010 登记触发完成
     */
    @TableField("communicateStatus")
    private Integer communicateStatus;

    /**
     * 保养类型0未选择 1保养 2事故
     */
    @TableField("maintenanceType")
    private Integer maintenanceType;

    /**
     * 非必填 油箱余量 范围0-100
     */
    @TableField("powerLevel")
    private Integer powerLevel;

}
