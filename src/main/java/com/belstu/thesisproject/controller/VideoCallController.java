package com.belstu.thesisproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VideoCallController {
  @RequestMapping(method = RequestMethod.GET, value = "/2")
  public String index() {
    return "static/index.html";
  }
}
