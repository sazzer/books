package uk.co.grahamcox.books.controllers.debug

import org.scalatra._
import uk.co.grahamcox.books.buildinfo.BuildInfo
import uk.co.grahamcox.books.controllers.BaseServlet

/**
 * Simple servlet to return debug information to the client
 */
class DebugServlet extends BaseServlet {
  case class Version(name: String, version: String)

  get("/version") {
    Version(
      name = "Books",
      version = BuildInfo.version
    )
  }
}
