resource "aws_lambda_function" "user_registration" {
  function_name = "user-registration-${var.env}"
  handler       = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"
  runtime       = "java17"
  role          = aws_iam_role.lambda_exec_role.arn

  filename         = "${path.module}/../build/function.zip"
  source_code_hash = filebase64sha256("${path.module}/../build/function.zip")

  environment {
    variables = {
      USERS_TABLE_NAME = aws_dynamodb_table.user_table.name
    }
  }
}