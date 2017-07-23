package scalajsjest

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.JSConverters._

trait JestSuiteOnly extends BaseSuite
