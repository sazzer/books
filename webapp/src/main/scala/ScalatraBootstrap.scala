import org.scalatra._
import javax.servlet.ServletContext

/**
 * Bootstrap the Scalatra system
 */
class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val accessTokenService = new uk.co.grahamcox.books.oauth2.AccessTokenService

    context.mount(new uk.co.grahamcox.books.controllers.debug.DebugServlet, "/debug")
    context.mount(new uk.co.grahamcox.books.controllers.oauth2.OAuth2Servlet(accessTokenService), "/oauth/2.0")
  }
}
