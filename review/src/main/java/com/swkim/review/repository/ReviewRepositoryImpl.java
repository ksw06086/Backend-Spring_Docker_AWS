package com.swkim.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swkim.review.model.QReviewEntity;
import com.swkim.review.model.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Double getAvgScoreByRestaurantId(Long restaurantId) {
        return queryFactory.select(QReviewEntity.reviewEntity.score.avg())
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))
                .fetchFirst();
    }

    @Override
    public Slice<ReviewEntity> findSliceByRestaurantId(Long restaurantId, Pageable page) {
        List<ReviewEntity> reviews = queryFactory.select(QReviewEntity.reviewEntity)
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))
                .offset((long) page.getPageNumber() * page.getPageSize())
                .limit(page.getPageSize() + 1)
                .fetch();
        // offset : 0번부터, 10번부터
        // limit : 5개, 10개 가져오라는 것
        // + 1을 해주는 이유 -> 다음게 있는지 보려고 즉, 5개 가져오라고 하면 6개 가져옴
        // hasNext - 10개 가져오라고 했을 때 11개 가져올 수 있다면 다음 페이지도 요청할 수 있다 라는 것을 알려줌

        return new SliceImpl<>(
                reviews.stream().limit(page.getPageSize()).toList(),
                page,
                reviews.size() > page.getPageSize()
        );
    }
}
