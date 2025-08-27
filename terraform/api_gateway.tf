resource "aws_apigatewayv2_api" "users_api" {
  name          = "users-api-${var.env}"
  protocol_type = "HTTP"
  description   = "API Gateway v2 para registro de usuários"
}

resource "aws_apigatewayv2_integration" "lambda_register" {
  api_id                 = aws_apigatewayv2_api.users_api.id
  integration_type       = "AWS_PROXY"
  integration_uri        = aws_lambda_function.user_registration.invoke_arn
  integration_method     = "POST"
  payload_format_version = "2.0"
}

resource "aws_apigatewayv2_route" "post_register" {
  api_id    = aws_apigatewayv2_api.users_api.id
  route_key = "POST /users/register"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_register.id}"
}

resource "aws_apigatewayv2_stage" "dev" {
  api_id      = aws_apigatewayv2_api.users_api.id
  name        = var.env
  auto_deploy = true
}

resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.user_registration.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.users_api.execution_arn}/*/*"
}