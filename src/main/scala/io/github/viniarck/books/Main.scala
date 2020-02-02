package io.github.viniarck.books

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._
import io.getquill._
import scala.concurrent.ExecutionContext.Implicits.global

import io.github.viniarck.books.Models._
import io.github.viniarck.books.Routes._

object Main extends App {

  def service: Service[Request, Response] =
    Bootstrap
      .serve[Text.Plain](healthcheck)
      .serve[Application.Json](book :+: bookPost)
      .serve[Application.Json](author)
      .serve[Application.Json](library :+: libraryPost)
      .serve[Application.Json](personGet :+: personPost)
      .toService

  Await.ready(Http.server.serve(":8081", service))
}
