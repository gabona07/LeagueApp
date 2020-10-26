package com.example.leagueapp.model;

import java.util.Map;

public class ChampionResponse {
    public Map<String, Champion> data;


    class Champion {
        String id;
        String name;
        String title;
        String blurb;

        public Champion(String id, String name, String title, String blurb) {
            this.id = id;
            this.name = name;
            this.title = title;
            this.blurb = blurb;
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
    }
}
