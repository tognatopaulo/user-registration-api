resource "aws_lambda_function" "user_registration" {
  count            = var.create_lambda ? 1 : 0
  function_name = "user-registration-${var.env}"
  handler       = "com.example.adapter.lambda.UserHandler::handleRequest"
  runtime       = "java17"
  role          = aws_iam_role.lambda_exec_role.arn
  timeout       = 30
  filename         = "${path.module}/../build/function.zip"
  source_code_hash = filebase64sha256("${path.module}/../build/function.zip")

  environment {
    variables = {
      USERS_TABLE_NAME = aws_dynamodb_table.user_table.name
    }
  }
}