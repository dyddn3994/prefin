package com.prefin.service.entertainment;

import com.prefin.repository.entertainment.MascotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MascotService {
    private final MascotRepository mascotRepository;
}
