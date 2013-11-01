package uk.co.grahamcox.oauth

import org.specs2.mutable._
import com.github.nscala_time.time.Imports._

class AuthorizationSpec extends SpecificationWithJUnit {
  "Authorization" should {
    "return the correct string" in {
      val auth = new Authorization("xvz1evFS4wEEPTGEFPHBog", 
        new Nonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"),
        new Signature("tnnArxj06cWHq44gCs1OSKk/jLY=", "HMAC-SHA1"),
        new DateTime(1318622958000L),
        "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb",
        "1.0")

      auth.toString must beEqualTo("OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"tnnArxj06cWHq44gCs1OSKk%2FjLY%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1318622958\", oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", oauth_version=\"1.0\"")
    }
  }
}

