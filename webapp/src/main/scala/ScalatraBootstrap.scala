import org.scalatra._
import javax.servlet.ServletContext

/**
 * Bootstrap the Scalatra system
 */
class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new uk.co.grahamcox.books.controllers.debug.DebugServlet, "/debug")
    context.mount(new uk.co.grahamcox.books.controllers.oauth2.OAuth2Servlet, "/oauth/2.0")
  }
}
