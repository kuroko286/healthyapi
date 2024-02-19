package com.kuroko.heathyapi.feature.water;

import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.water.dto.WaterDto;
import com.kuroko.heathyapi.feature.water.service.WaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v1/water")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WaterController {
    @Autowired
    private WaterService waterService;

    @PostMapping("/water-intake")
    public ResponseEntity<WaterDto> addWaterIntake(@RequestAttribute("email") String email,
            @RequestBody WaterDto waterDto) {
        WaterDto water = waterService.addWaterIntake(email, waterDto);
        return ResponseEntity.ok().body(water);
    }

    @DeleteMapping("/water-intake")
    public ResponseEntity<WaterDto> deleteWaterIntake(@RequestAttribute("email") String email) {
        WaterDto water = waterService.deleteWaterIntake(email);
        return ResponseEntity.ok().body(water);
    }

}