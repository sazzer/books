package uk.co.grahamcox.oauth

import org.specs2.mutable._

class PercentEncoderSpec extends SpecificationWithJUnit {
  "PercentEncoder" should {
    "do nothing to a safe string" in {
      val input = "0123456789"
      val output = PercentEncoder(input)
      output must beEqualTo("0123456789")
    }
    "convert an unsafe string" in {
      val input = "!\"£$%^&*()"
      val output = PercentEncoder(input)
      output must beEqualTo("%21%22%C2%A3%24%25%5E%26%2A%28%29")
    }
    "convert a Base64 String" in {
      val input = "tnnArxj06cWHq44gCs1OSKk/jLY="
      val output = PercentEncoder(input)
      output must beEqualTo("tnnArxj06cWHq44gCs1OSKk%2FjLY%3D")
    }
  }
}


