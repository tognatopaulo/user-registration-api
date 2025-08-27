# Define a API Gateway HTTP API (V2)
resource "aws_apigatewayv2_api" "users_api" {
  name          = "users-api-${var.env}"
  protocol_type = "HTTP" # Isso define como uma HTTP API

  # Opcional: Configurações de CORS, se sua aplicação frontend precisa acessar esta API
  # cors_configuration {
  #   allow_origins = ["*"] # Ajuste para domínios específicos em produção
  #   allow_methods = ["*"] # Ex: ["GET", "POST", "OPTIONS"]
  #   allow_headers = ["*"] # Ex: ["Content-Type", "Authorization"]
  #   max_age       = 300
  # }
}

# Define a integração entre o API Gateway e a função Lambda
# Aqui é onde especificamos que é uma integração AWS_PROXY e o formato do payload V2
resource "aws_apigatewayv2_integration" "lambda_register" {
  api_id             = aws_apigatewayv2_api.users_api.id
  integration_type   = "AWS_PROXY" # Essencial para que o evento completo seja enviado à Lambda
  integration_method = "POST"      # O método HTTP que o API Gateway usará para invocar a Lambda (POST é o padrão para AWS_PROXY)
  integration_uri    = aws_lambda_function.user_registration.invoke_arn # ARN de invocação da sua Lambda
  payload_format_version = "2.0"   # Isso garante que a Lambda receberá o evento no formato APIGatewayV2HTTPEvent
}

# Define a rota para o endpoint POST /users/register
# No HTTP API, a rota já inclui o método e o path
resource "aws_apigatewayv2_route" "post_register" {
  api_id    = aws_apigatewayv2_api.users_api.id
  route_key = "POST /users/register" # Combina método HTTP e path
  target    = "integrations/${aws_apigatewayv2_integration.lambda_register.id}"
}

# Permissão para o API Gateway invocar a função Lambda
# O source_arn muda para o formato do API Gateway V2
resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.user_registration.function_name
  principal     = "apigateway.amazonaws.com"
  # O source_arn para HTTP API V2 usa o execution_arn do apigw V2
  source_arn    = "${aws_apigatewayv2_api.users_api.execution_arn}/*/*"
}

# Define um estágio para a API
# HTTP API não usa aws_api_gateway_deployment explícito como o V1.
# auto_deploy = true faz com que o estágio seja automaticamente atualizado
# quando houver mudanças nas integrações ou rotas.
resource "aws_apigatewayv2_stage" "dev" {
  api_id      = aws_apigatewayv2_api.users_api.id
  name        = var.env # Nome do estágio (ex: "dev", "prod")
  auto_deploy = true    # Habilita a implantação automática de mudanças

  # Garante que a rota seja criada antes do estágio tentar implantá-la
  depends_on = [
    aws_apigatewayv2_route.post_register,
    aws_apigatewayv2_integration.lambda_register
  ]
}

# Observações:
# 1. Os recursos 'aws_api_gateway_resource', 'aws_api_gateway_method',
#    'aws_api_gateway_deployment' e 'aws_api_gateway_rest_api_policy'
#    (do seu arquivo original) não são mais necessários para o API Gateway V2.
# 2. A política de recurso ('aws_api_gateway_rest_api_policy') não é diretamente aplicável
#    ao HTTP API V2 da mesma forma. Para controle de acesso, você pode usar IAM Policies
#    associadas à API ou autorizadores de Lambda. Para uma API pública, a permissão
#    da Lambda ('aws_lambda_permission') geralmente é suficiente.