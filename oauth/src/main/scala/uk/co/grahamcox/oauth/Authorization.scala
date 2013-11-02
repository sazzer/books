package uk.co.grahamcox.oauth

import com.github.nscala_time.time.Imports._
import java.net.URLEncoder

/**
 * Representation of the details needed for the Authorization header
 * @param consumerKey The consumer key to use
 * @param nonce The nonce to use
 * @param signature The signature to use
 * @param signatureMethod The method of the signature
 * @param token The token to use
 * @param version The OAuth version
 */
class Authorization(val consumerKey: String, 
  val nonce: Nonce, 
  val signature: Signature, 
  val timestamp: DateTime,
  val token: String, 
  val version: String) {
  /**
   * Build the actual Authorization string to use from the header values
   * @return the authorization string
   */
  override def toString: String = {
    val headerValues = Map("oauth_consumer_key" -> consumerKey,
      "oauth_nonce" -> nonce.value,
      "oauth_signature" -> signature.value,
      "oauth_signature_method" -> signature.method,
      "oauth_timestamp" -> (timestamp.withZone(DateTimeZone.UTC).getMillis() / 1000).toString(),
      "oauth_token" -> token,
      "oauth_version" -> version)

    val headerList = headerValues.foldLeft(Seq.empty[String]) {
      case (soFar, (key, value)) => soFar ++ Seq(PercentEncoder(key) + "=\"" + PercentEncoder(value) + "\"")
    } sorted

    headerList.mkString("OAuth ", ", ", "")
  }
}
