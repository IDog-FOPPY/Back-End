package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.ResponseDTO;
import com.idog.FOPPY.dto.chat.*;
import com.idog.FOPPY.service.ChatRoomService;
import com.idog.FOPPY.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Tag(name = "chat", description = "채팅 API")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/send")
    public void send(@Payload ChatMessageDTO.Send message) {
        chatService.sendMessage(message);
        template.convertAndSend("/sub/room/" + message.getRoomId(), message);  // /sub/room/{roomId} 구독자에게 메시지 전달
//        template.convertAndSendToUser(message.getReceiver(), "/queue", message); // /user/{username}/queue 구독자에게 메시지 전달
    }

    @PostMapping("/room")
    @Operation(summary = "채팅방 생성")
    public ResponseEntity<ResponseDTO<Long>> join(@RequestBody ChatRoomDTO.Request request) {
        try {
            Long roomId = chatRoomService.join(request);

            ResponseDTO<Long> response = new ResponseDTO<>();
            response.setStatus(true);
            response.setMessage("Success");
            response.setData(roomId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage()));
        }
    }

    @GetMapping("/rooms")
    @Operation(summary = "채팅방 목록 조회")
    public ResponseEntity<ResponseDTO<List<ChatRoomDTO.Response>>> getChatRoomList(@RequestParam Long memberId) {
        try {
            List<ChatRoomDTO.Response> chatRooms = chatRoomService.getChatRoomList(memberId);

            ResponseDTO<List<ChatRoomDTO.Response>> response = new ResponseDTO<>();
            response.setStatus(true);
            response.setMessage("Success");
            response.setData(chatRooms);

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage()));
        }
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "채팅방 상세 조회")
    public ResponseEntity<ResponseDTO<ChatRoomDTO.Detail>> getChatRoomDetail(@PathVariable Long roomId) {
        try {
            ChatRoomDTO.Detail chatRoom = chatRoomService.getChatRoomDetail(roomId);

            ResponseDTO<ChatRoomDTO.Detail> response = new ResponseDTO<>();
            response.setStatus(true);
            response.setMessage("Success");
            response.setData(chatRoom);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage()));
        }
    }

    @DeleteMapping("/room/{roomId}")
    @Operation(summary = "채팅방 삭제")
    public ResponseEntity<ResponseDTO<Void>> deleteChatRoom(@PathVariable Long roomId) {
        try {
            chatRoomService.deleteChatRoom(roomId);

            ResponseDTO<Void> response = new ResponseDTO<>();
            response.setStatus(true);
            response.setMessage("Success");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage()));
        }
    }
}

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
