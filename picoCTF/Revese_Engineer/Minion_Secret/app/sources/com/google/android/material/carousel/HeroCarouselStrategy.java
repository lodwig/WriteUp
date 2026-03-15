package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class HeroCarouselStrategy extends CarouselStrategy {
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {0, 1};

    @Override // com.google.android.material.carousel.CarouselStrategy
    KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        int availableSpace = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            availableSpace = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins = childLayoutParams.topMargin + childLayoutParams.bottomMargin;
        float measuredChildSize = child.getMeasuredWidth() * 2;
        if (carousel.isHorizontal()) {
            childMargins = childLayoutParams.leftMargin + childLayoutParams.rightMargin;
            measuredChildSize = child.getMeasuredHeight() * 2;
        }
        float smallChildSizeMin = CarouselStrategyHelper.getSmallSizeMin(child.getContext()) + childMargins;
        float smallChildSizeMax = CarouselStrategyHelper.getSmallSizeMax(child.getContext()) + childMargins;
        float targetLargeChildSize = Math.min(measuredChildSize + childMargins, availableSpace);
        float targetSmallChildSize = MathUtils.clamp((measuredChildSize / 3.0f) + childMargins, CarouselStrategyHelper.getSmallSizeMin(child.getContext()) + childMargins, CarouselStrategyHelper.getSmallSizeMax(child.getContext()) + childMargins);
        float targetMediumChildSize = (targetLargeChildSize + targetSmallChildSize) / 2.0f;
        float minAvailableLargeSpace = availableSpace - (CarouselStrategyHelper.maxValue(SMALL_COUNTS) * smallChildSizeMax);
        int largeCountMin = (int) Math.max(1.0d, Math.floor(minAvailableLargeSpace / targetLargeChildSize));
        int largeCountMax = (int) Math.ceil(availableSpace / targetLargeChildSize);
        int[] largeCounts = new int[(largeCountMax - largeCountMin) + 1];
        for (int i = 0; i < largeCounts.length; i++) {
            largeCounts[i] = largeCountMin + i;
        }
        Arrangement arrangement = Arrangement.findLowestCostArrangement(availableSpace, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, SMALL_COUNTS, targetMediumChildSize, MEDIUM_COUNTS, targetLargeChildSize, largeCounts);
        return CarouselStrategyHelper.createLeftAlignedKeylineState(child.getContext(), childMargins, availableSpace, arrangement);
    }
}
