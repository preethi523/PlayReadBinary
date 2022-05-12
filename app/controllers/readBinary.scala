package controllers

import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import play.libs.Scala
import views.models.TodoListItem

import java.io.{ByteArrayOutputStream, File, FileInputStream, PrintWriter}
import java.nio.file.Paths
import javax.inject.Inject
import scala.collection.mutable
import scala.concurrent.ExecutionContext


class readBinary  @Inject()(val controllerComponents: ControllerComponents)
  extends BaseController {
  //  implicit val todoListJson = Json.format[TodoListItem]
  private val todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", true)
  todoList += TodoListItem(2, "some other value", false)

  implicit val todoListJson = Json.format[TodoListItem]


  def getAll(): Action[AnyContent] = Action {
    if (todoList.isEmpty) NoContent
    else {
      Ok(Json.toJson(todoList))
    }
  }

  def get(): Action[AnyContent] = Action { implicit request=>
    val post =request.body.asRaw
   val a = post.get
    val writer = new PrintWriter(new File("ji.txt"))
    writer.write(a.asBytes().toString)
//    print(a.asBytes().get.toString())
 val file=scala.io.Source.fromFile(a.asFile)
  val out=  file.getLines().toString()
      val inStream = new FileInputStream(a.asFile)
      val outStream = new ByteArrayOutputStream
      try {
        var reading = true
        while ( reading ) {
          inStream.read() match {
            case -1 => reading = false
            case c => print(outStream.write(c))
          }
        }
        outStream.flush()
      }
      finally {
        inStream.close()
      }



    Ok
  }



  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("picture")
      .map { picture =>
        // only get the last part of the filename
        // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
        val filename    = Paths.get(picture.filename).getFileName
        val fileSize    = picture.fileSize
        val contentType = picture.contentType

        picture.ref.copyTo(Paths.get(s"/tmp/picture/$filename"), replace = true)
        Ok("File uploaded")
      }
      .getOrElse {
        Redirect(routes.HomeController.index).flashing("error" -> "Missing file")
      }
  }
}



