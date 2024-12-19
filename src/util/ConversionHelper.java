package util;

public class ConversionHelper {
    public static boolean toFloat(String value) {
        if (value == null || value.isEmpty())
            return false;

        try {
            Float.parseFloat(value);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean toByte(String value) {
        if (value == null) {
            return false;
        }

        try {
            Byte.parseByte(value);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
