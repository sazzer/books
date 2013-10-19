package uk.co.grahamcox.books.controllers.debug

import org.scalatra._
import org.json4s._
import org.scalatra.json._
import uk.co.grahamcox.books.buildinfo.BuildInfo

class DebugServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }

  case class Version(name: String, version: String)

  get("/version") {
    Version(
      name = "Books",
      version = BuildInfo.version
    )
  }
}
