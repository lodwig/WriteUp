package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private final boolean forceCompactArrangement;
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {1, 0};
    private static final int[] MEDIUM_COUNTS_COMPACT = {0};

    public MultiBrowseCarouselStrategy() {
        this(false);
    }

    public MultiBrowseCarouselStrategy(boolean forceCompactArrangement) {
        this.forceCompactArrangement = forceCompactArrangement;
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        float childMargins;
        float measuredChildSize;
        float availableSpace = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            availableSpace = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins2 = childLayoutParams.topMargin + childLayoutParams.bottomMargin;
        float measuredChildSize2 = child.getMeasuredHeight();
        if (!carousel.isHorizontal()) {
            childMargins = childMargins2;
            measuredChildSize = measuredChildSize2;
        } else {
            float childMargins3 = childLayoutParams.leftMargin + childLayoutParams.rightMargin;
            float measuredChildSize3 = child.getMeasuredWidth();
            childMargins = childMargins3;
            measuredChildSize = measuredChildSize3;
        }
        float smallChildSizeMin = CarouselStrategyHelper.getSmallSizeMin(child.getContext()) + childMargins;
        float smallChildSizeMax = CarouselStrategyHelper.getSmallSizeMax(child.getContext()) + childMargins;
        float targetLargeChildSize = Math.min(measuredChildSize + childMargins, availableSpace);
        float targetSmallChildSize = MathUtils.clamp((measuredChildSize / 3.0f) + childMargins, CarouselStrategyHelper.getSmallSizeMin(child.getContext()) + childMargins, CarouselStrategyHelper.getSmallSizeMax(child.getContext()) + childMargins);
        float targetMediumChildSize = (targetLargeChildSize + targetSmallChildSize) / 2.0f;
        int[] smallCounts = SMALL_COUNTS;
        int[] mediumCounts = this.forceCompactArrangement ? MEDIUM_COUNTS_COMPACT : MEDIUM_COUNTS;
        float minAvailableLargeSpace = (availableSpace - (CarouselStrategyHelper.maxValue(mediumCounts) * targetMediumChildSize)) - (CarouselStrategyHelper.maxValue(smallCounts) * smallChildSizeMax);
        int largeCountMin = (int) Math.max(1.0d, Math.floor(minAvailableLargeSpace / targetLargeChildSize));
        int largeCountMax = (int) Math.ceil(availableSpace / targetLargeChildSize);
        int[] largeCounts = new int[(largeCountMax - largeCountMin) + 1];
        for (int i = 0; i < largeCounts.length; i++) {
            largeCounts[i] = largeCountMax - i;
        }
        Arrangement arrangement = Arrangement.findLowestCostArrangement(availableSpace, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, smallCounts, targetMediumChildSize, mediumCounts, targetLargeChildSize, largeCounts);
        return CarouselStrategyHelper.createLeftAlignedKeylineState(child.getContext(), childMargins, availableSpace, arrangement);
    }
}
