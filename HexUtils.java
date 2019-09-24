import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HexUtils {

    private static final HexEncoder encoder = new HexEncoder();

    public static String toHexString(byte[] data) {
        return toHexString(data, 0, data.length);
    }

    public static String toHexString(byte[] data, int off, int length) {
        byte[] encoded = encode(data, off, length);
        char[] chars = new char[encoded.length];
        for (int i = 0; i != chars.length; i++) {
            chars[i] = (char) (encoded[i] & 0xff);
        }
        return new String(chars);
    }

    private static byte[] encode(byte[] data, int off, int length) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            encoder.encode(data, off, length, out);
        } catch (Exception e) {
            throw new IllegalArgumentException("exception encoding Hex string: " + e.getMessage(), e);
        }
        return out.toByteArray();
    }

    public static byte[] decode(String data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            encoder.decode(data, out);
        } catch (Exception e) {
            throw new IllegalArgumentException("exception decoding Hex string: " + e.getMessage(), e);
        }
        return out.toByteArray();
    }

    private static class HexEncoder {
        private final byte[] encodingTable =
                {
                        (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                        (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
                };

        private final byte[] decodingTable = new byte[128];

        private void initialiseDecodingTable() {
            for (int i = 0; i < decodingTable.length; i++) {
                decodingTable[i] = (byte) 0xff;
            }

            for (int i = 0; i < encodingTable.length; i++) {
                decodingTable[encodingTable[i]] = (byte) i;
            }

            decodingTable['A'] = decodingTable['a'];
            decodingTable['B'] = decodingTable['b'];
            decodingTable['C'] = decodingTable['c'];
            decodingTable['D'] = decodingTable['d'];
            decodingTable['E'] = decodingTable['e'];
            decodingTable['F'] = decodingTable['f'];
        }

        private HexEncoder() {
            initialiseDecodingTable();
        }

        private int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
            for (int i = off; i < (off + length); i++) {
                int v = data[i] & 0xff;
                out.write(encodingTable[(v >>> 4)]);
                out.write(encodingTable[v & 0xf]);
            }
            return length * 2;
        }

        private static boolean ignore(char c) {
            return c == '\n' || c == '\r' || c == '\t' || c == ' ';
        }

        private int decode(String data, OutputStream out) throws IOException {
            byte b1, b2;
            int length = 0;
            int end = data.length();
            while (end > 0) {
                if (!ignore(data.charAt(end - 1))) {
                    break;
                }
                end--;
            }
            int i = 0;
            while (i < end) {
                while (i < end && ignore(data.charAt(i))) {
                    i++;
                }
                b1 = decodingTable[data.charAt(i++)];
                while (i < end && ignore(data.charAt(i))) {
                    i++;
                }
                b2 = decodingTable[data.charAt(i++)];
                if ((b1 | b2) < 0) {
                    throw new IOException("invalid characters encountered in Hex string");
                }
                out.write((b1 << 4) | b2);
                length++;
            }
            return length;
        }
    }
}