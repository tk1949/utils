import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public class ShaUtils
{
    public static byte[] sha3(byte[] _0) {
        byte[] _2 = new byte[_0.length];
        System.arraycopy(_0, 0, _2, 0, _0.length);

        SHA3.DigestSHA3 md = new SHA3.DigestSHA3(512);
        md.update(_2);

        return md.digest();
    }

    public static byte[] sha3(byte[] _0, byte[] _1) {
        byte[] _2 = new byte[_0.length + _1.length];
        System.arraycopy(_0, 0, _2, 0, _0.length);
        System.arraycopy(_1, 0, _2, _1.length, _1.length);

        SHA3.DigestSHA3 md = new SHA3.DigestSHA3(512);
        md.update(_2);

        return md.digest();
    }

    public static String sha3(String text) {
        SHA3.DigestSHA3 md = new SHA3.DigestSHA3(512);
        md.update(text.getBytes(StandardCharsets.UTF_8));

        byte[] digest = md.digest();
        return Hex.toHexString(digest);
    }
}