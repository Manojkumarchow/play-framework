package controllers

import javax.inject._
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import models.TaskListInMemoryModel
import Utilities._
// import play.api.il8n._

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(override val controllerComponents: MessagesControllerComponents) extends MessagesAbstractController(controllerComponents) {

    val loginForm = Form(mapping(
        "Username" -> text(3, 10),
        "Password" -> text(3)
    )(LoginData.apply)(LoginData.unapply))

    def login() = Action { implicit request =>
        Ok(views.html.login(loginForm))
    }

    def userValidateLoginGet(userName: String, password: String) = Action {
        Ok(s"User Validated -> $userName, : $password")
    }

    def userValidateLoginPost() = Action { implicit request =>
        // since the username and password are now part of the request (not part of the URL), so we don't need to
        // pass them as arguments.
        val postVals = request.body.asFormUrlEncoded
        postVals.map{ args =>
            val userName = args(ApplicationConstants.username).head
            val password = args(ApplicationConstants.password).head
            // Ok(s"User Validated POST -> $userName, : $password")
            if (TaskListInMemoryModel.validateUser(userName, password)) {
                Redirect(routes.TaskList1.taskList).withSession(ApplicationConstants.username -> userName)
            } else {
                Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username or password")
            }
            
        }.getOrElse(Redirect(routes.TaskList1.login()))
        // Ok("")
        // Ok(s"User Validated -> $userName, : $password")
    }

    def taskList = Action { implicit request =>
        // val tasks = List("Videos", "Play", "Code")
        val userNameOption = request.session.get(ApplicationConstants.username)
        userNameOption.map { userName =>
            val tasks = TaskListInMemoryModel.getTasks(userName)
            Ok(views.html.taskList1(tasks))
        }.getOrElse(Redirect(routes.TaskList1.login()))
    }

    def validateLoginForm = Action { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => BadRequest(views.html.login(formWithErrors)),
            loginData => 
            if (TaskListInMemoryModel.validateUser(loginData.username, loginData.password)) {
                Redirect(routes.TaskList1.taskList).withSession(ApplicationConstants.username -> loginData.username)
            } else {
                Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username or password")
            }
        )
    }

    def createUser = Action { implicit request =>
        
        val postVals = request.body.asFormUrlEncoded
        postVals.map{ args =>
            val userName = args(ApplicationConstants.username).head
            val password = args(ApplicationConstants.password).head
            // Ok(s"User Validated POST -> $userName, : $password")
            if (TaskListInMemoryModel.createUser(userName, password)) {
                Redirect(routes.TaskList1.taskList).withSession(ApplicationConstants.username -> userName)
            } else {
                Redirect(routes.TaskList1.login()).flashing("error" -> "User creation failed")
            }
            
        }.getOrElse(Redirect(routes.TaskList1.login()))
        // Ok("")
        // Ok(s"User Validated -> $userName, : $password")
    }

    def addTask = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        val userNameOption = request.session.get(ApplicationConstants.username)
        userNameOption.map{ userName =>
            postVals.map{ args =>
                val task = args(ApplicationConstants.newTask).head
                TaskListInMemoryModel.addTask(userName, task)
                Redirect(routes.TaskList1.taskList)
            }.getOrElse(Redirect(routes.TaskList1.taskList))
        }.getOrElse(Redirect(routes.TaskList1.login()))
        
    }

    def deleteTask = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        val userNameOption = request.session.get(ApplicationConstants.username)
        userNameOption.map{ userName =>
            postVals.map{ args =>
                val taskIndex = args("index").head.toInt
                TaskListInMemoryModel.removeTask(userName, taskIndex)
                Redirect(routes.TaskList1.taskList)
            }.getOrElse(Redirect(routes.TaskList1.taskList))
        }.getOrElse(Redirect(routes.TaskList1.login()))
    }

    def logout = Action { implicit request =>
        Redirect(routes.TaskList1.login()).withNewSession
    }
}