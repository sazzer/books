package uk.co.grahamcox.oauth

/**
 * Encode a given string following the percent encoding rules in RFC-3986
 */
object PercentEncoder {
  /** The collection of bytes that are considered safe */
  private val safeBytes: Seq[Byte] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~".getBytes.toSeq
  /**
   * Actually encode the string
   * @param input The string to encode
   * @return the encoded string
   */
  def encode(input: String) = {
    input.getBytes().toSeq.map {
      _ match {
        case b if safeBytes.contains(b) => b.toChar
        case b => "%" + "%02X".format(b)
      }
    } mkString("")
  }
}
