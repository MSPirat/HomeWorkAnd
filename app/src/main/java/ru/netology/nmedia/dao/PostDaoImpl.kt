package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_AUTHOR_AVATAR = "authorAvatar"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_LIKED = "liked"
        const val COLUMN_LIKENUM = "likeNum"
        const val COLUMN_SHARENUM = "shareNum"
        const val COLUMN_VIEWNUM = "viewNum"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_AUTHOR_AVATAR,
            COLUMN_PUBLISHED,
            COLUMN_CONTENT,
            COLUMN_VIDEO,
            COLUMN_LIKED,
            COLUMN_LIKENUM,
            COLUMN_SHARENUM,
            COLUMN_VIEWNUM
        )
    }

    override fun get(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                likeNum = likeNum + CASE WHEN liked THEN -1 ELSE 1 END,
                liked = CASE WHEN liked THEN 0 ELSE 1 END
                WHERE id = ?;
                """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                shareNum = shareNum + 1
                WHERE id = ?;
                """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, "Somebody")
            put(PostColumns.COLUMN_AUTHOR_AVATAR, "")
            put(PostColumns.COLUMN_PUBLISHED, "Once upon a time")
            put(PostColumns.COLUMN_CONTENT, post.content)
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                authorAvatar = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR_AVATAR)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
                liked = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED)) != 0,
                likeNum = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_LIKENUM)),
                shareNum = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_SHARENUM)),
                viewNum = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_SHARENUM))
            )
        }
    }

    companion object {
        val DDL: String =
            """
                CREATE TABLE ${PostColumns.TABLE} (
                ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
                ${PostColumns.COLUMN_AUTHOR_AVATAR} TEXT NOT NULL,
                ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
                ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
                ${PostColumns.COLUMN_VIDEO} TEXT,
                ${PostColumns.COLUMN_LIKED} BOOLEAN NOT NULL DEFAULT 0,
                ${PostColumns.COLUMN_LIKENUM} INTEGER NOT NULL DEFAULT 0,
                ${PostColumns.COLUMN_SHARENUM} INTEGER NOT NULL DEFAULT 0,
                ${PostColumns.COLUMN_VIEWNUM} INTEGER NOT NULL DEFAULT 0
                );
                """.trimIndent()
    }
}