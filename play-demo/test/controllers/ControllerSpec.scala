import org.scalatestplus.play._
import controllers._
import play.api.test.{Helpers, FakeRequest}
import play.api.test.Helpers._
class ControllerSpec  extends PlaySpec {
    "HomeController#index" must {
        "give back expected page" in {
            val controller = new HomeController(Helpers.stubControllerComponents())
            val result = controller.index().apply(FakeRequest())
            val bodyText = contentAsString(result)
            bodyText must include ("Welcome to Play, Manoj!")
        }
    }
}