# ScalaJS-Jest

scalajs facade for [jest](https://facebook.github.io/jest/)

```scala
// build.sbt
resolvers += Resolver.bintrayRepo("scalajs-jest", "maven")
libraryDependencies += "scalajs-jest" %%% "core" % "replaceThisWithLatestVersionNumberFromReleaseTags"
```

# Docs 

* [Writing Tests](#writing-tests)
* [Running Tests](#running-tests)
* [Community](#community)


### Writing Tests

```scala

class MyTestSuite extends JestSuite {


  beforeEach {
    println(s"Before Each test")
  }

  afterEach {
    println(s"After Each test")
  }

  beforeAll {
    println(s"Before All tests")
  }

  afterAll {
    println(s"After all  tests")
  }

  test("test") {
    val x = 5
    expect(1 + 1).toBe(2)
    expect(x).toBe(5)
  }

  test("test") {
    expect(1 - 1).toBe(0)
  }

  testSkip("skip")  {
    expect(1 - 1).toBe(10)
  }

  test("assertions")  {
    assertions(2)
    expect(2).toBe(2)
    expect(2).toBe(2)
  }

  testAsync("Async") {
    val f = Future { 5 }
    assertions(1)
    expectAsync(f).resolves.toBe(5)
  }


  test("Jest Object") {
    expect(Jest.isMockFunction(() => null)).toBeFalsy()
  }


}



```

If you want skip a particular TestSuite then use  `JestSuiteSkip` 

If you want to run a particular TestSuite Only then use `JestSuiteOnly`


### Running Tests 

To run your tests just call JestRunner.run() in your main method. 

```scala

object TestRunner {

  def main(args: Array[String]): Unit = {
    JestRunner.run()
  }

}


```

 build.sbt 
 
 ```scala
//Test

scalaJSUseMainModuleInitializer in Test := true

scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))

val TEST_FILE = s"./sjs.test.js"

artifactPath in Test in fastOptJS := new File(TEST_FILE)
artifactPath in Test in fullOptJS := new File(TEST_FILE)

val testDev = Def.taskKey[Unit]("test in dev mode")
val testProd = Def.taskKey[Unit]("test in prod mode")

testDev := {
  (fastOptJS in Test).value
  runJest()
}

testProd := {
  (fullOptJS in Test).value
  runJest()
}

def runJest() = {
  import sys.process._
  val jestResult = "npm test".!
  if (jestResult != 0) throw new IllegalStateException("Jest Suite failed")
}

```

package.json

```js
{
  "name": "scalajstest",
  "version": "1.0.0",
  "description": "",
  "scripts": {
    "test": "jest"
  },
  "author": "",
  "license": "ISC",
  "dependencies": {
  
  },
  "devDependencies": {
    "jest": "^20.0.4"
  }
}
``` 

***Test Commands :***

```scala

sbt ~testDev //for dev

or 

sbt ~testProd // for prod

```

### Community

If you have any questions regarding scalajs-jest ,open a thread [here](https://github.com/scalajs-jest/discuss/issues)