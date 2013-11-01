package uk.co.grahamcox.oauth

import com.github.nscala_time.time.Imports._
import java.net.URLEncoder

/**
 * Representation of the details needed for the Authorization header
 */
class Authorization(consumerKey: String, nonce: String, signature: String, signatureMethod: String, timestamp: DateTime, token: String, version:
String) {
  /**
   * Build the actual Authorization string to use from the header values
   * @return the authorization string
   */
  override def toString: String = {
    val headerValues = Map("oauth_consumer_key" -> consumerKey,
      "oauth_nonce" -> nonce,
      "oauth_signature" -> signature,
      "oauth_signature_method" -> signatureMethod,
      "oauth_timestamp" -> (timestamp.withZone(DateTimeZone.UTC).getMillis() / 1000).toString(),
      "oauth_token" -> token,
      "oauth_version" -> version)

    val headerList = headerValues.foldLeft(Seq.empty[String]) {
      case (soFar, (key, value)) => soFar ++ Seq(PercentEncoder.encode(key) + "=\"" + PercentEncoder.encode(value) + "\"")
    } sorted

    headerList.mkString("OAuth ", ", ", "")
  }
}
