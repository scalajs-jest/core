package scalajsjest

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.JSConverters._

trait JestSuite {

  def test(name:String)(func : => Unit): Unit = macro JestMacro.testMacroImpl

  def testAsync(name:String)(func : => Any): Unit = macro JestMacro.testMacroImpl

  def testOnly(name:String)(func : => Unit): Unit = macro JestMacro.testOnlyMacroImpl

  def testSkip(name:String)(func : => Unit): Unit = macro JestMacro.testSkipMacroImpl

  def testOnlyAsync(name:String)(func : => Any): Unit = macro JestMacro.testOnlyMacroImpl

  def testSkipAsync(name:String)(func : => Any): Unit = macro JestMacro.testSkipMacroImpl

  def expect[T](value:T) = JestGlobal.expect(value)

  def expectAsync[T](value:Future[T]) = JestGlobal.expectAsync(value.toJSPromise)

  def assertions(num: Int) = JestGlobal.expect.assertions(num)
}
