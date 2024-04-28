package com.epicas.platform.log;

import com.epicas.platform.domain.dto.LogDTO;
import com.epicas.platform.holder.ClientIpHolder;
import com.epicas.platform.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * @date 2023年10月09日 11:03
 */
@Aspect
@Component
@RequiredArgsConstructor
public class EpicasLogAspect {

    private final SysLogService sysLogService;

    @Pointcut("@annotation( com.epicas.platform.log.EpicasLog)")
    public void logPointCut() {
    }

    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LogDTO logDTO = new LogDTO();
        logDTO.setUrl(request.getRequestURI());
        String value = null;
        //获取操作
        EpicasLog epicasLog = method.getAnnotation(EpicasLog.class);
        if (epicasLog != null) {
            value = epicasLog.value();
        }
        if (value.startsWith("导入文件")) {
            logDTO.setParameter("导入文件");
        } else if (value.startsWith("导出")) {
            logDTO.setParameter("文件 导出");
        } else {
            // 请求的参数
            Object[] args = joinPoint.getArgs();
            // 获取参数的索引
            int[] paramIndex = epicasLog.paramIndex();

            // 定义一个字符串保存最终存入数据库的参数
            String params;

            if (paramIndex.length > 0) {
                // 创建一个数组来保存参数
                List<Object> filteredArgs = new ArrayList<>();
                for (int i = 0; i < paramIndex.length; i++) {
                    // 排除 MultipartFile 类型的参数
                    if (!(args[paramIndex[i]] instanceof MultipartFile)) {
                        filteredArgs.add(args[paramIndex[i]]);
                    }
                }
                params = new ObjectMapper().writeValueAsString(filteredArgs);
            } else {
                List<Object> filteredArgs = new ArrayList<>();
                for (Object arg : args) {
                    // 排除 MultipartFile 类型的参数
                    if (!(arg instanceof MultipartFile)) {
                        filteredArgs.add(arg);
                    }
                }
                params = new ObjectMapper().writeValueAsString(filteredArgs);
            }
            logDTO.setParameter(params);
        }

        //设置操作类型
        logDTO.setOperation(value);

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        logDTO.setClassName(className);
        //获取请求的方法名
        String methodName = method.getName();
        logDTO.setMethod(methodName);

        //获取用户ip地址
        String ip = ClientIpHolder.getClientIp();
        logDTO.setLoginIp(ip);
        logDTO.setOperTime(System.currentTimeMillis());
        //调用service保存SysLog实体类到数据库
        sysLogService.addLog(logDTO);
    }
}
