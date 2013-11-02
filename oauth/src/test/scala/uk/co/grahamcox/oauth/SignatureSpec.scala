package uk.co.grahamcox.oauth

import org.specs2.mutable._
import com.github.nscala_time.time.Imports._

class SignatureSpec extends SpecificationWithJUnit {
  "Signature" should {
    "correctly sign a request" in {
      val sig = Signature(new Request("POST", "https://api.twitter.com/1/statuses/update.json", Map(
        "include_entities" -> "true",
        "status" -> "Hello Ladies + Gentlemen, a signed OAuth request!"
        )), 
        new Key("xvz1evFS4wEEPTGEFPHBog", "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw"),
        Some(new Key("370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb", "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE")),
        new Nonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"),
        new DateTime(1318622958000L))

      sig.method must beEqualTo("HMAC-SHA1")
      sig.value must beEqualTo("tnnArxj06cWHq44gCs1OSKk/jLY=")
    }
  }
}



