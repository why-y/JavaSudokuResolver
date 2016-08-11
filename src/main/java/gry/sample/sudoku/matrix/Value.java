package gry.sample.sudoku.matrix;

/**
 * Created by gry on 01.07.16.
 */
public enum Value {

    UNSET(0), ONE(1), TWO(2), THREE(3), FOUR(4),
    FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    private int value;

    Value(int value) {
        this.value = value;
    }

    public int toInt() {
        return this.value;
    }

    static public Value fromInt(int value) {
        switch (value) {
            case 0: return UNSET;
            case 1: return ONE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIVE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            default: throw new IllegalArgumentException("Invalid value!");
        }
    }

}
