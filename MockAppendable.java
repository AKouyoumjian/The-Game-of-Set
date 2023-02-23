import java.io.IOException;

/**
 * Class for mock of an appendable to test when transmission to view or to read from Readable
 * fails, playGame throws IllegalStateException not IOException.
 *
 */
public class MockAppendable implements Appendable {

  /**
   * Overrides the append method to throw IOException.
   *
   * @param csq The character sequence to append.  If {@code csq} is
   *            {@code null}, then the four characters {@code "null"} are
   *            appended to this Appendable.
   * @return will not return anything
   * @throws IOException always throws
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("mock exception");
  }

  /**
   * Overrides the append method to throw IOException.
   *
   * @param csq   The character sequence from which a subsequence will be
   *              appended.  If {@code csq} is {@code null}, then characters
   *              will be appended as if {@code csq} contained the four
   *              characters {@code "null"}.
   * @param start The index of the first character in the subsequence
   * @param end   The index of the character following the last character in the
   *              subsequence
   * @return nothing
   * @throws IOException always throws
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("mock exception");
  }

  /**
   * Overrides the append method to throw IOException.
   *
   * @param c The character to append
   * @return nothing
   * @throws IOException throws the exception every time
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("mock exception");
  }
}
