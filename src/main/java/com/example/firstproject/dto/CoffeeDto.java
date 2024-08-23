package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CoffeeDto {
    private Long id;

    private String name;

    private String price;

    public Coffee toEntity() {
        return new Coffee(id, name, price);
    }
}
