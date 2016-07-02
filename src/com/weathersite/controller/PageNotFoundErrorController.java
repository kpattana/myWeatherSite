package com.weathersite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageNotFoundErrorController {
    
	@RequestMapping(value="/pageNotFound.html")
    public ModelAndView handlePageNotFound() {
            // redirect to main page for all 404 errors
		return new ModelAndView("redirect:/");
       
    }
}
