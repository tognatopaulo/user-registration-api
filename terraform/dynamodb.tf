locals {
  user_table_name = var.table_name
}

resource "aws_dynamodb_table" "user_table" {
  name           = local.user_table_name
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "userId"

  attribute {
    name = "userId"
    type = "S"
  }

  attribute {
    name = "email"
    type = "S"
  }

  global_secondary_index {
    name            = "email-index"
    hash_key        = "email"
    projection_type = "ALL"
  }

  tags = {
    Environment = var.env
    Name        = local.user_table_name
  }
}

