package com.example.auction.Point;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Point.Dto.PointEarnResponseDto;
import com.example.auction.Point.Dto.PointResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    /**
     * <p>유저 포인트 적립</p>
     * @param userDetails 유저 Principal 객체 {@link UserDetailsImpl}
     * @return PointEarnResponseDto 포인트 적립 응답Dto {@link PointEarnResponseDto}
     */
    @PostMapping("/earn")
    public ResponseEntity<CommonResponseBody<PointEarnResponseDto>> earnPoint(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok().body(new CommonResponseBody<>("포인트 적립",
                pointService.earnPoint(userDetails.getUser().getId(),1000)));
    }

    /**
     * <p>유저 총합 포인트</p>
     * @param userDetails 유저 Principal 객체 {@link UserDetailsImpl}
     * @return Integer
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<Integer>> getUserTotalPoint(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("총합 포인트",
                pointService.lastTotalPoint(userDetails.getUser().getId())));
    }

    /**
     * <p>유저 포인트내역 리스트</p>
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @param userDetails userDetails 유저 Principal 객체 {@link UserDetailsImpl}
     * @return Page<PointResponseDto>
     */
    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Page<PointResponseDto>>> getPointList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok().body(new CommonResponseBody<>("유저 포인트내역 리스트",
                pointService.getPointList(userDetails.getUser().getId(),page,size)));
    }

}
