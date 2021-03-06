package uk.co.grahamcox.books.controllers.oauth2

import com.github.nscala_time.time.Imports._
import org.scalatra._
import uk.co.grahamcox.books.buildinfo.BuildInfo
import uk.co.grahamcox.books.controllers.BaseServlet
import uk.co.grahamcox.books.oauth2._

/**
 * Controller for managing OAuth 2.0 authentication (RFC-6749)
 */
class OAuth2Servlet(accessTokenService: AccessTokenService) extends BaseServlet {
  /**
   * Response indicating that an Access Token has been issued
   * @param access_token The actual access token
   * @param token_type The type of access token
   * @param expires_in The number of seconds until the token expires
   * @param refresh_token A refresh token to obtain a new access token with
   * @param scope The scopes of the access token
   */
  case class AccessTokenResponse(access_token: String, token_type: String, expires_in: Long, refresh_token: Option[String] = None, scope:
  Option[String] = None)

  /**
   * Companion object to make creating AccessTokenResponse objects easier
   */
  object AccessTokenResponse {
    /**
     * Create an AccessTokenResponse from a Token
     * @param token The token to construct the response from
     * @return the response
     */
    def apply(token: Token): AccessTokenResponse = AccessTokenResponse(
      token.accessToken.token,
      token.tokenType.tokenType,
      (DateTime.now to token.expiry).duration.seconds,
      token.refreshToken.map { rt => rt.token },
      token.scopes match {
        case Nil => None
        case scopes => Some(scopes.map {
          scope: Scope => scope.scope
        } mkString(" "))
      }
    )
  }
  /**
   * Representation of an error from an OAuth 2.0 request
   * @param error The actual error code
   * @param error_description The error description
   * @param error_uri A URI to describe the error
   */
  case class ErrorResponse(error: String, error_description: Option[String] = None, error_uri: Option[String] = None)
  /**
   * Companion object to build the various error responses
   */
  object ErrorResponse {
    /**
     * Build the OAuth 2.0 error for an Unsupported Grant Type
     * @param grantType the grant type that is unsupported
     * @return the error object
     */
    def unsupportedGrantType(grantType: String): ErrorResponse = ErrorResponse(error = "unsupported_grant_type",
      error_description = Some(s"Grant type specified is not valid for this request: $grantType"))
    /**
     * Build the OAuth 2.0 error for when a required parameter is missing
     * @param param The param that was required
     * @return the error object
     */
    def missingParam(param: String): ErrorResponse = ErrorResponse(error = "invalid_request",
      error_description = Some(s"A required parameter was not supplied: $param"))
    /**
     * Build the OAuth 2.0 error for when a grant failed
     * @param error The description of the error
     * @return the error object
     */
    def invalidGrant(error: String): ErrorResponse = ErrorResponse(error = "invalid_grant", error_description = Some(error))
  }

  /**
   * Perform a Resource Owner Password Credentials Grant - taken from RFC-6749 section 4.3
   * @param username The username to use
   * @param password The password to use
   * @param scopes The scopes of the grant
   * @return Either a successful grant or an Error Response
   */
  def resourceOwnerPasswordCredentialsGrant(username: String, password: String, scopes: Option[Seq[String]]): Either[AccessTokenResponse, ErrorResponse] = {
    val request = ResourceOwnerPasswordCredentials(username, password, scopes.getOrElse(Nil).map {
      scope: String => Scope(scope)
    })

    Left(AccessTokenResponse(accessTokenService.grant(request)))
    }

  post("/token") {
    params.get("grant_type") match {
      case Some("password") => {
        val username: String = params.getOrElse("username", halt(BadRequest(ErrorResponse.missingParam("username"))))
        val password: String = params.getOrElse("password", halt(BadRequest(ErrorResponse.missingParam("password"))))
        val scopes: Option[Seq[String]] = params.get("scope").map {
          s: String => s.split(" ").toSeq
        }
        resourceOwnerPasswordCredentialsGrant(username, password, scopes) match {
          case Left(result) => Ok(result)
          case Right(error) => BadRequest(error)
        }
      }
      case Some(grantType: String) => BadRequest(ErrorResponse.unsupportedGrantType(grantType))
      case None => BadRequest(ErrorResponse.missingParam("grant_type"))
    }
  }
}

