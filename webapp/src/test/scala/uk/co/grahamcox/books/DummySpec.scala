package uk.co.grahamcox.books

import org.specs2.mutable._
import uk.co.grahamcox.books.buildinfo.BuildInfo

class DummySpec extends Specification {
  "BuildInfo" should {
    "have a version" in {
      BuildInfo.version must not beNull
    }
  }
}
