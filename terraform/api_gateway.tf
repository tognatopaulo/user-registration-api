resource "aws_api_gateway_rest_api" "users_api" {
  name        = "users-api-${var.env}"
  description = "API Gateway para registro de usuários"
}

resource "aws_api_gateway_resource" "users" {
  rest_api_id = aws_api_gateway_rest_api.users_api.id
  parent_id   = aws_api_gateway_rest_api.users_api.root_resource_id
  path_part   = "users"
}

resource "aws_api_gateway_resource" "register" {
  rest_api_id = aws_api_gateway_rest_api.users_api.id
  parent_id   = aws_api_gateway_resource.users.id
  path_part   = "register"
}

resource "aws_api_gateway_method" "register_post" {
  rest_api_id   = aws_api_gateway_rest_api.users_api.id
  resource_id   = aws_api_gateway_resource.register.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "register_post_lambda" {
  rest_api_id             = aws_api_gateway_rest_api.users_api.id
  resource_id             = aws_api_gateway_resource.register.id
  http_method             = aws_api_gateway_method.register_post.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.user_registration.invoke_arn
}

resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.user_registration.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_api_gateway_rest_api.users_api.execution_arn}/*/*"
}

resource "aws_api_gateway_rest_api_policy" "open_policy" {
  rest_api_id = aws_api_gateway_rest_api.users_api.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = "*",
      Action = "execute-api:Invoke",
      Resource = "${aws_api_gateway_rest_api.users_api.execution_arn}/*"
    }]
  })
}

resource "aws_api_gateway_deployment" "users_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.users_api.id
  triggers = {
    redeployment = sha1(jsonencode([
      aws_api_gateway_method.register_post.id,
      aws_api_gateway_integration.register_post_lambda.id
    ]))
  }
  lifecycle {
    create_before_destroy = true
  }
  depends_on = [
    aws_api_gateway_integration.register_post_lambda,
    aws_api_gateway_method.register_post
  ]
}

resource "aws_api_gateway_stage" "dev" {
  deployment_id = aws_api_gateway_deployment.users_api_deployment.id
  rest_api_id   = aws_api_gateway_rest_api.users_api.id
  stage_name    = var.env
}