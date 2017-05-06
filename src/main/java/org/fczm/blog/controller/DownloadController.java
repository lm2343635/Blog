package org.fczm.blog.controller;

import org.fczm.blog.bean.AttachmentBean;
import org.fczm.blog.controller.common.ControllerTemplate;
import org.fczm.blog.service.AttachmentManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/download")
public class DownloadController extends ControllerTemplate {

    @RequestMapping("/admin")
    public void downloadByAid(@RequestParam String aid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AttachmentBean attachmentBean = attachmentManager.adminDownloadAttachment(aid, request.getSession());
        if (attachmentBean == null) {
            response.sendRedirect("/admin/sessionError.html");
            return;
        }
        download(createUploadDirectory(attachmentBean.getBid()) + File.separator + attachmentBean.getStore(), attachmentBean.getFilename(), response);
    }

    @RequestMapping("/token")
    public void downloadByToken(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AttachmentBean attachmentBean = attachmentManager.userDownloadAttachment(token, request.getSession());
        if (attachmentBean == null) {
            response.sendRedirect("/downloadError.html");
            return;
        }
        download(createUploadDirectory(attachmentBean.getBid()) + File.separator + attachmentBean.getStore(), attachmentBean.getFilename(), response);
    }

}
