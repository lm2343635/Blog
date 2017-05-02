package org.fczm.blog.controller;

import org.fczm.blog.controller.common.ControllerTemplate;
import org.fczm.blog.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/upload")
public class UploadController extends ControllerTemplate {

    @RequestMapping(value = "/cover", method = RequestMethod.POST)
    public ResponseEntity uploadBlogCover(@RequestParam String bid, HttpServletRequest request) {
        if (!checkAdminSession(request.getSession())) {
            return generateBadRequest(ErrorCode.ErrorAdminSession);
        }
        String filepath = createUploadDirectory(bid);
        String fileName = upload(request, filepath);
        final String cover = blogManager.handleUploadedCover(bid, fileName);
        if (cover == null) {
            return generateBadRequest(ErrorCode.ErrorObjecId);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("cover", cover);
        }});
    }

    @RequestMapping(value = "/illustration", method = RequestMethod.POST)
    public ResponseEntity uploadIllustration(@RequestParam String bid, HttpServletRequest request) {
        if (!checkAdminSession(request.getSession())) {
            return generateBadRequest(ErrorCode.ErrorAdminSession);
        }
        String filepath = createUploadDirectory(bid);
        String fileName = upload(request, filepath);
        final String newName = illustrationManager.handleUploadIllustration(bid, fileName);
        return generateOK(new HashMap<String, Object>() {{
            put("filename", newName);
        }});
    }

    @RequestMapping(value = "/attachement", method = RequestMethod.POST)
    public ResponseEntity uploadAttachment(@RequestParam String bid, HttpServletRequest request) {

        return generateOK(new HashMap<String, Object>() {

        });
    }

}
