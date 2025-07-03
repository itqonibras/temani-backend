package com.temani.temani.features.todo.presentation.controller;

import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.constants.ToDoMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.todo.presentation.dto.request.ToDoListRequest;
import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;
import com.temani.temani.features.todo.usecase.CreateToDoListUseCase;
import com.temani.temani.features.todo.usecase.DeleteToDoListUseCase;
import com.temani.temani.features.todo.usecase.GetAllToDoListsUseCase;
import com.temani.temani.features.todo.usecase.UpdateToDoListUseCase;
import com.temani.temani.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo-lists")
public class ToDoListController {

    private final GetAllToDoListsUseCase getAllToDoListsUseCase;
    private final CreateToDoListUseCase createToDoListUseCase;
    private final UpdateToDoListUseCase updateToDoListUseCase;
    private final DeleteToDoListUseCase deleteToDoListUseCase;

    @GetMapping
    public ResponseEntity<?> getToDoLists(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            List<ToDoListResponse> toDoLists = getAllToDoListsUseCase.execute(user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_LISTS_RECEIVED_SUCCESS, toDoLists));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }



    @PostMapping("/create")
    public ResponseEntity<?> createToDoList(@RequestBody @Valid ToDoListRequest request, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            ToDoListResponse toDoList = createToDoListUseCase.execute(request, user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_LIST_CREATED_SUCCESS, toDoList));
        } catch (Exception e) {
            System.err.println("Error in createToDoList: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponse.error("Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateToDoList(@PathVariable UUID id, @RequestBody @Valid ToDoListRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            ToDoListResponse toDoList = updateToDoListUseCase.execute(id, request, user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_LIST_UPDATED_SUCCESS, toDoList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToDoList(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            deleteToDoListUseCase.execute(id, user.getId());
            return ResponseEntity.ok(BaseResponse.success(String.format(ToDoMessages.TODO_LIST_DELETED_SUCCESS, id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
} 