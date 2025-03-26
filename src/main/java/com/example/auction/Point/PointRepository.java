package com.example.auction.Point;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    default Point findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.POINT_NOT_FOUND));
    }

    @Query("SELECT p.totalPoint from Point p where p.userId = :userId ORDER BY p.id desc limit 1")
    Optional<Integer> findByLastTotalPoint(Long userId);

    Page<Point> findByUserId(Long userId, Pageable pageable);
}
