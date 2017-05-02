package org.fczm.blog.controller;

import org.fczm.blog.controller.common.ControllerTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/download")
public class DownloadController extends ControllerTemplate {

    @RequestMapping("/admin")
    public void downloadByAid(@RequestParam String aid, HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping("/token")
    public void downloadByToken(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) {

    }

}
