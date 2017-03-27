package org.fczm.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResponseEntity uploadBlogCover(@RequestParam String bid, HttpServletRequest request) {

        return null;
    }

}
