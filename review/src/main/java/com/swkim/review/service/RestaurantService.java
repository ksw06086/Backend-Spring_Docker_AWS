package com.swkim.review.service;

import com.swkim.review.api.request.CreateAndEditRestaurantRequest;
import com.swkim.review.model.MenuEntity;
import com.swkim.review.model.RestaurantEntity;
import com.swkim.review.repository.MenuRepository;
import com.swkim.review.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional // CRUD 트랜잭션을 열고 닫는 역할
    public RestaurantEntity createRestaurant(
            CreateAndEditRestaurantRequest request
    ) {
        // build 쓰기 위해서 @Build와 @AllArgsConstructor를 Entity에 추가해주어야 함
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });

        restaurantRepository.save(restaurant);

        return restaurant;
    }

    public void editRestaurant() {

    }

    public void deleteRestaurant() {

    }
}
