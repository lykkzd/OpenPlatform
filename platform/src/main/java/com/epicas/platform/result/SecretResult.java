package com.epicas.platform.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyang
 * @date 2023年10月16日 09:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretResult {
    private Long time;
    private String sign;
    private Result<String> result;
}
