package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Printing", description = "画图API")
@RestController
@RequestMapping("/printing")
public class PrintingController {

    @PostMapping("/")
    public void print() {
        System.out.println("Printing...");
    }
}
