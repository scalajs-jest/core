package scalajsjest

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

private[scalajsjest] object JestMacro {

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
          else if (s.isClass && !s.isAbstract) {

            if(s.asClass.toType <:< typeOf[JestSuite]) {
              result :+=
                q"""
                 JestGlobal.describe(${s.asClass.fullName.toString} ,() => {
                   new ${s.asClass.toType}
                 })
              """
            } else if(s.asClass.toType <:< typeOf[JestSuiteOnly]) {
              result :+=
                q"""
                 JestGlobal.describe.only(${s.asClass.fullName.toString} ,() => {
                   new ${s.asClass.toType}
                 })
              """
            } else if(s.asClass.toType <:< typeOf[JestSuiteSkip]) {
              result :+=
                q"""
                 JestGlobal.describe.skip(${s.asClass.fullName.toString} ,() => {
                   new ${s.asClass.toType}
                 })
              """
            }
          }

        })
    }
    allTestClasses(rootPackage)
    q"""
        ..$result
      """
  }

}
