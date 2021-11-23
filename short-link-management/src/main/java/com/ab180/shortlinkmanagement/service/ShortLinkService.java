package com.ab180.shortlinkmanagement.service;

import com.ab180.shortlinkmanagement.db.dao.ShortLinkDAO;
import com.ab180.shortlinkmanagement.entity.ShortLink;
import com.ab180.shortlinkmanagement.request.AddAliasRequest;
import com.ab180.shortlinkmanagement.request.CreateShortLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortLinkService {

    @Autowired
    private ShortLinkDAO shortLinkDAO;

    public ShortLink registerShortLink(CreateShortLinkRequest request){
        return shortLinkDAO.insertShortLink(request);
    }

    public List<ShortLink> getShortLinks(int from, int size) {
        return shortLinkDAO.selectShortLinks( from, size );
    }

    public ShortLink getShortLinkFromShortId(String shortId) {
        return shortLinkDAO.selectShortLinkFromShortId(shortId);
    }

    public ShortLink updateAliasFromShortId(String shortId, AddAliasRequest request) {
        return shortLinkDAO.updateShortLinkSetAliasName(shortId, request);
    }

    public ShortLink getShortLinkFromAliasName(String aliasName) {
        return shortLinkDAO.selectShortLinkFromAliasName(aliasName);
    }

    public ShortLink deleteShortLink(String shortId) {
        return shortLinkDAO.deleteShortLink(shortId);
    }
}
