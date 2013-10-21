import org.scalatra._
import javax.servlet.ServletContext
import grizzled.slf4j.Logger

/**
 * Bootstrap the Scalatra system
 */
class ScalatraBootstrap extends LifeCycle {
  private val logger = Logger[this.type]

  override def init(context: ServletContext) {
    val accessTokenService = new uk.co.grahamcox.books.oauth2.AccessTokenService

    val servlets = Map(
      "/debug" -> new uk.co.grahamcox.books.controllers.debug.DebugServlet,
      "/oauth/2.0" -> new uk.co.grahamcox.books.controllers.oauth2.OAuth2Servlet(accessTokenService)
    )

    servlets.foreach {
      case (key, servlet) => {
        logger.info(s"Mounting servlet $servlet on $key")
        context.mount(servlet, key)
      }
    }
  }
}
