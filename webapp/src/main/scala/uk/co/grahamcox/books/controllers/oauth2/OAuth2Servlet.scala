package uk.co.grahamcox.books.controllers.oauth2

import org.scalatra._
import uk.co.grahamcox.books.buildinfo.BuildInfo
import uk.co.grahamcox.books.controllers.BaseServlet

/**
 * Controller for managing OAuth 2.0 authentication (RFC-6749)
 */
class OAuth2Servlet extends BaseServlet {
  /**
   * Representation of an error from an OAuth 2.0 request
   * @param error The actual error code
   * @param error_description The error description
   * @param error_uri A URI to describe the error
   */
  case class ErrorResponse(val error: String, error_description: Option[String] = None, error_uri: Option[String] = None)
  /**
   * Companion object to build the various error responses
   */
  object ErrorResponse {
    /**
     * Build the OAuth 2.0 error for an Unsupported Grant Type
     * @param grantType the grant type that is unsupported
     * @return the error object
     */
    def unsupportedGrantType(grantType: String) = new ErrorResponse(error = "unsupported_grant_type", 
      error_description = Some(s"Grant type specified is not valid for this request: $grantType"))
    def missingParam(param: String) = new ErrorResponse(error = "invalid_request",
      error_description = Some(s"A required parameter was not supplied: $param"))
  }

  post("/token") {
    params.get("grant_type") match {
      case Some(grantType: String) => BadRequest(ErrorResponse.unsupportedGrantType(grantType))
      case None => BadRequest(ErrorResponse.missingParam("grant_type"))
    }
  }
}

