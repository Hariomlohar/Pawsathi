package com.PawSathi.service.imple;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.PawSathi.services.CommanService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CommanServiceImpl implements CommanService  {

    @Override
    public void removeSessionMessage() {
           HttpServletRequest  request =((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
           HttpSession session= request.getSession();
           session.removeAttribute("succMsg");
           session.removeAttribute("errorMsg");
     }
    
}
