package uk.co.grahamcox.books.oauth2

import com.github.nscala_time.time.Imports._

/**
 * Simple type to represent the type of a token
 * @param tokenType the token type
 */
case class TokenType(tokenType: String)
/**
 * Simple type to represent an Access Token
 * @param token The access token
 */
case class AccessToken(token: String)
/**
 * Simple type to represent a Refresh Token
 * @param token The refresh token
 */
case class RefreshToken(token: String)
/**
 * Simple type to represent a token scope
 * @param scope The scope
 */
case class Scope(scope: String)

/**
 * Input request for an access token using the Resource Owner Password Credentials grant mechanism
 * @param username The username to use
 * @param password The password to use
 * @param scopes The scopes to request
 */
case class ResourceOwnerPasswordCredentials(username: String, password: String, scopes: Seq[Scope] = Nil)
/**
 * Response indicating that an Access Token has been issued
 * @param access_token The actual access token
 * @param token_type The type of access token
 * @param expires_in The number of seconds until the token expires
 * @param refresh_token A refresh token to obtain a new access token with
 * @param scope The scopes of the access token
 */
case class Token(accessToken: AccessToken,
  tokenType: TokenType,
  expiry: DateTime,
  refreshToken: Option[RefreshToken] = None,
  scopes: Seq[Scope] = Nil)
/**
 * The service that is responsible for issuing access tokens
 */
class AccessTokenService {
  /**
   * Grant a token based on a Resource Owner Password Credentials grant, from RFC-6749 section 4.3
   * @param grant the grant details
   * @return the token
   */
  def grant(grant: ResourceOwnerPasswordCredentials): Token = Token(
    accessToken = AccessToken("abcdef"),
    tokenType = TokenType("bearer"),
    expiry = DateTime.now + 1.hour,
    refreshToken = Some(RefreshToken("fedcba")),
    scopes = grant.scopes)
}
