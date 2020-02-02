package io.github.viniarck.books

import io.getquill._
import java.time.{LocalDateTime, ZoneId, ZoneOffset}
import java.util.Date

object Serializers {

  case class PersonSerializer(
      name: String
  )

  case class LibrarySerializer(
      name: String
  )

  case class BookSerializer(
      title: String,
      library_uuid: String,
      author_uuid: String,
      book_category: Option[String],
      published_at: Option[LocalDateTime]
  )
}
