variable "aws_region" {
  type = string
  default = "us-east-1"
  description = "AWS Region"
}

variable "env" {
    type = string
    default = "dev"
    description = "Environment name, e.g., dev, prod"
}

variable "table_name" {
    type = string
    default = "users"
    description = "DynamoDB table name"
}

variable "create_lambda" {
  type    = bool
  default = true
}