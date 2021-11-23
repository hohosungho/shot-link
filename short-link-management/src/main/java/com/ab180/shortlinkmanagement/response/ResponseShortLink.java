package com.ab180.shortlinkmanagement.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseShortLink {
    private String url;
    private String shortId;
    private String createdAt;

}
