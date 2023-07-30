//package com.idog.FOPPY.controller;
//
//import com.idog.FOPPY.domain.ChatRoom;
//import com.idog.FOPPY.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@RequestMapping("/chat")
//@RequiredArgsConstructor
//@Controller
//public class ChatRoomController {
//
//    private final ChatService chatService;
//
////    // 채팅방 목록 화면
////    @GetMapping("/room")
////    public String rooms() {
////        return "/chat/room";
////    }
//
//    // 모든 채팅방 목록
//    @GetMapping("/rooms")
//    public List<ChatRoom> room() {
//        return chatService.findAllRoom();
//    }
//
//    // 채팅방 생성
//    @PostMapping ("/room")
//    public ChatRoom createChatRoom(String name) {
//        return chatService.createChatRoom(name);
//    }
//
//    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    public ChatRoom roomDetail(Long roomId) {
//        return chatService.findRoomById(roomId);
//    }
//}
