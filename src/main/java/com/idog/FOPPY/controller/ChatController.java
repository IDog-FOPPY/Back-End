package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.chat.*;
import com.idog.FOPPY.service.ChatRoomService;
import com.idog.FOPPY.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/send") // endpoint: /app/private
    public void send(@Payload ChatMessageDTO.Send message) {
        chatService.sendMessage(message);
        template.convertAndSend("/sub/room/" + message.getRoomId(), message);  // /sub/room/{roomId} 구독자에게 메시지 전달
//        template.convertAndSendToUser(message.getReceiver(), "/queue", message); // /user/{username}/queue 구독자에게 메시지 전달
    }

    @PostMapping("/room")
    public ResponseEntity<?> join(@RequestBody ChatRoomDTO.Request request) {
        try {
            Long roomId = chatRoomService.join(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(roomId));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "400"));
        }
    }

//    @GetMapping("/room")
//    public ResponseEntity<?> getChatRoomList(@RequestParam Long memberId) {
//        return ResponseEntity.ok(chatRoomService.getChatRoomList(memberId));
//    }
//
//    @GetMapping("/room/{roomId}")
//    public ResponseEntity<?> getChatRoomDetail(@PathVariable Long roomId) {
//        return ResponseEntity.ok(chatRoomService.getChatRoomDetail(roomId));
//    }
    @GetMapping("/room")
    public String getChatRoomList(@RequestParam Long memberId, Model model) {
        List<ChatRoomDTO.Response> chatRooms = chatRoomService.getChatRoomList(memberId);
        System.out.println(chatRooms);
        model.addAttribute("chatRooms", chatRooms);
        return "chat_room_list";
    }

    @GetMapping("/room/{roomId}")
    public String getChatRoomDetail(@PathVariable Long roomId, Model model) {
        ChatRoomDTO.Detail chatRoom = chatRoomService.getChatRoomDetail(roomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chat_room_detail";
    }

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<?> updateChatRoom(@PathVariable Long roomId, @RequestBody ChatRoomNameRequest request) {
        return ResponseEntity.ok(chatRoomService.updateChatRoomName(roomId, request.getNewChatRoomName()));
    }
}

//    @DeleteMapping("/room/{roomId}")
//    public ResponseEntity<?> deleteChatRoom(@PathVariable Long roomId) {
//        chatRoomService.deleteChatRoom(roomId);
//        return ResponseEntity.ok().build();
//    }


//    @MessageMapping("/chatroom/{roomId}")
//    @SendTo("/sub/chatroom/{roomId}")
//    public ChatMessageDTO message(@DestinationVariable Long roomId, @Payload ChatMessageDTO message) {
//        chatService.save(ChatMessage.builder()
//                .chatRoomId(roomId)
//                .sender(message.getSender())
//                .message(message.getMessage())
//                .build());
//        return message;
//    }

//    @MessageMapping("/public") // endpoint: /app/public
//    @SendTo("/topic/messages") // /topic/messages 구독자에게 메시지 전달
//    public ChatMessageDTO receivePublicMessage(@Payload ChatMessageDTO message) {
//        return message;
//    }
//
//    @MessageMapping("/private") // endpoint: /app/private
//    public ChatMessageDTO receivePrivateMessage(@Payload ChatMessageDTO message) {
//        chatService.save(ChatMessage.builder()
//                .sender(message.getSender())
//                .message(message.getMessage())
//                .build());
//        template.convertAndSendToUser(message.getReceiver(), "/queue", message); // /user/{username}/queue 구독자에게 메시지 전달
//        return message;
//    }

//    //Client가 SEND할 수 있는 경로
//    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
//    //"/app/chat/enter"
//    @MessageMapping("/chat/enter")
//    public void enter(ChatMessageDTO message){
//        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }
//
//    @MessageMapping("/chat/message")
//    public void message(ChatMessageDTO message){
//        template.convertAndSend("/queue/chat/room/" + message.getRoomId(), message);
//    }
//}
