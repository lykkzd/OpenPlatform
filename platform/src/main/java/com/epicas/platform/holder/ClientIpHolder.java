package com.epicas.platform.holder;

/**
 * @author liuyang
 * @date 2023年10月09日 10:55
 */
public class ClientIpHolder {
    private static final InheritableThreadLocal<String> clientIpHolder = new InheritableThreadLocal<>();

    public static void setClientIp(String clientIp) {
        clientIpHolder.set(clientIp);
    }

    public static String getClientIp() {
        return clientIpHolder.get();
    }

    public static void clear() {
        clientIpHolder.remove();
    }
}
