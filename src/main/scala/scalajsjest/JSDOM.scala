package scalajsjest

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object JSDOM {

  def register(content:String = "") = {
    if(js.isUndefined(js.Dynamic.global.window) || js.isUndefined(js.Dynamic.global.document)) {
      val jsdom = new JSDOMJS(content)
      js.Dynamic.global.window = jsdom.window
      js.Dynamic.global.document = jsdom.window.document
      js.Dynamic.global.navigator = jsdom.window.navigator
      jsdom
    }
  }
}



@js.native
@JSImport("jsdom","JSDOM")
class JSDOMJS(val content:String) extends js.Object {

  val window: js.Dynamic = js.native
}