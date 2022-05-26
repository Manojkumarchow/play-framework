import org.scalatestplus.play._
import models._

class TaskListInMemoryModelSpec extends PlaySpec {
    "TaskListInMemoryModel" must {
        "do valid login for default user" in {
            TaskListInMemoryModel.validateUser("manoj", "123") mustBe (true)
        }

        "reject login with invalid password" in {
           TaskListInMemoryModel.validateUser("manoj", "password") mustBe (false) 
        }

        "reject login with invalid username" in {
           TaskListInMemoryModel.validateUser("hello", "123") mustBe (false) 
        }

        "reject login with invalid username and password" in {
           TaskListInMemoryModel.validateUser("hello", "1234") mustBe (false) 
        }

        "get correct default task list" in {
            TaskListInMemoryModel.getTasks("manoj") mustBe List("Code", "Play", "Listen")
        }

        "create new user with no tasks" in {
            TaskListInMemoryModel.createUser("pentela", "123") mustBe (true)
            TaskListInMemoryModel.getTasks("pentela") mustBe (Nil)
        }

        "create new user with existing username" in {
            TaskListInMemoryModel.createUser("manoj", "123") mustBe (false)
        }

        "add new Task for default user" in {
            TaskListInMemoryModel.addTask("manoj", "testing")
            TaskListInMemoryModel.getTasks("manoj") mustBe List("testing", "Code", "Play", "Listen")
            TaskListInMemoryModel.getTasks("manoj") must contain ("testing")
        }

        "add new task for new user" in {
            TaskListInMemoryModel.addTask("pentela", "testing1")
            TaskListInMemoryModel.getTasks("pentela") must contain ("testing1")
        }

        "remove task for default user" in {
            TaskListInMemoryModel.removeTask("manoj", 1)

             TaskListInMemoryModel.getTasks("manoj") must not contain ("Code")
        }
    }
}