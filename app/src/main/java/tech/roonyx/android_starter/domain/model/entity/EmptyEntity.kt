package tech.roonyx.android_starter.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empty")
data class EmptyEntity(
    @PrimaryKey val id: Int
)