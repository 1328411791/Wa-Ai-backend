package org.talang.wabackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {

    // 图片返回接口
    @GetMapping("/id/{id}")
    public void getImageById(@PathVariable String id) {
        System.out.println("Getting image by id...");
    }

}
