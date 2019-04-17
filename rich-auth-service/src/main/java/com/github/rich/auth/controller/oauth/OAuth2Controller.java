package com.github.rich.auth.controller.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 重写用户授权页面
 *
 * @author Petty
 * @version 1.0.0
 * @since 2018年3月11日
 */
@Controller
@SessionAttributes("authorizationRequest")
@RequestMapping("oauth")
public class OAuth2Controller {

    private final ClientDetailsService clientDetailsService;

    private final ApprovalStore approvalStore;

    @Autowired
    public OAuth2Controller(ClientDetailsService clientDetailsService, ApprovalStore approvalStore) {
        this.clientDetailsService = clientDetailsService;
        this.approvalStore = approvalStore;
    }

    @GetMapping("/confirm_access")
    public ModelAndView authorize(Map<String, Object> model, Principal principal) {
        AuthorizationRequest clientAuth = (AuthorizationRequest) model.remove("authorizationRequest");
        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
        model.put("auth_request", clientAuth);
        model.put("client", client);
        Map<String, String> scopes = new LinkedHashMap<>();
        for (String scope : clientAuth.getScope()) {
            scopes.put(OAuth2Utils.SCOPE_PREFIX + scope, "false");
        }
        for (Approval approval : approvalStore.getApprovals(principal.getName(), client.getClientId())) {
            if (clientAuth.getScope().contains(approval.getScope())) {
                scopes.put(OAuth2Utils.SCOPE_PREFIX + approval.getScope(),
                        approval.getStatus() == ApprovalStatus.APPROVED ? "true" : "false");
            }
        }
        model.put("scopes", scopes);
        return new ModelAndView("oauth/confirm_access", model);
    }

    @RequestMapping("/error")
    public String handleError(Map<String, Object> model) {
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth/oauth_error";
    }

}
