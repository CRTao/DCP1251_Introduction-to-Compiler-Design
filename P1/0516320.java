new Program() {
  MainClass = new MainClass() {
    ClassName = "Factorial",
    Args = "a",
    Statement = new PrintStatement() {
      HasLn = True,
      Expression = new MethodCallExpression() {
        Object = new ConstructorExpression() {
          Size = null,
          ClassName = "Fac"
        },
        Identifier = "ComputeFac",
        Arguments = new List<Expression>() {
          new IntegerExpression() { Value = "10" }
        }
      }
    }
  },
  ClassDeclarations = new List<ClassDeclaration>() {
    new ClassDeclaration() {
      ClassName = "Fac",
      Extends = null,
      VarDeclarations = new List<VarDeclaration>() {},
      MethodDeclarations = new List<MethodDeclaration>() {
        new MethodDeclaration() {
          ReturnType = new IntegerType() {},
          MethodName = "ComputeFac",
          Parameters = new List<Parameter>() {
            new Parameter() {
              Type = new IntegerType() {},
              Identifier = "num"
            }
          },
          VarDeclarations = new List<VarDeclaration>() {
            new VarDeclaration() {
              Type = new IntegerType() {},
              Identifier = "num_aux"
            }
          },
          Statements = new List<Statement>() {
            new IfStatement() {
              Condition = new LessThanExpression() {
                Lhs = new IdentifierExpression() { Name = "num"},
                Rhs = new IntegerExpression() { Value = "1" }
              },
              TrueStatement = new AssignmentStatement() {
                Variable = "num_aux",
                Index = null,
                Expression = new IntegerExpression() { Value = "1" }
              },
              FalseStatement = new AssignmentStatement() {
                Variable = "num_aux",
                Index = null,
                Expression = new TimesExpression() {
                  Lhs = new IdentifierExpression() { Name = "num"},
                  Rhs = new GroupExpression() {
                    Expression = new MethodCallExpression() {
                      Object = new ThisExpression() { },
                      Identifier = "ComputeFac",
                      Arguments = new List<Expression>() {
                        new AdditiveExpression() {
                          Lhs = new IdentifierExpression() { Name = "num"},
                          Operator = "-",
                          Rhs = new IntegerExpression() { Value = "1" }
                        }
                      }
                    }
                  }
                }
              }
            }
          },
          Returns = new IdentifierExpression() { Name = "num_aux" }
        }
      }
    }
  }
}