package scalajsjest

import scala.concurrent.Future
import scalajs.concurrent.JSExecutionContext.Implicits.queue

class JestSuiteTest extends JestSuite {

 private var bEach = true

 private var bAll = true

  beforeEach {
    assert(bEach)
    bEach = false
  }

  afterEach {
    assert(!bEach)
    bEach = true
  }

  beforeAll {
    assert(bAll)
    bAll = false
  }

  afterAll {
    println(s"After all  : $bAll")
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
