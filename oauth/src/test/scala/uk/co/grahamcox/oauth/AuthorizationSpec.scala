package uk.co.grahamcox.oauth

import org.specs2.mutable._
import com.github.nscala_time.time.Imports._

class AuthorizationSpec extends SpecificationWithJUnit {
  "Authorization" should {
    "return the correct string" in {
      "from the actual class" in {
        val auth = new Authorization("xvz1evFS4wEEPTGEFPHBog", 
          new Nonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"),
          new Signature("tnnArxj06cWHq44gCs1OSKk/jLY=", "HMAC-SHA1"),
          new DateTime(1318622958000L),
          Some("370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb"),
          "1.0")

        auth.toString must beEqualTo("OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"tnnArxj06cWHq44gCs1OSKk%2FjLY%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1318622958\", oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", oauth_version=\"1.0\"")
      }
      "from the helper object" in {
        val auth = Authorization(new Request("POST", "https://api.twitter.com/1/statuses/update.json", Map(
            "include_entities" -> "true",
            "status" -> "Hello Ladies + Gentlemen, a signed OAuth request!"
          )), 
          new Key("xvz1evFS4wEEPTGEFPHBog", "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw"),
          Some(new Key("370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb", "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE")),
          new Nonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"),
          new DateTime(1318622958000L))

        auth.toString must beEqualTo("OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"tnnArxj06cWHq44gCs1OSKk%2FjLY%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1318622958\", oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", oauth_version=\"1.0\"")
      }
    }
    "build a header easily" in {
      val auth = Authorization(new Request("POST", "https://api.twitter.com/1/statuses/update.json", Map(
          "include_entities" -> "true",
          "status" -> "Hello Ladies + Gentlemen, a signed OAuth request!"
        )), 
        new Key("xvz1evFS4wEEPTGEFPHBog", "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw"),
        Some(new Key("370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb", "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE")))
        
      auth.toString must not be empty
    }
    "build a request_token header easily" in {
      val auth = Authorization(new Request("POST", "https://api.twitter.com/oauth/request_token"), 
        new Key("xvz1evFS4wEEPTGEFPHBog", "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw"))
        
      println("Auth header = " + auth.toString)
      auth.toString must not be empty
    }
  }
}

