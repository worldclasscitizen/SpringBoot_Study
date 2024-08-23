package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // GET
    @GetMapping("/api/coffees")
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    // UPDATE
    // 쿼리를 날릴 때 JSON 데이터에 id 도 같이 담아야 한다.
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto form) {
        Coffee coffee = form.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 찾고자 하는 데이터가 없으면 에러 메시지 반환
        if(target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id : {}, coffee : {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 찾고자 하는 데이터가 있으면 DB 에 저장 + 일부만 변경하고 싶은 경우를 대비
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
