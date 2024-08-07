package flab.nutridiary.product.constant;

import lombok.Getter;

import java.math.RoundingMode;

@Getter
public class DecimalConstant {
    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
}
