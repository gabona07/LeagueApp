package com.example.leagueapp.model;

import java.util.Map;

public class ChampionResponse {
    public Map<String, Champion> data;


    public class Champion {
        String id;
        String name;
        String title;
        String blurb;
        Image image;

        public Champion(String id, String name, String title, String blurb, Image image) {
            this.id = id;
            this.name = name;
            this.title = title;
            this.blurb = blurb;
            this.image = image;
        }

        @Override
        public String toString() {
            return "Champion{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", blurb='" + blurb + '\'' +
                    '}';
        }

        public class Image {
            String full;

            public Image(String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }

            public void setFull(String full) {
                this.full = full;
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

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBlurb() {
            return blurb;
        }

        public void setBlurb(String blurb) {
            this.blurb = blurb;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }
}
