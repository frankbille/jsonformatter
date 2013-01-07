package dk.frankbille.jsonformatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonFormatter {

	public static void format(File sourceFile, File destinationFile) throws Exception {
		final StringBuilder out = new StringBuilder();

		final FileReader fr = new FileReader(sourceFile);
		final BufferedReader br = new BufferedReader(fr);

		int indent = 0;

		String line = null;
		while ((line = br.readLine()) != null) {
			for (int p = 0; p < line.length(); p++) {
				char c = line.charAt(p);

				switch (c) {
				case ' ':
				case '\n':
				case '\r':
				case '\t':
					// Discard
					break;
				case '{':
				case '[':
					printNewLine(indent, out);
					out.append(c);
					indent++;
					printNewLine(indent, out);
					break;
				case '}':
				case ']':
					indent--;
					printNewLine(indent, out);
					out.append(c);
					break;
				default:
					out.append(c);
					break;
				}
			}
		}

		br.close();

		FileWriter writer = new FileWriter(destinationFile);
		writer.write(out.toString());
		writer.close();
	}

	private static void printNewLine(int indent, StringBuilder out) {
		out.append('\n');
		out.append(repeat("\t", indent));
	}

	/**
	 * COPIED FROM commons-lang 2.6
	 *
     * <p>Repeat a String <code>repeat</code> times to form a
     * new String.</p>
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
     * </pre>
     *
     * @param str  the String to repeat, may be null
     * @param repeat  number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     *  <code>null</code> if null String input
     */
    private static String repeat(String str, int repeat) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return "";
        }
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= 8192) {
            return padding(repeat, str.charAt(0));
        }

        int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1 :
                char ch = str.charAt(0);
                char[] output1 = new char[outputLength];
                for (int i = repeat - 1; i >= 0; i--) {
                    output1[i] = ch;
                }
                return new String(output1);
            case 2 :
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default :
                StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    /**
	 * COPIED FROM commons-lang 2.6
	 *
     * <p>Returns padding using the specified delimiter repeated
     * to a given length.</p>
     *
     * <pre>
     * StringUtils.padding(0, 'e')  = ""
     * StringUtils.padding(3, 'e')  = "eee"
     * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
     * </pre>
     *
     * <p>Note: this method doesn't not support padding with
     * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
     * as they require a pair of <code>char</code>s to be represented.
     * If you are needing to support full I18N of your applications
     * consider using {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param repeat  number of times to repeat delim
     * @param padChar  character to repeat
     * @return String with repeated character
     * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
     * @see #repeat(String, int)
     */
    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

}
