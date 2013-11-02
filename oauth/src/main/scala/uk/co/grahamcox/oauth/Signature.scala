package uk.co.grahamcox.oauth

import com.github.nscala_time.time.Imports._
import javax.crypto._
import org.apache.commons.codec.binary.Base64
import grizzled.slf4j.Logger

/**
 * Base class to represent the Signing Method
 */
abstract class SigningMethod {
  /**
   * Sign the given value with the given key
   * @param value The value to sign
   * @param key The key to sign it with
   * @return The signed value
   */
  def sign(value: String, key: String): String
  /**
   * Get the name of the signing method
   * @return the name
   */
  def name: String
}

/**
 * Implementation of the HMAC-SHA1 Signing Method
 */
class HmacSha1SigningMethod extends SigningMethod {
  /** The name to give the Java Crypto API for the Mac to use */
  private val MacName = "HmacSHA1"
  /**
   * Sign the given value with the given key
   * @param value The value to sign
   * @param key The key to sign it with
   * @return The signed value
   */
  def sign(value: String, key: String): String = {
    val mac = Mac.getInstance(MacName)
    mac.init(new spec.SecretKeySpec(key.getBytes(), MacName))
    val signed = mac.doFinal(value.getBytes())
    Base64.encodeBase64String(signed)
  }
  /**
   * Get the name of the signing method
   * @return the name
   */
  def name: String = "HMAC-SHA1"
}
/**
 * Representation of a Signature in the OAuth transaction
 * @param value The value of the signature
 * @param method The method for the signature
 */
class Signature(val value: String, val method: String)

/**
 * Companion object to create a Signature
 */
object Signature {
  /** The logger to use */
  val logger = Logger[this.type]

  /**
   * Actually build the signature from the component pieces
   * @param request The details of the request
   * @param nonce The nonce to use
   * @param timestamp The timestamp to use
   * @param method The signature method to use
   * @param version The OAuth version to use
   */
  def apply(request: Request, 
    consumerKey: Key, 
    token: Option[Key] = None,
    nonce: Nonce,
    timestamp: DateTime,
    method: SigningMethod = new HmacSha1SigningMethod(),
    version: String = "1.0"): Signature = {

    val oauthParams = Map("oauth_consumer_key" -> consumerKey.key, 
      "oauth_nonce" -> nonce.value,
      "oauth_signature_method" -> method.name,
      "oauth_timestamp" -> (timestamp.withZone(DateTimeZone.UTC).getMillis() / 1000).toString(),
      "oauth_version" -> version)

    val tokenParams = token match {
      case Some(k: Key) => Map("oauth_token" -> k.key)
      case None => Map.empty[String, String]
    }

    val parameters = (oauthParams ++ tokenParams ++ request.parameters).foldLeft(Seq.empty[String]) {
      case (soFar, (k, v)) => {
        soFar ++ Seq(PercentEncoder(k) + "=" + PercentEncoder(v))
      }
    } sorted
    val paramString = parameters.mkString("&")
    logger.debug(s"Param String = $paramString")

    val signatureString = Seq(request.method, PercentEncoder(request.url), PercentEncoder(paramString)).mkString("&")
    logger.debug(s"Signature String = $signatureString")

    val signingKey = PercentEncoder(consumerKey.secret) + "&" + (token match {
      case Some(k: Key) => PercentEncoder(k.secret)
      case None => ""
    })
    logger.debug(s"Signing key = $signingKey")

    new Signature(method.sign(signatureString, signingKey), method.name)
  }
}
