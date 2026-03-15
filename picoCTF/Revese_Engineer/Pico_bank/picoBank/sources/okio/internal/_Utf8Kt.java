package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;

/* compiled from: -Utf8.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class _Utf8Kt {
    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        return commonToUtf8String(bArr, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.String commonToUtf8String(byte[] r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 1040
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal._Utf8Kt.commonToUtf8String(byte[], int, int):java.lang.String");
    }

    public static final byte[] commonAsUtf8ToByteArray(String commonAsUtf8ToByteArray) {
        char cCharAt;
        Intrinsics.checkNotNullParameter(commonAsUtf8ToByteArray, "$this$commonAsUtf8ToByteArray");
        byte[] bytes = new byte[commonAsUtf8ToByteArray.length() * 4];
        int length = commonAsUtf8ToByteArray.length();
        for (int index = 0; index < length; index++) {
            char b0 = commonAsUtf8ToByteArray.charAt(index);
            if (Intrinsics.compare((int) b0, 128) >= 0) {
                int size = index;
                int endIndex$iv = commonAsUtf8ToByteArray.length();
                int index$iv = index;
                while (index$iv < endIndex$iv) {
                    char c$iv = commonAsUtf8ToByteArray.charAt(index$iv);
                    if (Intrinsics.compare((int) c$iv, 128) < 0) {
                        byte c = (byte) c$iv;
                        int size2 = size + 1;
                        bytes[size] = c;
                        index$iv++;
                        while (index$iv < endIndex$iv && Intrinsics.compare((int) commonAsUtf8ToByteArray.charAt(index$iv), 128) < 0) {
                            int index$iv2 = index$iv + 1;
                            byte c2 = (byte) commonAsUtf8ToByteArray.charAt(index$iv);
                            bytes[size2] = c2;
                            index$iv = index$iv2;
                            size2++;
                        }
                        size = size2;
                    } else if (Intrinsics.compare((int) c$iv, 2048) < 0) {
                        byte c3 = (byte) ((c$iv >> 6) | 192);
                        int size3 = size + 1;
                        bytes[size] = c3;
                        byte c4 = (byte) ((c$iv & '?') | 128);
                        bytes[size3] = c4;
                        index$iv++;
                        size = size3 + 1;
                    } else if (55296 > c$iv || 57343 < c$iv) {
                        byte c5 = (byte) ((c$iv >> '\f') | 224);
                        int size4 = size + 1;
                        bytes[size] = c5;
                        byte c6 = (byte) (((c$iv >> 6) & 63) | 128);
                        int size5 = size4 + 1;
                        bytes[size4] = c6;
                        byte c7 = (byte) ((c$iv & '?') | 128);
                        bytes[size5] = c7;
                        index$iv++;
                        size = size5 + 1;
                    } else if (Intrinsics.compare((int) c$iv, 56319) > 0 || endIndex$iv <= index$iv + 1 || 56320 > (cCharAt = commonAsUtf8ToByteArray.charAt(index$iv + 1)) || 57343 < cCharAt) {
                        bytes[size] = Utf8.REPLACEMENT_BYTE;
                        index$iv++;
                        size++;
                    } else {
                        int codePoint$iv = ((c$iv << '\n') + commonAsUtf8ToByteArray.charAt(index$iv + 1)) - 56613888;
                        byte c8 = (byte) ((codePoint$iv >> 18) | 240);
                        int size6 = size + 1;
                        bytes[size] = c8;
                        byte c9 = (byte) (((codePoint$iv >> 12) & 63) | 128);
                        int size7 = size6 + 1;
                        bytes[size6] = c9;
                        byte c10 = (byte) (((codePoint$iv >> 6) & 63) | 128);
                        int size8 = size7 + 1;
                        bytes[size7] = c10;
                        byte c11 = (byte) ((codePoint$iv & 63) | 128);
                        bytes[size8] = c11;
                        index$iv += 2;
                        size = size8 + 1;
                    }
                }
                byte[] bArrCopyOf = Arrays.copyOf(bytes, size);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
                return bArrCopyOf;
            }
            bytes[index] = (byte) b0;
        }
        byte[] bArrCopyOf2 = Arrays.copyOf(bytes, commonAsUtf8ToByteArray.length());
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return bArrCopyOf2;
    }
}
