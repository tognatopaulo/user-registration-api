output "users_table_name" {
  value = aws_dynamodb_table.user_table.name
}

output "users_table_arn" {
  value = aws_dynamodb_table.user_table.arn
}