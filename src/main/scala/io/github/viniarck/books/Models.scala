package io.github.viniarck.books
import io.getquill._
import java.time.{LocalDateTime, ZoneId, ZoneOffset}
import java.util.Date

object Models {

  implicit val decodeLocalTime = MappedEncoding[Date, LocalDateTime](
    date => LocalDateTime.ofInstant(date.toInstant, ZoneId.systemDefault())
  )
  implicit val encodeLocalTime = MappedEncoding[LocalDateTime, Date](
    time =>
      new Date(time.toEpochSecond(ZoneOffset.of(ZoneId.systemDefault().getId)))
  )

  case class Author(
      uuid: String,
      person_uuid: String,
      book_uuid: String,
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )

  case class Book(
      uuid: String,
      title: String,
      library_uuid: Option[String],
      published_at: Option[LocalDateTime],
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )

  case class BookSerializer(
      title: String,
      library_uuid: String,
      author_uuid: String,
      book_category: Option[String],
      published_at: Option[LocalDateTime]
  )

  case class BookCategory(
      uuid: String,
      name: String,
      book_uuid: String,
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )

  case class LibrarySerializer(
      name: String
  )

  case class Library(
      uuid: String,
      name: String,
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )

  case class PersonSerializer(
      name: String
  )

  case class Person(
      uuid: String,
      name: String,
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )

  case class Borrow(
      id: Option[Int],
      book_uuid: String,
      person_uuid: String,
      borrowed_on: Option[LocalDateTime],
      returned_on: Option[LocalDateTime],
      to_return_on: Option[LocalDateTime],
      fee: Option[Int],
      fee_paid_on: Option[LocalDateTime],
      deleted_at: Option[LocalDateTime],
      updated_at: Option[LocalDateTime],
      created_at: Option[LocalDateTime]
  )
}
