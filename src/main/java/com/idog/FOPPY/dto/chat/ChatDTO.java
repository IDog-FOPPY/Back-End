package com.idog.FOPPY.dto.chat;

import lombok.Data;

@Data
public class ChatDTO {

    private Long channelId;
    private Long sender;
    private String message;
}
