package controllers

import javax.inject.Inject
import play.api._
import play.api.mvc._

class HelloController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

    def hello() = Action { implicit request: Request[_] =>
        // Ok("Hello!")
        // Ok(
        //     <message>
        //         <h1>Hello World!</h1>
        //     </message>
        // )
        Ok(
            <h1>Hello</h1>
        ).as(HTML)
        // NotFound
        // NotFound(<h1>Page not found</h1>)
        // InternalServerError("Oops")
        // Status(488)("Strange response type")
        // Redirect("/hello")
    }
}