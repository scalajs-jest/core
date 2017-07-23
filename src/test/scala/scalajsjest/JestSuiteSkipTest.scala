package scalajsjest



class JestSuiteSkipTest extends JestSuiteSkip {

  test("I fail")  {
    expect(1 - 1).toBe(10)
  }


}
