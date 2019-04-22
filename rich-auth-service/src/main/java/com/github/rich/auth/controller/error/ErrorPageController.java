package com.github.rich.auth.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Controller
public class ErrorPageController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private final ErrorAttributes errorAttributes;

    public ErrorPageController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
                request, true));
        ModelAndView modelAndView = null;

        HttpStatus status = getStatus(request);
        if (status.value() == 404){
            modelAndView = new ModelAndView("error/404", model);
        } else if (status.value() == 403){
            modelAndView = new ModelAndView("error/403", model);
        } else if (status.value() == 500){
            modelAndView = new ModelAndView("error/500", model);
        } else {
            modelAndView = new ModelAndView("error", model);
        }

        return modelAndView;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                     boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
