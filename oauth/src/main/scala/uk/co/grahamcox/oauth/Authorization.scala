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
  val token: Option[String],
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
      "oauth_version" -> version)
    val tokenValues = token match {
      case Some(t: String) => Map("oauth_token" -> t)
      case None => Map.empty[String, String]
    }

    val headerList = (headerValues ++ tokenValues).foldLeft(Seq.empty[String]) {
      case (soFar, (key, value)) => soFar ++ Seq(PercentEncoder(key) + "=\"" + PercentEncoder(value) + "\"")
    } sorted

    headerList.mkString("OAuth ", ", ", "")
  }
}

/**
 * Companion object to take the pain out of building an Authorization header
 */
object Authorization {
  /**
   * Build an Authorization object for the given request
   * @param request The request to authorize
   * @param consumerKey the consumer key to use
   * @param token The auth token to use
   * @param nonce The nonce to use
   * @param timestamp The timestamp to use
   * @param signingMethod The signing method to use 
   * @param version The OAuth version to use
   */
  def apply(request: Request, 
    consumerKey: Key, 
    token: Option[Key],
    nonce: Nonce = new Nonce(),
    timestamp: DateTime = DateTime.now,
    signingMethod: SigningMethod = new HmacSha1SigningMethod(),
    version: String = "1.0"): Authorization = {
      val signature = Signature(request, 
        consumerKey, 
        token, 
        nonce, 
        timestamp, 
        signingMethod,
        version)
      new Authorization(consumerKey.key, 
        nonce, 
        signature,
        timestamp,
        token.map { t => t.key },
        version)
  }
}
