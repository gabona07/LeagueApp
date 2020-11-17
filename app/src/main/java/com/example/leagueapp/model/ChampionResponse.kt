package com.example.leagueapp.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class ChampionResponse(@JvmField val data: Map<String, Champion>) {

    data class Champion(@JvmField val id: String,
                        @JvmField val key: Long,
                        @JvmField val name: String,
                        @JvmField val title: String,
                        @JvmField val image: Image?,
                        @JvmField val tags: ArrayList<String>?,
                        @JvmField var isFavorite: Boolean) : Parcelable {

        data class Image(@JvmField val full: String) : Parcelable {

            constructor(parcel: Parcel) : this(parcel.readString()!!)

            fun getIconUrl(): String = "https://ddragon.leagueoflegends.com/cdn/10.21.1/img/champion/$full"

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(full)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Image> {
                override fun createFromParcel(parcel: Parcel): Image {
                    return Image(parcel)
                }

                override fun newArray(size: Int): Array<Image?> {
                    return arrayOfNulls(size)
                }
            }
        }

        constructor(parcel: Parcel) : this(
                parcel.readString().toString(),
                parcel.readLong(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readParcelable(Image::class.java.classLoader),
                parcel.createStringArrayList(),
                parcel.readByte() != 0.toByte())

        fun getTags(): String? = tags?.joinToString(", ") { it }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeLong(key)
            parcel.writeString(name)
            parcel.writeString(title)
            parcel.writeParcelable(image, flags)
            parcel.writeStringList(tags)
            parcel.writeByte(if (isFavorite) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Champion> {
            override fun createFromParcel(parcel: Parcel): Champion {
                return Champion(parcel)
            }

            override fun newArray(size: Int): Array<Champion?> {
                return arrayOfNulls(size)
            }
        }
    }
}