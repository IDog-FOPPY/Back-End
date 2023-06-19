package com.idog.FOPPY.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {

    private Long channelId;
    private String sender;
    private String receiver;
    private String text;
//    private String datetime;
    private Status status;
}
