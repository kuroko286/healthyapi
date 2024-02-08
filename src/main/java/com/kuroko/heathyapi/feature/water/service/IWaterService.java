package com.kuroko.heathyapi.feature.water.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.dto.WaterDto;

public interface IWaterService {
    List<Water> getWaterByUserAndDate(User user, LocalDate date);

    WaterDto addWaterIntake(String email, WaterDto waterDto);

    WaterDto deleteWaterIntake(String email);
}
