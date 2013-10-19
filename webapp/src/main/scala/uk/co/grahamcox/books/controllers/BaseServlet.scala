package uk.co.grahamcox.books.controllers

import org.scalatra._
import org.json4s._
import org.scalatra.json._
import uk.co.grahamcox.books.buildinfo.BuildInfo

/**
 * Base class for all the servlets
 */
class BaseServlet extends ScalatraServlet with JacksonJsonSupport {
  /** Ensure that we have the correct JSON Formatter */
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }
}

