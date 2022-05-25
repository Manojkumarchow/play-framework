package models
import collection.mutable
object TaskListInMemoryModel {

    private val users = mutable.Map[String, String]("manoj" -> "123", "kumar" -> "123")
    private val tasks = mutable.Map[String, List[String]]("manoj" -> List("Code", "Play", "Listen"))

    def validateUser(userName: String, password: String): Boolean = {
        users.get(userName).map(originalPassword => originalPassword == password).getOrElse(false)
    }

    def createUser(userName: String, password: String): Boolean = {
        if (users.contains(userName)) false else {
            users(userName) = password
            true
        }
    }

    def getTasks(userName: String): Seq[String] = {
        tasks.get(userName).getOrElse(Nil)
    }

    def addTask(userName: String, task: String): Unit = {
        tasks(userName) = task :: tasks.get(userName).getOrElse(Nil)
    }

    def removeTask(userName: String, index: Int): Unit = ???

}