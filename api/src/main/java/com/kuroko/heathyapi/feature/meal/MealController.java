package com.kuroko.heathyapi.feature.meal;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.food.dto.AddFoodDto;
import com.kuroko.heathyapi.feature.food.dto.FoodIntakeData;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayDto;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.meal.service.MealService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/meals")
public class MealController {
    @Autowired
    private MealService mealServicer;

    @PostMapping("/food-intake")
    public ResponseEntity<MealsPerDayDto> addFoodIntake(@RequestAttribute("email") String email,
            @RequestBody AddFoodDto addFoodDto) {
        MealsPerDayDto response = mealServicer.addFoodIntake(email, addFoodDto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/food-intake/{foodId}")
    public ResponseEntity<MealsPerDayDto> updateFoodIntake(@RequestAttribute("email") String email,
            @PathVariable Long foodId,
            @RequestBody FoodIntakeData mealDto) {
        MealsPerDayDto response = mealServicer.updateFoodIntake(email, foodId, mealDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/food-intake")
    public ResponseEntity<MealsPerDayDto> deleteFoodIntake(@RequestAttribute("email") String email,
            @RequestBody String mealType) {
        Map<String, MealType> mealTypeMap = Map.of("breakfast", MealType.BREAKFAST, "lunch", MealType.LUNCH, "dinner",
                MealType.DINNER, "snack", MealType.SNACK);
        MealsPerDayDto response = mealServicer.deleteFoodIntake(email, mealTypeMap.get(mealType));
        return ResponseEntity.ok().body(response);
    }

}