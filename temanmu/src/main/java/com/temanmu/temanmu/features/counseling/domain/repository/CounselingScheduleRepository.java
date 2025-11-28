package com.temanmu.temanmu.features.counseling.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temanmu.temanmu.features.counseling.domain.model.CounselingSchedule;

public interface CounselingScheduleRepository {

    CounselingSchedule save(CounselingSchedule schedule);

    void delete(CounselingSchedule schedule);

    Optional<CounselingSchedule> findById(UUID id);

    List<CounselingSchedule> findAllByClientId(UUID clientId);

    List<CounselingSchedule> findAllByCounselorId(UUID counselorId);

}
