CREATE TABLE "author" (
  "uuid" varchar PRIMARY KEY,
  "deleted_at" timestamp null,
  "person_uuid" varchar,
  "book_uuid" varchar,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

CREATE TABLE "book" (
  "uuid" varchar PRIMARY KEY,
  "deleted_at" timestamp null,
  "title" varchar,
  "published_at" timestamp null,
  "library_uuid" varchar,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

CREATE TABLE "book_category" (
  "uuid" varchar PRIMARY KEY,
  "name" varchar,
  "book_uuid" varchar,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

CREATE TABLE "library" (
  "uuid" varchar PRIMARY KEY,
  "name" varchar,
  "deleted_at" timestamp null,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

CREATE TABLE "person" (
  "uuid" varchar PRIMARY KEY,
  "name" varchar,
  "deleted_at" timestamp null,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

CREATE TABLE "borrow" (
  "uuid" varchar PRIMARY KEY,
  "book_uuid" varchar,
  "person_uuid" varchar,
  "deleted_at" timestamp null,
  "borrowed_at" timestamp null,
  "returned_at" timestamp null,
  "to_return_at" timestamp null,
  "fee" int null,
  "updated_at" timestamp null,
  "created_at" timestamp null
);

ALTER TABLE "author" ADD FOREIGN KEY ("book_uuid") REFERENCES "book" ("uuid");
ALTER TABLE "author" ADD FOREIGN KEY ("person_uuid") REFERENCES "person" ("uuid");

ALTER TABLE "book" ADD FOREIGN KEY ("library_uuid") REFERENCES "library" ("uuid");

ALTER TABLE "book_category" ADD FOREIGN KEY ("book_uuid") REFERENCES "book" ("uuid");

ALTER TABLE "borrow" ADD FOREIGN KEY ("book_uuid") REFERENCES "book" ("uuid");

ALTER TABLE "borrow" ADD FOREIGN KEY ("person_uuid") REFERENCES "person" ("uuid");
