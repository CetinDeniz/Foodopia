package com.axuca.foodorder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axuca.foodorder.model.db.Restaurant

/**
 * Set exportSchema to false, so as not to keep schema version history backups.
 */
@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun getRestaurantDao(): RestaurantDao

    companion object {

        @Volatile
        private lateinit var DATABASE_INSTANCE: RestaurantDatabase

        fun getDatabase(context: Context): RestaurantDatabase {

            if (!::DATABASE_INSTANCE.isInitialized) { // First check
                synchronized(this) {
                    if (!::DATABASE_INSTANCE.isInitialized) { // Double check
                        val instance = Room
                            .databaseBuilder(
                                context.applicationContext,
                                RestaurantDatabase::class.java,
                                "restaurant"
                            )
//                            .createFromAsset("database/restaurants.db")

                            /** Allows Room to destructively recreate database tables
                             * if Migrations that would migrate old database schemas
                             * to the latest schema version are not found. */
                            .fallbackToDestructiveMigration()
                            .build()

                        DATABASE_INSTANCE = instance
                    }
                }
            }
            return DATABASE_INSTANCE
        }

    }

}