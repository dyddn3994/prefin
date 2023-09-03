package com.prefin.service.user;

import com.prefin.repository.user.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
}
