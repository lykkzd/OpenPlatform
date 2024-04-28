package com.epicas.platform.holder;

/**
 * @author liuyang
 * @date 2023年10月07日 10:06
 */
public class OrgIdHolder {
    private static final InheritableThreadLocal<Long> orgIdHolder = new InheritableThreadLocal<>();

    public static void setOrgId(Long orgId) {
        orgIdHolder.set(orgId);
    }

    public static Long getOrgId() {
        return orgIdHolder.get();
    }

    public static void clear() {
        orgIdHolder.remove();
    }
}
