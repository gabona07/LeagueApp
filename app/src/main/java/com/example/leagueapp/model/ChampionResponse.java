package com.example.leagueapp.model;

import java.util.Map;

public class ChampionResponse {
    public Map<String, Champion> data;


    public class Champion {
        String id;
        String name;
        String title;
        Image image;

        public class Image {
            String full;

            public Image(String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }

            public String getIconUrl() {
                return "https://ddragon.leagueoflegends.com/cdn/10.21.1/img/champion/" + getFull();
            }

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public Image getImage() {
            return image;
        }

    }
}
