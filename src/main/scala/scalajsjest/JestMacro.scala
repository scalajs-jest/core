package scalajsjest

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

private[scalajsjest] object JestMacro {

  def testMacroImpl(c: whitebox.Context)(name: c.Tree, func: c.Tree): c.Tree = {

    import c.universe._
    q"""
      scalajsjest.JestGlobal.test(${c.internal.enclosingOwner.fullName
      .split("\\.")
      .init
      .mkString(".")}+"."+$name,$func)
      """
  }

  def testOnlyMacroImpl(c: whitebox.Context)(name: c.Tree,
                                             func: c.Tree): c.Tree = {
    import c.universe._
    q"""
      scalajsjest.JestGlobal.test.only(${c.internal.enclosingOwner.fullName
      .split("\\.")
      .init
      .mkString(".")}+"."+$name,$func)
      """
  }

  def testSkipMacroImpl(c: whitebox.Context)(name: c.Tree,
                                             func: c.Tree): c.Tree = {
    import c.universe._
    q"""
      scalajsjest.JestGlobal.test.skip(${c.internal.enclosingOwner.fullName
      .split("\\.")
      .init
      .mkString(".")}+"."+$name, $func)
      """
  }

  def runMacroImpl(c: whitebox.Context)(): c.Tree = {
    import c.universe._
    val rootPackage = c.internal.enclosingOwner.fullName.split("\\.").head
    var result: List[c.Tree] = List()
    def allTestClasses(name: String): Unit = {
      c.mirror
        .staticPackage(name)
        .info
        .decls
        .foreach(s => {
          if (s.isPackage) allTestClasses(s.fullName)
          else if (s.isClass && !s.isAbstract && s.asClass.toType != typeOf[
                     JestSuite] && s.asClass.toType <:< typeOf[JestSuite]) {
            result :+= q"new ${s.asClass.toType}"
          }
        })
    }
    allTestClasses(rootPackage)
    println(s"CLasses : $result")
    q"""
        ..$result
      """
  }

}
