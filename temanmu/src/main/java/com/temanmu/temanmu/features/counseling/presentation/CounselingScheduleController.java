package com.temanmu.temanmu.features.counseling.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.constants.CounselingScheduleMessages;
import com.temanmu.temanmu.common.enums.CounselingScheduleStatus;
import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temanmu.temanmu.features.counseling.presentation.dto.UpdateScheduleStatusRequest;
import com.temanmu.temanmu.features.counseling.usecase.BookCounselingScheduleUseCase;
import com.temanmu.temanmu.features.counseling.usecase.CreateCounselingScheduleUseCase;
import com.temanmu.temanmu.features.counseling.usecase.DeleteCounselingScheduleUseCase;
import com.temanmu.temanmu.features.counseling.usecase.GetAllCounselingSchedulesUseCase;
import com.temanmu.temanmu.features.counseling.usecase.GetAvailableCounselingSchedulesUseCase;
import com.temanmu.temanmu.features.counseling.usecase.GetCounselingScheduleByIdUseCase;
import com.temanmu.temanmu.features.counseling.usecase.UpdateCounselingScheduleUseCase;
import com.temanmu.temanmu.features.counseling.usecase.UpdateScheduleStatusUseCase;
import com.temanmu.temanmu.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counseling-schedules")
public class CounselingScheduleController {

    private final GetAllCounselingSchedulesUseCase getAllSchedules;
    private final GetAvailableCounselingSchedulesUseCase getAvailableSchedules;
    private final GetCounselingScheduleByIdUseCase getById;
    private final CreateCounselingScheduleUseCase createSchedule;
    private final BookCounselingScheduleUseCase bookSchedule;
    private final UpdateCounselingScheduleUseCase updateSchedule;
    private final UpdateScheduleStatusUseCase updateScheduleStatus;
    private final DeleteCounselingScheduleUseCase deleteSchedule;

    @GetMapping
    public ResponseEntity<?> getAll(
            Authentication auth,
            @RequestParam(required = false) List<CounselingScheduleStatus> status) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            boolean isCaregiver = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("CAREGIVER"));
            List<CounselingScheduleResponse> schedules = getAllSchedules.execute(user.getId(), isCaregiver, status);
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.SCHEDULES_RECEIVED_SUCCESS, schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/client")
    public ResponseEntity<?> getClient(
            Authentication auth,
            @RequestParam(required = false) List<CounselingScheduleStatus> status) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            // Check if user has CLIENT role
            boolean isClient = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("CLIENT"));
            if (!isClient) {
                return ResponseEntity.badRequest()
                        .body(BaseResponse.error("Only clients can access client schedules"));
            }

            List<CounselingScheduleResponse> schedules = getAllSchedules.execute(user.getId(), false, status);
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.CLIENT_SCHEDULES_RECEIVED_SUCCESS, schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/caregiver")
    public ResponseEntity<?> getCaregiver(
            Authentication auth,
            @RequestParam(required = false) List<CounselingScheduleStatus> status) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            // Check if user has CAREGIVER role
            boolean isCaregiver = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("CAREGIVER"));
            if (!isCaregiver) {
                return ResponseEntity.badRequest()
                        .body(BaseResponse.error("Only caregivers can access caregiver schedules"));
            }

            List<CounselingScheduleResponse> schedules = getAllSchedules.execute(user.getId(), true, status);
            return ResponseEntity.ok(
                    BaseResponse.success(CounselingScheduleMessages.CAREGIVER_SCHEDULES_RECEIVED_SUCCESS, schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/peer")
    public ResponseEntity<?> getPeer(
            Authentication auth,
            @RequestParam(required = false) List<CounselingScheduleStatus> status) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            // Check if user has PEER role
            boolean isPeer = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
            if (!isPeer) {
                return ResponseEntity.badRequest()
                        .body(BaseResponse.error("Only peers can access peer schedules"));
            }

            List<CounselingScheduleResponse> schedules = getAllSchedules.execute(user.getId(), true, status);
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.PEER_SCHEDULES_RECEIVED_SUCCESS, schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailable(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            // Check if user has CLIENT role to view available schedules
            boolean isClient = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("CLIENT"));
            if (!isClient) {
                return ResponseEntity.badRequest()
                        .body(BaseResponse.error("Only clients can view available schedules"));
            }

            List<CounselingScheduleResponse> schedules = getAvailableSchedules.execute();
            return ResponseEntity.ok(BaseResponse.success("Available schedules retrieved successfully", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            CounselingScheduleResponse schedule = getById.execute(id, user.getId());
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.SCHEDULE_RECEIVED_SUCCESS, schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CounselingScheduleRequest request, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            CounselingScheduleResponse schedule = createSchedule.execute(user.getId(), request);
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.SCHEDULE_CREATED_SUCCESS, schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> book(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            CounselingScheduleResponse schedule = bookSchedule.execute(id, user.getId());
            return ResponseEntity.ok(BaseResponse.success("Schedule booked successfully", schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid CounselingScheduleRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            CounselingScheduleResponse schedule = updateSchedule.execute(id, user.getId(), request);
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.SCHEDULE_UPDATED_SUCCESS, schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id,
            @RequestBody @Valid UpdateScheduleStatusRequest request, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            // Check if user has PEER role
            boolean isPeer = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
            if (!isPeer) {
                return ResponseEntity.status(403)
                        .body(BaseResponse.error("Only peers can update schedule status"));
            }

            CounselingScheduleResponse schedule = updateScheduleStatus.execute(id, user.getId(), request.getStatus());
            return ResponseEntity.ok(BaseResponse.success("Schedule status updated successfully", schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            deleteSchedule.execute(id, user.getId());
            return ResponseEntity
                    .ok(BaseResponse.success(CounselingScheduleMessages.SCHEDULE_DELETED_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}
