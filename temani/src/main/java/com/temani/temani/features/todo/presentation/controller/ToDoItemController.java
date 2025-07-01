package com.temani.temani.features.todo.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.constants.ToDoMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;
import com.temani.temani.features.todo.usecase.CreateToDoItemUseCase;
import com.temani.temani.features.todo.usecase.DeleteToDoItemUseCase;
import com.temani.temani.features.todo.usecase.ToggleToDoItemCompleteUseCase;
import com.temani.temani.features.todo.usecase.UpdateToDoItemUseCase;
import com.temani.temani.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo-items")
public class ToDoItemController {

    private final CreateToDoItemUseCase createToDoItemUseCase;
    private final UpdateToDoItemUseCase updateToDoItemUseCase;
    private final DeleteToDoItemUseCase deleteToDoItemUseCase;
    private final ToggleToDoItemCompleteUseCase toggleToDoItemCompleteUseCase;

    @PostMapping("/{listId}")
    public ResponseEntity<?> createToDoItem(@PathVariable UUID listId, @RequestBody @Valid ToDoItemRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            ToDoItemResponse toDoItem = createToDoItemUseCase.execute(listId, request, user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_ITEM_CREATED_SUCCESS, toDoItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateToDoItem(@PathVariable UUID id, @RequestBody @Valid ToDoItemRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            ToDoItemResponse toDoItem = updateToDoItemUseCase.execute(id, request, user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_ITEM_UPDATED_SUCCESS, toDoItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<?> toggleToDoItemComplete(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            ToDoItemResponse toDoItem = toggleToDoItemCompleteUseCase.execute(id, user.getId());
            return ResponseEntity.ok(BaseResponse.success(ToDoMessages.TODO_ITEM_TOGGLED_SUCCESS, toDoItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToDoItem(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            deleteToDoItemUseCase.execute(id, user.getId());
            return ResponseEntity.ok(BaseResponse.success(String.format(ToDoMessages.TODO_ITEM_DELETED_SUCCESS, id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
} 