locals {
  user_table_name = "${var.table_name}-${var.env}"
}

resource "aws_dynamodb_table" "user_table" {
  name           = local.user_table_name
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "userId"

  attribute {
    name = "userId"
    type = "S"
  }

  global_secondary_index {
    hash_key        = "gsi-email"
    name            = "email"
    projection_type = "ALL"
  }

  attribute {
    name = "email"
    type = "S"
  }

  point_in_time_recovery {
    enabled = var.enable_point_in_time_recovery
  }

  tags = {
    Environment = var.env
    Name        = local.user_table_name
  }
}

