package org.fczm.blog.controller;

import org.fczm.common.util.RandomValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/validate")
public class ValidateController {

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void validateCode(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            // Output random validate code
            randomValidateCode.outputRandomCode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
