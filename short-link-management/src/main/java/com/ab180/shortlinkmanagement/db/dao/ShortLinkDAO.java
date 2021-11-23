package com.ab180.shortlinkmanagement.db.dao;

import com.ab180.shortlinkmanagement.entity.ShortLink;
import com.ab180.shortlinkmanagement.request.AddAliasRequest;
import com.ab180.shortlinkmanagement.request.CreateShortLinkRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ShortLinkDAO {
    private static final String NAMESPACE = "com.ab180.shortlinkmanagement.db.dao.";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static final int SHORTCODE_LENGTH = 5;
    private static final int SHORTCODE_GENERATION_TRIES = 10000000;

    @Autowired
    private SqlSession sqlSession;

    public ShortLink insertShortLink(CreateShortLinkRequest request ){
        String shortId = createUniqueShortId();
        String now_UTC = getCurrentTime();

        ShortLink shortLink = ShortLink.builder()
                .shortId(shortId)
                .createdAt(now_UTC)
                .url(request.url)
                .build();

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("url", shortLink.getUrl());
        data.put("shortId", shortLink.getShortId());
        data.put("createdAt", shortLink.getCreatedAt());

        sqlSession.insert( NAMESPACE + "insertShortLinkData", data );

        return shortLink;
    }

    private String getCurrentTime(){
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }

    private boolean isExistingShortId( String shortId ){
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("shortId", shortId);
        int ret = sqlSession.selectOne( NAMESPACE + "existsShortId", input );
        return ret == 1;
    }

    private String createUniqueShortId(){
        String shortId = createShortId();
        int tries = 0;
        while( isExistingShortId( shortId ) ){
            shortId = createShortId();
            tries++;
            if( tries >= SHORTCODE_GENERATION_TRIES )
                //throws error
                return null;
        }

        return shortId;
    }

    private String createShortId(){
        StringBuilder sb = new StringBuilder();
        Random randomType = new Random();
        Random randAlpha = new Random();
        Random randDigit = new Random();

        for(int i = 0; i < SHORTCODE_LENGTH; i++){
            int type = randomType.nextInt(2);

            if(type == 0){ //alphabet
                sb.append( (char)('z' - randAlpha.nextInt(26)) );
            } else { //number
                sb.append( randDigit.nextInt(10) );
            }
        }
        return sb.toString();
    }

    public List<ShortLink> selectShortLinks(int from, int to) {
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("from", from);
        input.put("size", to);

//        List<Object> list =
        return sqlSession.selectList( NAMESPACE + "selectShortLinksLimit", input );
//        List<ShortLink> res = new ArrayList<>();
//        for( int i = 0; i < list.size(); i++ )
//        {
//            ShortLink shortLink = (ShortLink) list.get(i);
//            res.add(shortLink);
//        }
//
//        return res;
    }

    public ShortLink selectShortLinkFromShortId(String shortId) {
        return sqlSession.selectOne( NAMESPACE + "selectShortLinkFromShortId", shortId );
    }

    public ShortLink updateShortLinkSetAliasName(String shortId, AddAliasRequest request) {
        if( isExistingAliasName( request.aliasName ) ){
            //throw error
            return null;
        } else if( !isExistingShortId(shortId) ){
            //throw error
            return null;
        } else {
            HashMap<String, Object> input = new HashMap<String, Object>();
            input.put("shortId", shortId);
            input.put("aliasName", request.aliasName );
            sqlSession.update( NAMESPACE + "updateShortLinkSetAliasName", input );

            return selectShortLinkFromShortId(shortId);
        }
    }

    public ShortLink selectShortLinkFromAliasName(String aliasName) {
        if( !isExistingAliasName( aliasName ) ){
            //throw error
            return null;
        } else {
            HashMap<String, Object> input = new HashMap<String, Object>();
            input.put("aliasName", aliasName);
            return sqlSession.selectOne( NAMESPACE + "selectShortLinkFromAliasName", input);
        }
    }

    public ShortLink deleteShortLink(String shortId) {
        ShortLink shortLink = selectShortLinkFromShortId(shortId);
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("shortId", shortId);
        sqlSession.delete( NAMESPACE + "deleteShortLink", input);

        return shortLink;
    }

    private boolean isExistingAliasName(String aliasName){
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("aliasName", aliasName);
        int ret = sqlSession.selectOne( NAMESPACE + "existsAliasName", input );
        return ret == 1;
    }
}
