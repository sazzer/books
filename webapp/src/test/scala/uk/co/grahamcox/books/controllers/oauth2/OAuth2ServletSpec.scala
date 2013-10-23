package uk.co.grahamcox.books.controllers.oauth2

import org.scalatra.test.specs2._
import org.specs2.matcher._
import org.specs2.mutable._
import uk.co.grahamcox.books.oauth2.AccessTokenService

class OAuth2ServletSpec extends SpecificationWithJUnit with MutableScalatraSpec with JsonMatchers {
  addServlet(new OAuth2Servlet(new AccessTokenService), "/oauth/2.0/*")

  "GET /oauth/2.0/token" should {
    "return status 405" in {
      get("/oauth/2.0/token") {
        status must beEqualTo(405)
      }
    }
  }
  "POST /oauth/2.0/token" should {
    "when no grant_type is provided" in {
      post("/oauth/2.0/token") {
        status must beEqualTo(400)
        body must /("error" -> "invalid_request")
      }
    }
    "when an invalid grant_type is provided" in {
      post("/oauth/2.0/token", ("grant_type" -> "invalid")) {
        status must beEqualTo(400)
        body must /("error" -> "unsupported_grant_type")
      }
    }
  }
}
