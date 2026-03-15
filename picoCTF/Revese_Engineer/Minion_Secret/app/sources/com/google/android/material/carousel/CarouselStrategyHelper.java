package com.google.android.material.carousel;

import android.content.Context;
import com.google.android.material.R;
import com.google.android.material.carousel.KeylineState;

/* loaded from: classes.dex */
final class CarouselStrategyHelper {
    private CarouselStrategyHelper() {
    }

    static float getExtraSmallSize(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_gone_size);
    }

    static float getSmallSizeMin(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
    }

    static float getSmallSizeMax(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
    }

    static KeylineState createLeftAlignedKeylineState(Context context, float childHorizontalMargins, float availableSpace, Arrangement arrangement) {
        float extraSmallChildWidth = getExtraSmallSize(context) + childHorizontalMargins;
        float extraSmallHeadCenterX = 0.0f - (extraSmallChildWidth / 2.0f);
        float largeStartCenterX = (arrangement.largeSize / 2.0f) + 0.0f;
        float largeEndCenterX = largeStartCenterX + (Math.max(0, arrangement.largeCount - 1) * arrangement.largeSize);
        float start = (arrangement.largeSize / 2.0f) + largeEndCenterX;
        float mediumCenterX = arrangement.mediumCount > 0 ? (arrangement.mediumSize / 2.0f) + start : largeEndCenterX;
        float smallStartCenterX = arrangement.smallCount > 0 ? (arrangement.smallSize / 2.0f) + (arrangement.mediumCount > 0 ? (arrangement.mediumSize / 2.0f) + mediumCenterX : start) : mediumCenterX;
        float extraSmallTailCenterX = availableSpace + (extraSmallChildWidth / 2.0f);
        float extraSmallMask = CarouselStrategy.getChildMaskPercentage(extraSmallChildWidth, arrangement.largeSize, childHorizontalMargins);
        float smallMask = CarouselStrategy.getChildMaskPercentage(arrangement.smallSize, arrangement.largeSize, childHorizontalMargins);
        float mediumMask = CarouselStrategy.getChildMaskPercentage(arrangement.mediumSize, arrangement.largeSize, childHorizontalMargins);
        KeylineState.Builder builder = new KeylineState.Builder(arrangement.largeSize).addKeyline(extraSmallHeadCenterX, extraSmallMask, extraSmallChildWidth).addKeylineRange(largeStartCenterX, 0.0f, arrangement.largeSize, arrangement.largeCount, true);
        if (arrangement.mediumCount > 0) {
            builder.addKeyline(mediumCenterX, mediumMask, arrangement.mediumSize);
        }
        if (arrangement.smallCount > 0) {
            builder.addKeylineRange(smallStartCenterX, smallMask, arrangement.smallSize, arrangement.smallCount);
        }
        builder.addKeyline(extraSmallTailCenterX, extraSmallMask, extraSmallChildWidth);
        return builder.build();
    }

    static int maxValue(int[] array) {
        int largest = Integer.MIN_VALUE;
        for (int j : array) {
            if (j > largest) {
                largest = j;
            }
        }
        return largest;
    }
}
