package ru.dariamikhailukova.notebook_5.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var text: String,
    var date: Date
): Parcelable