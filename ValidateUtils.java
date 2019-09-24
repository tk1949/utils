import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils
{
    public static boolean verify(String _0) {
        return _0 != null && ! _0.isEmpty());
    }

    public static boolean verify(double _0) {
        return _0 > 0;
    }

    public static boolean verify(boolean _0) {
        return _0;
    }

    public static boolean verify(Collection _0) {
        return _0 != null && !_0.isEmpty();
    }

    public static boolean verify(Map _0) {
        return _0 != null && !_0.isEmpty();
    }

    public static <E> boolean verify(E[] _0) {
        return _0 != null && _0.length > 0;
    }

    public static <E> boolean isNull(E e) {
        return e == null;
    }

    public static <E> boolean nonNull(E e) {
        return e != null;
    }

    private static final Pattern pattern =
            Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    public static boolean IP(String ip) {
        Matcher m = pattern.matcher(ip);
        return m.matches();
    }
}