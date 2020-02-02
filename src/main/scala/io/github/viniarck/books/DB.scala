package io.github.viniarck.books

import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._
import io.getquill._

object DB {
  lazy val ctx = new FinaglePostgresContext(SnakeCase, "ctx")
  import ctx._
}
