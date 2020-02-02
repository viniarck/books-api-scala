package io.github.viniarck.books

import cats.effect.IO
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._
import io.getquill._

import io.github.viniarck.books.Models._
import io.github.viniarck.books.Routes._
import io.github.viniarck.books.DB._

object Routes {

  import DB.ctx._

  case class Message(msg: String)
  case class Books(books: List[Book])
  case class Authors(authors: List[Author])
  case class Libraries(libraries: List[Library])
  case class People(people: List[Person])

  import io.circe.Decoder, io.circe.Encoder, io.circe.generic.semiauto._

  def genUUIDStr(): String = java.util.UUID.randomUUID.toString

  def healthcheck: Endpoint[cats.effect.IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def book: Endpoint[cats.effect.IO, Books] = get("book") {
    Ok(Books(Await.result(ctx.run(quote { query[Book] }))))
  }

  def author: Endpoint[cats.effect.IO, Authors] = get("author") {
    Ok(Authors(Await.result(ctx.run(quote { query[Author] }))))
  }

  def library: Endpoint[cats.effect.IO, Libraries] = get("library") {
    Ok(Libraries(Await.result(ctx.run(quote { query[Library] }))))
  }

  def personGet: Endpoint[cats.effect.IO, People] = get("person") {
    Ok(People(Await.result(ctx.run(quote { query[Person] }))))
  }

  def createPerson(name: String): Either[Exception, Person] = {
    val now = Some(java.time.LocalDateTime.now)
    val person = Person(genUUIDStr(), name, None, now, now)
    try {
      Await.result(ctx.run(quote { query[Person].insert(lift(person)) }))
      Right(person)
    } catch {
      case e: Exception => Left(e)
    }
  }

  def personPost: Endpoint[cats.effect.IO, Message] =
    post("person" :: stringBody) { (body: String) =>
      io.circe.jawn.decode[PersonSerializer](body) match {
        case Left(l) => BadRequest(l)
        case Right(p) => {
          createPerson(p.name) match {
            case Left(e)  => BadRequest(e)
            case Right(p) => Created(Message(p.uuid))
          }
        }
      }
    }

  def createLibrary(name: String): Either[Exception, Library] = {
    val now = Some(java.time.LocalDateTime.now)
    val library = Library(genUUIDStr(), name, None, now, now)
    try {
      Await.result(ctx.run(quote { query[Library].insert(lift(library)) }))
      Right(library)
    } catch {
      case e: Exception => Left(e)
    }
  }

  def libraryPost: Endpoint[cats.effect.IO, Message] =
    post("library" :: stringBody) { (body: String) =>
      io.circe.jawn.decode[LibrarySerializer](body) match {
        case Left(l) => BadRequest(l)
        case Right(p) => {
          createLibrary(p.name) match {
            case Left(e)  => BadRequest(e)
            case Right(p) => Created(Message(p.uuid))
          }
        }
      }
    }

  def createBook(bookSer: BookSerializer): Either[Exception, Book] = {
    val now = Some(java.time.LocalDateTime.now)
    val book = Book(
      genUUIDStr(),
      bookSer.title,
      Some(bookSer.library_uuid),
      bookSer.published_at,
      None,
      now,
      now
    )
    try {
      Await.result(ctx.run(quote { query[Book].insert(lift(book)) }))
      // TODO insert Author
      Right(book)
    } catch {
      case e: Exception => Left(e)
    }
  }

  def bookPost: Endpoint[cats.effect.IO, Message] =
    post("book" :: stringBody) { (body: String) =>
      io.circe.jawn.decode[BookSerializer](body) match {
        case Left(e) => BadRequest(e)
        case Right(b) => {
          createBook(b) match {
            case Left(e)  => BadRequest(e)
            case Right(b) => Created(Message(b.uuid))
          }
        }
      }
    }

}
