package com.ab180.shortlinkmanagement.controller;

import com.ab180.shortlinkmanagement.entity.ShortLink;
import com.ab180.shortlinkmanagement.request.AddAliasRequest;
import com.ab180.shortlinkmanagement.request.CreateShortLinkRequest;
import com.ab180.shortlinkmanagement.response.ResponseShortLink;
import com.ab180.shortlinkmanagement.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShortLinkController {
    private Object data = null;

    @Autowired
    ShortLinkService SHORTLINK_SERVICE;

    @RequestMapping( value = "/short-links", method = RequestMethod.POST )
    @ResponseStatus( value = HttpStatus.OK )
    public Object createShortLink(@RequestBody CreateShortLinkRequest request )
    {
        if(request.url == null )
            return null;
            //throw new Exception();

        data = SHORTLINK_SERVICE.registerShortLink( request );
        return new ResponseEntity<ShortLink>( (ShortLink) data, HttpStatus.OK );
    }

    @RequestMapping( value = "/short-links", method = RequestMethod.GET )
    @ResponseStatus( value = HttpStatus.OK ) //validation needed on params
    public Object getShortLinks(@RequestParam("size") int size, @RequestParam("page") int page )
    {
        int from = (page-1) * size;
        data = SHORTLINK_SERVICE.getShortLinks( from, size );
        return new ResponseEntity<List<ShortLink>>( (List<ShortLink>) data, HttpStatus.OK );
    }

    @RequestMapping( value = "/short-links/{code}", method = RequestMethod.GET )
    @ResponseStatus( value = HttpStatus.OK )
    public Object getShortLinkFromShortCode(@PathVariable("code") String shortId){
        data = SHORTLINK_SERVICE.getShortLinkFromShortId( shortId );

        return new ResponseEntity<ShortLink>( (ShortLink) data, HttpStatus.OK );
    }

    @RequestMapping( value = "/short-links/{code}", method = RequestMethod.PATCH )
    @ResponseStatus( value = HttpStatus.OK )
    public Object addAliasNameToShortLink(@PathVariable("code") String shortId, @RequestBody AddAliasRequest request){
        //error when aliasName is null
        if( request.aliasName == null )
            return null;

        //error when shortId does not exists, error when alias name is in use
        data = SHORTLINK_SERVICE.updateAliasFromShortId( shortId, request );

        return new ResponseEntity<ShortLink>( (ShortLink) data, HttpStatus.OK );
    }

    @RequestMapping( value = "/short-links/{code}", method = RequestMethod.DELETE )
    @ResponseStatus( value = HttpStatus.OK )
    public Object deleteShortLinkByShortCode(@PathVariable("code") String shortId){
        //short id check
        if( shortId == null )
            return null;

        data = SHORTLINK_SERVICE.deleteShortLink( shortId );

        return new ResponseEntity<ShortLink>( (ShortLink) data, HttpStatus.OK );
    }
}
