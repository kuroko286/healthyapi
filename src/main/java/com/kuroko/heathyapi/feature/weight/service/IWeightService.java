package com.kuroko.heathyapi.feature.weight.service;

import com.kuroko.heathyapi.feature.weight.dto.WeightDto;
import com.kuroko.heathyapi.feature.weight.dto.WeightUpdatedDto;

public interface IWeightService {
    WeightUpdatedDto createWeight(String email, WeightDto weightDto);
}
