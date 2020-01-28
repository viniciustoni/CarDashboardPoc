package com.globallogic.car.dashboard.service;

import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.globallogic.car.dashboard.dto.LayoutDto;
import com.globallogic.car.dashboard.entity.Layout;
import com.globallogic.car.dashboard.mapper.LayoutMapper;
import com.globallogic.car.dashboard.repository.LayoutRepository;
import com.globallogic.car.dashboard.service.spi.LayoutService;

@Service
public class LayoutServiceImpl implements LayoutService {

	private final LayoutMapper layoutMapper;
	private final LayoutRepository layoutRepository;

	public LayoutServiceImpl(LayoutMapper layoutMapper, LayoutRepository layoutRepository) {
		this.layoutMapper = layoutMapper;
		this.layoutRepository = layoutRepository;
	}

	@Override
	public LayoutDto saveLayout(LayoutDto layoutDto) {
		return of(layoutDto)
				.map(layoutMapper::layoutDtoToLayout)
				.map(layoutRepository::save)
				.map(layoutMapper::layoutToLayoutDto)
				.orElseThrow(PersistenceException::new);
	}

	@Override
	public void updateLayout(LayoutDto layoutDto) {
		layoutRepository.findById(layoutDto.getLayoutId())
			.orElseThrow(() -> new EntityNotFoundException(layoutDto.getLayoutId().toString()));
		
		of(layoutDto)
			.map(layoutMapper::layoutDtoToLayout)
			.map(layoutRepository::save)
			.map(layoutMapper::layoutToLayoutDto)
			.orElseThrow(PersistenceException::new);;
	}

	@Override
	public List<LayoutDto> findAll() {
		return StreamSupport.stream(layoutRepository.findAll().spliterator(), false)
				.map(layoutMapper::layoutToLayoutDto)
				.collect(toList());
	}

	@Override
	public void deleteLayout(Long layoutId) {
		final Layout layout = layoutRepository.findById(layoutId)
				.orElseThrow(EntityNotFoundException::new);
		
		layoutRepository.delete(layout);
	}
}
