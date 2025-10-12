package com.temani.temani.features.counseling.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.counseling.domain.model.CounselingSchedule;
import com.temani.temani.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CounselingScheduleRepositoryImpl implements CounselingScheduleRepository {

    private final CounselingScheduleJpaRepository jpaRepository;
    private final CounselingScheduleEntityMapper mapper;

    @Override
    public CounselingSchedule save(CounselingSchedule schedule) {
        var entity = mapper.toEntity(schedule);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(CounselingSchedule schedule) {
        var entity = mapper.toEntity(schedule);
        jpaRepository.delete(entity);
    }

    @Override
    public Optional<CounselingSchedule> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CounselingSchedule> findAllByClientId(UUID clientId) {
        return jpaRepository.findAllByClientIdOrderByScheduledAtDesc(clientId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<CounselingSchedule> findAllByCounselorId(UUID counselorId) {
        return jpaRepository.findAllByCounselorIdOrderByScheduledAtDesc(counselorId).stream().map(mapper::toDomain)
                .toList();
    }
}
