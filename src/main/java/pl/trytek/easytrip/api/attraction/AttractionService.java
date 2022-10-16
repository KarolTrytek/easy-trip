package pl.trytek.easytrip.api.attraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.trytek.easytrip.data.repository.AttractionRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionRepository attractionRepository;
	private final AttractionMapper mapper;

	@Transactional
	public List<AttractionDto> getAttractions() {
		return mapper.map(attractionRepository.findAll());
	}
}
