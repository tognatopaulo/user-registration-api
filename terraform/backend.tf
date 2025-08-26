terraform {
  backend "s3" {
    bucket         = "tognatech-terraform-state"
    key            = "user-registration/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-lock"
  }
}