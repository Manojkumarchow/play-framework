package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index());
  }

  // this function name is used in routes
  def hello(name: String) = Action { implicit request =>
    Ok(views.html.hai(name));
    // view name - file name
  }

  def echo() = Action { implicit request: Request[_] =>
    Ok("Got request [" + request + "]")
  }

  def product(prodType: String, prodNumber: Int) = Action { implicit request: Request[_] =>
    Ok(s"Product Type is: $prodType and Product Number is: $prodNumber")
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }
}
