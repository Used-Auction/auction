package com.example.auction.Point;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Point.Dto.PointEarnResponseDto;
import com.example.auction.Point.Dto.PointResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    @PostMapping("/earn")
    public ResponseEntity<CommonResponseBody<PointEarnResponseDto>> earnPoint() {

        return ResponseEntity.ok().body(new CommonResponseBody<>("포인트 적립",
                pointService.earnPoint(1L,1000)));
    }

    @PostMapping("/bid")
    public ResponseEntity<CommonResponseBody<PointResponseDto>> bidPoint(){

        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 입찰 포인트",
                pointService.bidPoint(1L,1L,2000)));
    }
}
