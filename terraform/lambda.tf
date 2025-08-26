resource "aws_lambda_function" "user_registration" {
  function_name = "user-registration-${var.env}"
  handler       = "com.example.adapter.lambda.Handler::handleRequest"
  runtime       = "java17"
  role          = aws_iam_role.lambda_exec_role.arn

  filename         = "${path.module}/../build/quarkus-app/app/userregistration-1.0-SNAPSHOT.jar"
  source_code_hash = filebase64sha256("${path.module}/../build/quarkus-app/app/userregistration-1.0-SNAPSHOT.jar")

  environment {
    variables = {
      USERS_TABLE_NAME = aws_dynamodb_table.user_table.name
    }
  }
}