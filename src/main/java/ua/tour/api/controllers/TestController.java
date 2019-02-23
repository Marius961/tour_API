package ua.tour.api.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/test/")
public class TestController {

    @GetMapping
    public ModelAndView test() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        StringBuilder a = new StringBuilder();
        for (GrantedAuthority au: authorities) {
            a.append(au.getAuthority());
        }
        ModelAndView mw = new ModelAndView();
        mw.addObject("auth", a);
        mw.setViewName("test");
        return mw;
    }
}
