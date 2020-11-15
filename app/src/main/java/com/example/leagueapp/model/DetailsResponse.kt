package com.example.leagueapp.model

data class DetailsResponse(@JvmField val data: Map<String,Detail>) {

    data class Detail(@JvmField val lore: String, @JvmField val info: Info, @JvmField val spells: List<Spell>) {

        data class Info(val attack: Int,
                        val defense: Int,
                        val magic: Int,
                        val difficulty: Int) {

            fun getAttackProgress(): Int = attack * 100

            fun getDefenseProgress(): Int = defense * 100

            fun getMagicProgress(): Int = magic * 100

            fun getDifficultyProgress(): Int = difficulty * 100

        }

        data class Spell(@JvmField val name: String, @JvmField val description: String, @JvmField val image: Image) {

            data class Image(val full: String) {

                fun getSpellIcon(): String = "https://ddragon.leagueoflegends.com/cdn/10.22.1/img/spell/$full"

            }
        }
    }
}