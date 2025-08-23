variable "aws_regions" {
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

variable "enable_point_in_time_recovery" {
    type = bool
    default = true
    description = "Enable point-in-time recovery for the DynamoDB table"
}