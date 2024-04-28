package com.epicas.platform.controller;

import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.log.EpicasLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * 下载文件 前端控制器
 * @author liuyang
 * @date 2023年10月16日 17:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Api(tags = "下载文件相关接口")
public class FileController {

    private final ServletContext servletContext;

    private final ResourceLoader resourceLoader;

    /**
     * 下载文件
     * @param fileName
     * @return
     */
    @ApiOperation("excel模板下载")
    @GetMapping("/downloadFile/{fileName}")
    @EpicasLog(value = "excel模板下载", paramIndex = {0})
    public ResponseEntity downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        //1. 获取文件的字节流
        InputStream in = resourceLoader.getResource("classpath:/static/" + fileName).getInputStream();
        //2. 读取文件的字节流
        byte[] body = new byte[in.available()];
        in.read(body);
        //2. 获取文件的MIME类型
        String mimeType = servletContext.getMimeType(fileName);
        //3. 构建ResponseEntity对象
        ResponseEntity<byte[]> entity = ResponseEntity.ok(body);
        entity.getHeaders().add("Content-Disposition", "attachment;filename=" + fileName);
        entity.getHeaders().add("Content-Type", mimeType);
        return entity;
    }
}
