package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 检测报告表
 * @author liuyang
 * @date 2023年10月07日 17:27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("x_wvid_dossier_01_dossier_position")
public class DossierPosition {
    /**
     * 档案ID
     */
    @TableField("dossierId")
    private Integer dossierId;

    /**
     * 部位ID
     */
    @TableField("positionId")
    private Integer positionId;

    /**
     * 诊断结果
     */
    @TableField("diagnosis")
    private String diagnosis;

    /**
     * 0不需要更换 10需要更换
     */
    @TableField("tag")
    private Integer tag;


    /**
     * 第二版状态0忽略1 00正常 110下次再检测 120 紧急本次处理
     */
    @TableField("positionCheckTag")
    private Integer positionCheckTag;

    /**
     * 第二版磨损度0忽略1-99
     */
    @TableField("durability")
    private Integer durability;

    /**
     * 技师ID
     */
    @TableField("workUserId")
    private Long workUserId;

    /**
     * 数据上传时间
     */
    @TableField("submitTime")
    private Long submitTime;

    /**
     * 报价确认0不需要确认1需要确认未确认100确认正常101确认替换
     */
    @TableField("quoteMakeSure")
    private Integer quoteMakeSure;

    /**
     * 换件登记 0未处理 800 换 400 不换
     */
    @TableField("usFeedback")
    private Integer usFeedback;

    /**
     * 1 预检工程师登记 2 sa登记
     */
    private Integer whoFeed;

    /**
     * 1、价格贵，2、未报价，3、维修厂，4、赶时间，5、下次做，6、其他。
     */
    @TableField("reason")
    private Integer reason;

    /**
     * 报价状态0没有报价信息1有报价信息
     */
    @TableField("quoteV2Status")
    private Integer quoteV2Status;

    /**
     * 成交数量 默认1
     */
    @TableField("dealFeedback")
    private Integer dealFeedback;

    /**
     * 根据技师岗位区分部位类别  1预检  2车间
     */
    @TableField("category")
    private Integer category;

    /**
     * 是否在报告小程序中隐藏该检测部位
     */
    @TableField("hiding")
    private Integer hiding;
}
