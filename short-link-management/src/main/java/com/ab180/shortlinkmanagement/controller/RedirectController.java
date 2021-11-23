package com.ab180.shortlinkmanagement.controller;

import com.ab180.shortlinkmanagement.entity.ShortLink;
import com.ab180.shortlinkmanagement.request.CreateShortLinkRequest;
import com.ab180.shortlinkmanagement.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class RedirectController {
    private Object data = null;

    @Autowired
    ShortLinkService SHORTLINK_SERVICE;

    @RequestMapping( value = "/r/{code}", method = RequestMethod.GET )
    @ResponseStatus( value = HttpStatus.FOUND )
    public void redirectToURLFromShortCode(HttpServletResponse httpServletResponse,
                                  @PathVariable("code") String shortId ) throws IOException {
        ShortLink shortLink = SHORTLINK_SERVICE.getShortLinkFromShortId( shortId );

        httpServletResponse.sendRedirect(shortLink.getUrl());
    }

    @RequestMapping( value = "/a/{alias}", method = RequestMethod.GET )
    @ResponseStatus( value = HttpStatus.FOUND )
    public void redirectToURLFromAliasName(HttpServletResponse httpServletResponse,
                                           @PathVariable("alias") String aliasName ) throws IOException {
        ShortLink shortLink = SHORTLINK_SERVICE.getShortLinkFromAliasName( aliasName );

        httpServletResponse.sendRedirect(shortLink.getUrl());
    }
}
