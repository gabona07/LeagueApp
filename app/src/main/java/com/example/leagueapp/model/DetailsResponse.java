package com.example.leagueapp.model;

import java.util.List;
import java.util.Map;

public class DetailsResponse {
    public Map<String, DetailsResponse.Detail> data;


    public class Detail {
        String lore;
        Info info;
        List<Spell> spells;

        public Detail(String lore, Info info, List<Spell> spells) {
            this.lore = lore;
            this.info = info;
            this.spells = spells;
        }

        public String getLore() {
            return lore;
        }

        public Info getInfo() {
            return info;
        }

        public List<Spell> getSpells() {
            return spells;
        }

        public class Info {
            int attack;
            int defense;
            int magic;
            int difficulty;

            protected Info(int attack, int defense, int magic, int difficulty) {
                this.attack = attack;
                this.defense = defense;
                this.magic = magic;
                this.difficulty = difficulty;
            }

            public int getAttack() {
                return attack * 100;
            }

            public int getDefense() {
                return defense * 100;
            }

            public int getMagic() {
                return magic * 100;
            }

            public int getDifficulty() {
                return difficulty * 100;
            }

        }

        public class Spell {
            String name;
            String description;
            Image image;

            public Spell(String name, String description, Image image) {
                this.name = name;
                this.description = description;
                this.image = image;
            }

            public class Image {
                String full;

                public Image(String full) {
                    this.full = full;
                }

                public String getSpellIcon() {
                    return "https://ddragon.leagueoflegends.com/cdn/10.22.1/img/spell/" + full;
                }

            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public Image getImage() {
                return image;
            }
        }
    }
}
